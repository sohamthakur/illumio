package com.illumio.model;

/**
 * FlowLogEntry
 *
 * Represents a single entry from the flow log.
 */
public class FlowLogEntry {
    private int destinationPort;
    private int protocolNumber;

    public FlowLogEntry(int destinationPort, int protocolNumber) {
        this.destinationPort = destinationPort;
        this.protocolNumber = protocolNumber;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public int getProtocolNumber() {
        return protocolNumber;
    }
}

