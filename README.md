# illumio

# Flow Log Tagging Program

## Overview
This program processes flow logs by mapping each entry to a tag using a lookup table. It outputs two key metrics:
- **Tag Counts**: The total number of occurrences for each tag.
- **Port/Protocol Combination Counts**: The total number of occurrences for each port/protocol combination.

## Assumptions
- **Log Format**: The program supports AWS VPC Flow Logs in **version 2** format, which is assumed to have 13 columns:
  - Column 6 represents the **destination port**.
  - Column 7 represents the **protocol number**.
- **Lookup Table Format**: The lookup table is provided as a CSV file with the following columns:
  - `dstport`: Destination port.
  - `protocol`: Protocol (e.g., `tcp`, `udp`, `icmp`). This field is case-insensitive.
  - `tag`: A string representing the tag to apply for the port/protocol combination.
- **Protocol Mapping**: A predefined mapping exists for commonly used protocols:
  - `1 -> icmp`
  - `6 -> tcp`
  - `17 -> udp`
  - Any unknown protocol numbers are mapped to `"unknown"`.
- **Untagged Entries**: Any entries that do not match a `(dstport, protocol)` combination in the lookup table will be assigned the tag `"Untagged"`.
- **Output File**: The output file is generated with the current date and time in the filename, ensuring uniqueness.

## Project Structure

/data # Contains the input files (logs.txt, lookup.csv)

logs.txt # Flow log file to be processed.
lookup.csv # Lookup table containing dstport, protocol, and tag.
src/main/java/com/illumio

FlowLogTagger.java ## Main entry point for the program.
parser/
FlowLogParser.java # Responsible for parsing flow logs.
LookupTableParser.java # Responsible for parsing the lookup table.
processor/
TagProcessor.java # Processes the flow logs and assigns tags.
util/
ProtocolMapper.java # Maps protocol numbers to protocol names.
OutputWriter.java # Writes the result to an output file.
output/ # Output directory containing results.


## Instructions to Compile and Run

### Prerequisites
- **JDK 8+**
- **Apache Maven**

### Compile the Program
1. Navigate to the root directory of the project (where `pom.xml` is located).
2. Run the following Maven command to compile the project:
   ```bash
   mvn clean compile

### Run the Program
Ensure that the input files (logs.txt and lookup.csv) are placed inside the data/ directory.

## Run the program using the following command:

mvn exec:java -Dexec.args="data/logs.txt data/lookup.csv"

The output file will be saved in the output/ directory with a name like flow_log_summary_YYYYMMDD_HHmmss.txt

## Testing
Test Cases
# Basic Matching: Tested with sample flow logs and lookup table to ensure correct matching and tagging.
# Unknown Protocol Handling: Flow logs with protocol numbers not found in the ProtocolMapper are mapped as "unknown".
# Malformed Entries: Lines with fewer than 13 fields or incorrect formats are skipped with appropriate warnings.
# Large File Handling: Successfully tested with flow log files up to 10MB and lookup tables with 10,000 mappings.

## How to Test
Ensure that the flow log file and lookup table exist in the data/ directory.
Run the program as described in the Run the Program section and verify the output in the output/ directory.
Future Improvements
Extend the protocol mapping in ProtocolMapper to include more protocols.
Add support for alternative output formats such as CSV or JSON.
Consider optimizing the program for multi-threaded processing of large flow log files.
Notes
If no matches are found in the lookup table for a given (dstport, protocol) combination, the entry will be tagged as "Untagged".
Protocol mapping is currently limited to common protocols (TCP, UDP, ICMP). Any unrecognized protocol will be marked as "unknown".

