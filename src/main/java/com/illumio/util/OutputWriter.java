package com.illumio.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * OutputWriter
 *
 * Utility class to write output CSV files.
 */
public class OutputWriter {

    /**
     * Writes tag counts to a CSV file.
     *
     * @param tagCounts Map of tag to count.
     * @param filePath  Output file path.
     * @throws IOException If writing fails.
     */
    public static void writeTagCounts(Map<String, Integer> tagCounts, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Tag,Count");
            writer.newLine();
            for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        }
    }

    /**
     * Writes port/protocol counts to a CSV file.
     *
     * @param portProtocolCounts Map of "port,protocol" to count.
     * @param filePath           Output file path.
     * @throws IOException If writing fails.
     */
    public static void writePortProtocolCounts(Map<String, Integer> portProtocolCounts, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Port,Protocol,Count");
            writer.newLine();
            for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
                String[] keyParts = entry.getKey().split(",", 2);
                if (keyParts.length != 2) continue;
                writer.write(keyParts[0] + "," + keyParts[1] + "," + entry.getValue());
                writer.newLine();
            }
        }
    }
}

