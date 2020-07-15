package com.viettel.ems.snmp;

import org.snmp4j.PDU;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.VariableBinding;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Setter extends ServerSide {

    public Setter(String ipAddress, String port) throws IOException {
        super(ipAddress, port);
    }

    @Override
    protected ResponseEvent<Address> getRespond() throws IOException {
        return snmp.set(pdu, target);
    }

    @Override
    public Setter bind(VariableBinding... vars) {
        super.bind(vars);
        pdu.setType(PDU.SET);
        return this;
    }
}
