package com.illumio.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Author: Soham Thakur
 * OutputWriter is responsible for writing tag and port/protocol counts to an output file.
 */
public class OutputWriter {

    /**
     * Writes the tag counts and port/protocol counts to the output file.
     *
     * @param outputFilename the name of the output file
     * @param tagCountMap the tag count data
     * @param portProtocolCountMap the port/protocol count data
     * @throws IOException if an error occurs during file writing
     */
    public void writeOutput(String outputFilename, Map<String, Integer> tagCountMap, Map<String, Integer> portProtocolCountMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            // Write Tag Counts
            writer.write("Tag Counts:\n");
            for (Map.Entry<String, Integer> entry : tagCountMap.entrySet()) {
                writer.write(entry.getKey() + ", " + entry.getValue() + "\n");
            }

            // Write Protocol/Port counts
            writer.write("\nPort/Protocol Combination Counts:\n");
            for (Map.Entry<String, Integer> entry : portProtocolCountMap.entrySet()) {
                writer.write(entry.getKey() + ", " + entry.getValue() + "\n");
            }
        }
    }
}
