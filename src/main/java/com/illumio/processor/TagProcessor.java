package com.illumio.processor;

import com.illumio.util.ProtocolMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Soham Thakur
 * TagProcessor is responsible for processing log entries to assign tags and count port/protocol combinations.
 */
public class TagProcessor {

    private final Map<String, String> lookupMap;

    public TagProcessor(Map<String, String> lookupMap) {
        this.lookupMap = lookupMap;
    }

    /**
     * Processes flow log entries, returning tag counts and port/protocol counts.
     *
     * @param logEntries the parsed flow log entries
     * @return a result map containing tagCountMap and portProtocolCountMap
     */
    public Map<String, Map<String, Integer>> processLogs(List<String[]> logEntries) {
        Map<String, Integer> tagCountMap = new HashMap<>();
        Map<String, Integer> portProtocolCountMap = new HashMap<>();

        for (String[] entry : logEntries) {
            int destinationPort = Integer.parseInt(entry[6]);
            String protocolName = ProtocolMapper.getProtocolName(Integer.parseInt(entry[7])).toLowerCase();
            String portProtocolKey = destinationPort + "," + protocolName;

            // Update port/protocol counts
            portProtocolCountMap.put(portProtocolKey, portProtocolCountMap.getOrDefault(portProtocolKey, 0) + 1);

            // Assign tag based on lookup table
            String tag = lookupMap.getOrDefault(portProtocolKey, "Untagged");

            // Update tag counts
            tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
        }

        Map<String, Map<String, Integer>> resultMap = new HashMap<>();
        resultMap.put("tagCountMap", tagCountMap);
        resultMap.put("portProtocolCountMap", portProtocolCountMap);
        return resultMap;
    }
}
