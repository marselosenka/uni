# CS460 — Database Management Systems

![Oracle Database](https://img.shields.io/badge/Oracle_Database-F80000?logo=oracle&logoColor=white)
![SQL](https://img.shields.io/badge/SQL-4479A1)
![PL/SQL](https://img.shields.io/badge/PL%2FSQL-F80000?logo=oracle&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)

Coursework repository for **CS460 — Database Management Systems**, Spring 2026.

This repository contains Assignment 2 and Assignment 3 materials (I can't find Assigment 1 :( )), task-specific instructions, SQL/PL/SQL implementations, and final reports.

## Repository structure

```text
CS460/
├── Assignment_02/
│   ├── Task1/ ... Task5/
│   ├── report/
│   └── CS460_Assignment2_2026.pdf
└── Assignment_03/
    ├── TaskA/ ... TaskH/
    ├── report/
    └── CS460_Assignment3_2026.pdf
```

Each task folder contains a `README.md` with the relevant assignment instructions. Where applicable, the folder also contains the SQL or PL/SQL files used to complete the task.

## Assignment 2 — Query Optimization


Assignment 2 investigates Oracle query-execution optimization using execution plans, optimizer modes, statistics, indexing strategies, query rewrites, and join methods.

| Task | Topic | Instructions |
| --- | --- | --- |
| 1 | Optimizer modes and plan sensitivity | [Task 1 README](Assignment_02/Task1/README.md) |
| 2 | Manual range partitioning and partition pruning | [Task 2 README](Assignment_02/Task2/README.md) |
| 3 | Query rewriting and optimizer transformations | [Task 3 README](Assignment_02/Task3/README.md) |
| 4 | Join methods and plan forcing with hints | [Task 4 README](Assignment_02/Task4/README.md) |
| 5 | Function-based and invisible indexes | [Task 5 README](Assignment_02/Task5/README.md) |

Additional material:

- [Assignment 2 report folder](Assignment_02/report/)
- [Original Assignment 2 PDF](Assignment_02/CS460_Assignment2_2026.pdf)

## Assignment 3 — Transactions and Concurrency

**Due date:** 29 June 2026  
**Database:** Oracle Database Express Edition / Oracle Database Free

Assignment 3 develops a mini-banking system to explore PL/SQL error handling, transactions, savepoints, autonomous logging, locking, isolation levels, deadlock prevention, and concurrent queue processing.

| Task | Topic | Instructions |
| --- | --- | --- |
| A | Schema and seed data | [Task A README](Assignment_03/TaskA/README.md) |
| B | Transactions, savepoints, and error scenarios | [Task B README](Assignment_03/TaskB/README.md) |
| C | `transfer_funds` procedure and error handling | [Task C README](Assignment_03/TaskC/README.md) |
| D | Autonomous-transaction audit logging | [Task D README](Assignment_03/TaskD/README.md) |
| E | Deadlock prevention with `NOWAIT` and deterministic locking | [Task E README](Assignment_03/TaskE/README.md) |
| F | Isolation levels and the lost-update problem | [Task F README](Assignment_03/TaskF/README.md) |
| G | Non-blocking queue processing with `SKIP LOCKED` | [Task G README](Assignment_03/TaskG/README.md) |
| H | Bonus MySQL comparison | [Task H README](Assignment_03/TaskH/README.md) |

Additional material:

- [Assignment 3 report folder](Assignment_03/report/)
- [Original Assignment 3 PDF](Assignment_03/CS460_Assignment3_2026.pdf)

## Main technologies and concepts

- Oracle SQL and PL/SQL
- Cost-based query optimization and execution plans
- B-tree, bitmap, function-based, and invisible indexes
- Manual partitioning and partition pruning
- Nested-loop, hash, and sort-merge joins
- Stored procedures, functions, and exception handling
- Transactions, savepoints, and autonomous transactions
- Row locking with `NOWAIT`, `SKIP LOCKED`, and `FOR UPDATE`
- `READ COMMITTED` and `SERIALIZABLE` isolation
- Deadlock detection and prevention
- MySQL/InnoDB comparison

