package com.viettel.ems.snmp;

import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Address;

import java.io.IOException;

public class Getter extends ServerSide {

    public Getter(String ipAddress, String port) throws IOException {
        super(ipAddress, port);
    }

    @Override
    protected ResponseEvent<Address> getRespond() throws IOException {
        return snmp.getBulk(pdu, target);
    }
}

