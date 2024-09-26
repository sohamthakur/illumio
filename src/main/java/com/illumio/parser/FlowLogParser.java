package com.illumio.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Soham Thakur
 * FlowLogParser is responsible for parsing the flow log file.
 */
public class FlowLogParser {

    /**
     * Parses the flow log file and returns a list of raw log entries.
     * Each entry is a String array containing the fields from a flow log line.
     *
     * @param flowLogPath the path to the flow log file
     * @return List of String[] containing parsed log entries
     * @throws IOException if there is an error reading the log file
     */
    public List<String[]> parseFlowLog(String flowLogPath) throws IOException {
        List<String[]> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(flowLogPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 13) {
                    entries.add(parts);
                } else {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        }
        return entries;
    }
}
