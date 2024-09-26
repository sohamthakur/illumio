package com.illumio.parser;

import com.illumio.model.FlowLogEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FlowLogParser
 *
 * Parses AWS VPC Flow Log files.
 */
public class FlowLogParser {

    /**
     * Parses the flow log file and returns a list of FlowLogEntry objects.
     *
     * @param filePath Path to the flow log file.
     * @return List of FlowLogEntry.
     * @throws IOException If file reading fails.
     */
    public List<FlowLogEntry> parseFlowLog(String filePath) throws IOException {
        List<FlowLogEntry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                String[] tokens = line.split("\\s+");
                if (tokens.length < 14) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }

                try {
                    int destinationPort = Integer.parseInt(tokens[5]);
                    int protocolNumber = Integer.parseInt(tokens[6]);

                    FlowLogEntry entry = new FlowLogEntry(destinationPort, protocolNumber);
                    entries.add(entry);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                }
            }
        }
        return entries;
    }
}
