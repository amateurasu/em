package com.viettel.ems.snmp;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.snmp4j.*;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@NoArgsConstructor
public class TrapReceiver implements CommandResponder, CommandLineRunner {

    private static final String username = "username"; // SET THIS
    private static final String authPassphrase = "authpassphrase"; // SET THIS
    private static final String privacyPassphrase = "privacypassphrase"; // SET THIS

    @Value("${server.snmp.trap-pool-size:10}")
    private static int TRAP_POOL_SIZE = 10;

    private Snmp snmp = null;

    public static void main(String... args) throws Exception {
        new TrapReceiver().run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("initializing SNMP trap receiver: {}", TRAP_POOL_SIZE);
        init();
        snmp.addCommandResponder(this);
    }

    private void init() throws IOException {
        var threadPool = ThreadPool.create("Trap", TRAP_POOL_SIZE);
        var dispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());

        //TRANSPORT
        Address listenAddress = GenericAddress.parse(System.getProperty("snmp4j.listenAddress", "udp:127.0.0.1/162"));
        TransportMapping<?> transport = listenAddress instanceof UdpAddress
            ? new DefaultUdpTransportMapping((UdpAddress) listenAddress)
            : new DefaultTcpTransportMapping((TcpAddress) listenAddress);

        //V3 SECURITY
        var securityProtocols = SecurityProtocols.getInstance();
        SecurityProtocols protocols = securityProtocols.addDefaultProtocols();
        USM usm = new USM(protocols, new OctetString(MPv3.createLocalEngineID()), 0);

        securityProtocols.addPrivacyProtocol(new PrivAES192());
        securityProtocols.addPrivacyProtocol(new PrivAES256());
        securityProtocols.addPrivacyProtocol(new Priv3DES());

        usm.setEngineDiscoveryEnabled(true);
        SecurityModels.getInstance().addSecurityModel(usm);

        snmp = new Snmp(dispatcher, transport);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3(usm));

        snmp.getUSM()
            .addUser(new OctetString(username),
                new UsmUser(new OctetString(username), AuthMD5.ID, new OctetString(authPassphrase), PrivAES128.ID,
                    new OctetString(privacyPassphrase)));

        snmp.listen();
    }

    public <A extends Address> void processPdu(CommandResponderEvent<A> crEvent) {
        PDU pdu = crEvent.getPDU();

        switch (pdu.getType()) {
            case PDU.V1TRAP:
                readPduV1(crEvent, (PDUv1) pdu);
                break;
            case PDU.TRAP:
                readPdu(crEvent, pdu);
                break;
        }

        List<? extends VariableBinding> varBinds = pdu.getVariableBindings();
        if (varBinds != null) {
            for (VariableBinding vb : varBinds) {
                Variable variable = vb.getVariable();
                String syntaxString = variable.getSyntaxString();
                int syntax = variable.getSyntax();
                log.info("Variable{OID={}, value={}, syntax={}, syntaxString={}}; ", vb.getOid(), variable, syntax,
                    syntaxString);
            }
        }

        log.info("==== TRAP END ===");
    }

    private static <A extends Address> void readPdu(CommandResponderEvent<A> crEvent, PDU pdu) {
        log.info("SnmpTrap{errorStatus={}, errorIndex={}, requestID={}, snmpVersion={}, community={}}",
            pdu.getErrorStatus(), pdu.getErrorIndex(), pdu.getRequestID(), PDU.TRAP,
            new String(crEvent.getSecurityName()));
    }

    private static <A extends Address> void readPduV1(CommandResponderEvent<A> crEvent, PDUv1 pdu) {
        log.info("SnmpTrapV1{agentAddr={}, enterprise={}, timeStamp={}, genericTrap={}, specificTrap={}, community={}}",
            pdu.getAgentAddress(), pdu.getEnterprise(), pdu.getTimestamp(), pdu.getGenericTrap(), pdu.getSpecificTrap(),
            new String(crEvent.getSecurityName()));
    }
}
