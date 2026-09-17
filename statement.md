# Problem Statement & Scope


# Problem Statement
During humanitarian disasters, relief agencies encounter sever bandwidth limitations and network disruptins and  Traditional cloud-dependent inventory platforms fail under these conditions, leading to inefficient resource allocation and priority mismatches and untracked supply dispatces There is an urgent need for a lightweight offline-capable terminal ledger that can queue supply requests based on dynamic urgency weights and local disaster severity indices, Thats why i made this, the cli interaction is super sm,ooth.

# Scope of the Project
The **Disaster Relief Supply Distribution Ledger** is a standalone, terminal-based Java application designed for execution in low resource environments. The scope encompasses:
* local file-based persistence for inventory records.
* In memory priority queue evaluation for incoming relief requests.
* automated calculation of dispatch priority scores based on severity and urgency metrics.
* audit trail generation for administrative ledger tracking

# Target Users
* Emergency Field Offcers updating local supply levels
* logistics Managers handling resource dispatches.
* non-Governmental Organizations(NGOs) operating in disconnected/ low-bandwidth disater zones.

# High-Level Features
1. **Inventory Management Engine**: Direct CRUD access to stock categories (Medical, Food, Shelter) with automatic CSV state persistence.
2. **Priority-Queue Request Dispatcher**: Algorthmic scoring algorithm prioritizing emergency requests ($Score = Severity \times Urgency$) over FIFO processing, I think it is a smart solution here
3. **Persisted Audit System**: Timestamped tranaction logs for auditing disatched vs. rejected allocations