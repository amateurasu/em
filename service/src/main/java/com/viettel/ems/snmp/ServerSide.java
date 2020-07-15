package com.viettel.ems.snmp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Data
public abstract class ServerSide implements AutoCloseable {

    @Getter
    @Setter
    private static String community;
    @Getter
    @Setter
    private static int version;

    protected CommunityTarget<Address> target;
    protected Snmp snmp;
    protected PDU pdu = new PDU();;

    public ServerSide(String ipAddress, String port) throws IOException {
        TransportMapping<?> transport = new DefaultUdpTransportMapping();
        transport.listen();

        // Create Target Address object
        target = new CommunityTarget<>(new UdpAddress(ipAddress + "/" + port), new OctetString(community));
        target.setVersion(version);
        target.setRetries(2);
        target.setTimeout(1000);

        pdu.setRequestID(new Integer32(1));

        snmp = new Snmp(transport);
    }

    protected ServerSide bind(VariableBinding... vars) {
        if (vars != null) {
            for (VariableBinding var : vars) {
                if (var != null) pdu.add(var);
            }
        }

        return this;
    }

    public void handleResult(RespondHandler resultHandler, RespondHandler errorHandler) throws IOException, TimeoutException {
        ResponseEvent<Address> response = getRespond();
        if (response == null) {
            throw new TimeoutException("Agent Timeout");
        }

        PDU responsePDU = response.getResponse();
        if (responsePDU == null) {
            throw new NullPointerException("Respond PDU is null");
        }

        int errorStatus = responsePDU.getErrorStatus();
        if (errorStatus == PDU.noError) {
            resultHandler.handle(response, responsePDU);
        } else {
            errorHandler.handle(response, responsePDU);
        }
    }

    protected abstract ResponseEvent<Address> getRespond() throws IOException;

    @Override
    public void close() throws Exception {
        if (this.snmp != null) {
            this.snmp.close();
            this.snmp = null;
        }
    }
}
