package com.illumio.tagger;

import com.illumio.parser.FlowLogParser;
import com.illumio.parser.LookupTableParser;
import com.illumio.model.FlowLogEntry;
import com.illumio.util.OutputWriter;
import com.illumio.util.ProtocolMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FlowLogTagger
 *
 * Main class to execute the flow log tagging process.
 */
public class FlowLogTagger {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java FlowLogTagger <flow_log_file> <lookup_table_file>");
            return;
        }

        String flowLogPath = args[0];
        String lookupTablePath = args[1];

        // Parse lookup table
        LookupTableParser lookupParser = new LookupTableParser();
        Map<String, String> lookupMap = null;
        try {
            lookupMap = lookupParser.parseLookupTable(lookupTablePath);
        } catch (IOException e) {
            System.err.println("Error reading lookup table: " + e.getMessage());
            return;
        }

        // Initialize counters
        Map<String, Integer> tagCountMap = new HashMap<>();
        Map<String, Integer> portProtocolCountMap = new HashMap<>();

        // Parse flow logs
        FlowLogParser flowParser = new FlowLogParser();
        try {
            List<FlowLogEntry> entries = flowParser.parseFlowLog(flowLogPath);
            for (FlowLogEntry entry : entries) {
                String protocolName = ProtocolMapper.getProtocolName(entry.getProtocolNumber()).toLowerCase();
                String portProtocolKey = entry.getDestinationPort() + "," + protocolName;

                // Update port/protocol counts
                portProtocolCountMap.put(portProtocolKey, portProtocolCountMap.getOrDefault(portProtocolKey, 0) + 1);

                // Assign tag
                String tag = lookupMap.getOrDefault(portProtocolKey, "Untagged");

                // Update tag counts
                tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
            }
        } catch (IOException e) {
            System.err.println("Error reading flow log: " + e.getMessage());
            return;
        }

        // Write outputs
        try {
            OutputWriter.writeTagCounts(tagCountMap, "../../output/tag_counts.csv");
            OutputWriter.writePortProtocolCounts(portProtocolCountMap, "../../output/port_protocol_counts.csv");
        } catch (IOException e) {
            System.err.println("Error writing output files: " + e.getMessage());
        }

        System.out.println("Tagging completed. Output files are located in the 'output' directory.");
    }
}

