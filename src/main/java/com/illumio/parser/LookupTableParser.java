package com.illumio.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * LookupTableParser
 *
 * Author: Soham Thakur
 * Parses the lookup table CSV file mapping (dstport, protocol) to tags.
 */
public class LookupTableParser {

    /**
     * Parses the lookup table CSV and returns a map of (dstport, protocol) to tag.
     *
     * @param filePath Path to the lookup table CSV file.
     * @return Map with key as "dstport,protocol" and value as tag.
     * @throws IOException If file reading fails.
     */
    public Map<String, String> parseLookupTable(String filePath) throws IOException {
        Map<String, String> lookupMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine(); // Skip header
            if (header == null) {
                throw new IOException("Lookup table file is empty.");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                if (tokens.length != 3) {
                    System.err.println("Skipping malformed lookup line: " + line);
                    continue;
                }

                String dstPort = tokens[0].trim();
                String protocol = tokens[1].trim().toLowerCase();
                String tag = tokens[2].trim();

                if (dstPort.isEmpty() || protocol.isEmpty() || tag.isEmpty()) {
                    System.err.println("Incomplete data in lookup line: " + line);
                    continue;
                }

                String key = dstPort + "," + protocol;
                lookupMap.put(key, tag);
            }
        }
        return lookupMap;
    }
}
