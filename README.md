# illumio
Overview
This project implements a Java program that parses a flow log file and maps each entry to a tag based on a lookup table. The lookup table contains mappings of destination ports and protocols to tags. The program generates an output file that contains:

Tag Counts: Count of matches for each tag.
Port/Protocol Combination Counts: Count of matches for each unique port/protocol combination.
Assumptions Made
Flow Log Format: The flow log is in AWS VPC Flow Log format, with at least 13 columns, where:

Column 6 represents the destination port.
Column 7 represents the protocol number.
Version 2 Log Format: The program only supports version 2 of the AWS flow log format. It assumes all lines in the flow log follow this version format. Any malformed lines or lines with fewer than 13 fields will be skipped and not processed.

Lookup Table Format: The lookup table is a CSV file with the following columns:

dstport (integer, representing destination port)
protocol (string, representing the protocol in lowercase, e.g., tcp, udp, icmp)
tag (string, representing the tag)
Protocol Mapping: The program assumes a predefined mapping of protocol numbers to names:

1 -> icmp
6 -> tcp
17 -> udp
Any protocol numbers not in this predefined list will be mapped to "unknown".
Case Sensitivity: All protocol names in the lookup table are handled in a case-insensitive manner.

Untagged Entries: If no tag is found for a particular port/protocol combination, the entry will be tagged as "Untagged".

Output File Naming: The output file is created with the current date and time appended to its filename to ensure uniqueness.

Instructions to Compile and Run the Program
Prerequisites
Java Development Kit (JDK): Ensure you have JDK 8 or later installed.
Apache Maven: Used for project build and dependency management.
Compile the Program
Clone or download the project from the repository.
Navigate to the root directory of the project where the pom.xml file is located.
Run the following command to compile the project:
bash
Copy code
mvn clean compile
Run the Program
Ensure the flow log file and lookup table are in the appropriate directories (e.g., data/logs.txt and data/lookup.csv).

Use the following command to run the program with mvn:

bash
Copy code
mvn exec:java -Dexec.args="data/logs.txt data/lookup.csv"
Replace the file paths with the actual paths to your flow log and lookup table if necessary.

The output file will be generated in the output directory, with a name like flow_log_summary_YYYYMMDD_HHmmss.txt.

Sample Output Format
plaintext
Copy code
Tag Counts:
sv_P2, 1
sv_P1, 2
sv_P4, 1
email, 3
Untagged, 9

Port/Protocol Combination Counts:
443,tcp, 1
23,tcp, 1
110,tcp, 1
143,tcp, 1
993,tcp, 1
1024,tcp, 1
80,tcp, 1
25,tcp, 1
Testing
Test Cases Executed:
Basic Flow Log and Lookup Table Matching:

Tested with sample flow log and lookup table as provided in the problem statement to ensure correct tagging and counting.
Unknown Protocol Handling:

Entries in the flow log with unknown protocol numbers (not present in the ProtocolMapper) are tagged as "unknown".
Malformed Flow Log Entries:

Tested with malformed lines in the flow log file (e.g., fewer than 13 fields). The program correctly skips such lines and processes valid ones.
Empty Lookup Table:

Tested with an empty lookup table. The program correctly tags all entries as "Untagged".
Large File Handling:

Tested the program with a 10MB flow log file and a lookup table containing 10,000 mappings. The program performed as expected.
How to Test
Run the Program: Follow the instructions to run the program with different test cases.
Verify Output: Check the generated output file to ensure the results are as expected.
Edge Cases Handled
Malformed flow log entries are skipped with a warning.
Missing or unknown protocol numbers are handled and displayed as "unknown".
No tag is assigned when no matching port/protocol combination is found, and the entry is marked "Untagged".
Analysis of the Code
Modular Design:

The program follows a modular design pattern, with each class responsible for a specific functionality (e.g., parsing, processing, output writing). This allows for easy maintenance and extensibility.
Scalability:

The program efficiently handles large flow log files and lookup tables. It uses buffered readers and writers to manage file I/O, ensuring scalability for large datasets.
Error Handling:

The program is designed to skip over malformed entries in the flow log or lookup table while still processing valid entries. This ensures robustness even in the presence of data inconsistencies.
Logging:

Basic logging has been implemented to notify users of skipped malformed lines in the flow log and lookup table.

