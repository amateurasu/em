package com.viettel.ems.snmp;

import org.snmp4j.PDU;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Address;

public interface RespondHandler {
    void handle(ResponseEvent<Address> event, PDU pdu);
}
