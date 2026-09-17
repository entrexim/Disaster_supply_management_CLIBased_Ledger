# Disaster Relief Supply Distribution Ledger
//

A superlightweight , terminal executable Java system designed for resorce allocation and priority dispatching in disconnected disaster zone.

## overview
This application provides local inventry persistence and queue-based resource dispatch processing without requiring remote database connections or heavy graphical dependencies.
//
## key Features
**zero-GUI command Line Interface**: Run directly within standard terminal shell environments, I think it reduce time, no need of mouse or anything like, can run on any type of device and specially made of low end devices.
**Algorithmic Priority Dispatch**: Utilizes Java's `PriorityQueue` to handle supply allocations dynamically
**CSV File Persistence**: Automatically syncs inventory states to `inventory_data.csv`
**Transaction Logging**: Records audit traces to `dispatch_audit.log`

## Technologies Used
 **language**: Java (JDK 17 or later recommended)
 **version Control**: Git & GitHub
 **i/O Storage**: Stadard Java File I/O (`BufferedReader`, `BufferedWriter`, `PrintWrter`)

## setup & Execution Instructions

### Prerequisites
Ensure Java Development Kit (JDK) is installed and available in your shell environment path:
```bash
java -version
javac -version

###
//end