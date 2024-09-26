package com.illumio.tagger;

import com.illumio.parser.FlowLogParser;
import com.illumio.processor.TagProcessor;
import com.illumio.util.OutputWriter;
import com.illumio.parser.LookupTableParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Author: Soham Thakur
 * FlowLogTagger is the main class responsible for managing the flow log tagging process.
 */
public class FlowLogTagger {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java FlowLogTagger <flow_log_file> <lookup_table_file>");
            return;
        }

        String flowLogPath = args[0];
        String lookupTablePath = args[1];

        // Parse the lookup table
        LookupTableParser lookupParser = new LookupTableParser();
        Map<String, String> lookupMap;
        try {
            lookupMap = lookupParser.parseLookupTable(lookupTablePath);
        } catch (IOException e) {
            System.err.println("Error reading lookup table: " + e.getMessage());
            return;
        }

        // Parse the flow logs
        FlowLogParser flowLogParser = new FlowLogParser();
        List<String[]> logEntries;
        try {
            logEntries = flowLogParser.parseFlowLog(flowLogPath);
        } catch (IOException e) {
            System.err.println("Error reading flow log: " + e.getMessage());
            return;
        }

        // Processing the logs to get tag and port/protocol counts
        TagProcessor tagProcessor = new TagProcessor(lookupMap);
        Map<String, Map<String, Integer>> resultMap = tagProcessor.processLogs(logEntries);

        // Defining output file path with dynamic timestamp
        Path outputDir = Paths.get("output");
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectories(outputDir);
            } catch (IOException e) {
                System.err.println("Failed to create output directory: " + e.getMessage());
                return;
            }
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);
        String outputFilename = outputDir.resolve("flow_log_summary_" + timestamp + ".txt").toString();

        // Write the output to the file
        OutputWriter outputWriter = new OutputWriter();
        try {
            outputWriter.writeOutput(outputFilename, resultMap.get("tagCountMap"), resultMap.get("portProtocolCountMap"));
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
        }

        System.out.println("Tagging completed. Output file is located at: " + outputFilename);
    }
}
