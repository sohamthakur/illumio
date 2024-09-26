package com.illumio.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ProtocolMapper
 *
 * Utility class to map protocol numbers to protocol names.
 */
public class ProtocolMapper {

    private static final Map<Integer, String> protocolDictionary = new HashMap<>();

    static {
        protocolDictionary.put(1, "icmp");
        protocolDictionary.put(6, "tcp");
        protocolDictionary.put(17, "udp");
        // Extend with more protocols as needed
    }

    /**
     * Returns the protocol name for a given protocol number.
     *
     * @param protocolNumber Protocol number.
     * @return Protocol name or "unknown" if not found.
     */
    public static String getProtocolName(int protocolNumber) {
        return protocolDictionary.getOrDefault(protocolNumber, "unknown");
    }
}

