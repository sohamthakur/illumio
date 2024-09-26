# Illumio - Flow Log Tagging Assessment

## Overview
This program processes flow logs by mapping each entry to a tag using a lookup table. It outputs two key metrics:
- **Tag Counts**: The total number of occurrences for each tag.
- **Port/Protocol Combination Counts**: The total number of occurrences for each port/protocol combination.

## Assumptions
- **Log Format**: Supports AWS VPC Flow Logs in **version 2** format, which has 13 columns:
  - **Column 6**: Destination Port
  - **Column 7**: Protocol Number
- **Lookup Table Format**: CSV with columns:
  - `dstport`: Destination port.
  - `protocol`: Protocol (e.g., `tcp`, `udp`, `icmp`). Case-insensitive.
  - `tag`: Tag to apply.
- **Protocol Mapping**: Predefined mapping:
  - `1` → `icmp`
  - `6` → `tcp`
  - `17` → `udp`
  - Unknown protocols are mapped to `"unknown"`.
- **Untagged Entries**: Entries not matching a `(dstport, protocol)` combination in the lookup table are tagged as `"Untagged"`.
- **Output File**: Named with the current timestamp for uniqueness (e.g., `flow_log_summary_20240926_164151.txt`).

## Project Structure
- **data/**
  - `logs.txt` &mdash; Flow log file to be processed.
  - `lookup.csv` &mdash; Lookup table containing `dstport`, `protocol`, and `tag`.
  
- **src/main/java/com/illumio/**
  -  **tagger/**
    - `FlowLogTagger.java` &mdash; Main entry point for the program.
  
  - **parser/**
    - `FlowLogParser.java` &mdash; Parses flow logs.
    - `LookupTableParser.java` &mdash; Parses the lookup table.
  
  - **processor/**
    - `TagProcessor.java` &mdash; Processes flow logs and assigns tags.
  
  - **util/**
    - `ProtocolMapper.java` &mdash; Maps protocol numbers to names.
    - `OutputWriter.java` &mdash; Writes results to the output file.
  
- **output/**
  - `flow_log_summary_YYYYMMDD_HHmmss.txt` &mdash; Generated output file.


## Instructions to Compile and Run

### Prerequisites
- **Java Development Kit (JDK) 8+**
- **Apache Maven**

### Clone the Repository
First, clone the repository to your local machine using Git:

**git clone https://github.com/sohamthakur/illumio.git**

### Compile the Program
1. Navigate to the project root directory (where `pom.xml` is located).
2. Run the following Maven command to compile:
    ```bash
    mvn clean compile
    ```

### Run the Program
1. Ensure `logs.txt` and `lookup.csv` are placed inside the `data/` directory.
2. Execute the program using Maven:
    ```bash
    mvn exec:java -Dexec.args="data/logs.txt data/lookup.csv"
    ```
3. The output file will be saved in the `output/` directory with a name like `flow_log_summary_YYYYMMDD_HHmmss.txt`.

## Testing

### Test Cases
- **Basic Matching**: Verified correct matching and tagging with sample logs and lookup table.
- **Unknown Protocol Handling**: Ensured entries with protocol numbers not in `ProtocolMapper` are marked as `"unknown"`.
- **Malformed Entries**: Confirmed lines with fewer than 13 fields are skipped with warnings.
- **Large File Handling**: Successfully processed flow logs up to 10MB and lookup tables with 10,000 mappings.

### How to Test
1. Place the flow log (`logs.txt`) and lookup table (`lookup.csv`) in the `data/` directory.
2. Run the program as described above.
3. Verify the output in the `output/` directory matches expected results.

## Notes
- Entries with no matching `(dstport, protocol)` in the lookup table are tagged as `"Untagged"`.
- Protocol mapping is currently limited to common protocols (`TCP`, `UDP`, `ICMP`). Unrecognized protocols are marked as `"unknown"`.
- Ensure that the lookup table and flow logs are free from extra spaces or incorrect formatting to prevent parsing issues.

---
