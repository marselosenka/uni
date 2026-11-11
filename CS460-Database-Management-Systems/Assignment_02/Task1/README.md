# Task 1 — Optimizer Modes and Plan Sensitivity

**Points:** 25

In this task you will systematically compare how the optimizer modes ALL_ROWS, FIRST_ROWS_n, and RULE affect plan selection, join order, and execution time for multi-table queries over this dataset.

## A. Baseline with ALL_ROWS

Set the optimizer mode to ALL_ROWS. Execute the following query and capture the execution plan (join order, join method, access paths) and execution time:

```sql
ALTER SESSION SET OPTIMIZER_MODE = ALL_ROWS;
SELECT /*+ ORDERED USE_HASH(p,pl) */ pl.movieID
FROM   people p, plays pl
WHERE p.personID = pl.personID
  AND p.birthYear > 1990;
```

Now execute the same query without the hint and compare. Which plan does the optimizer prefer naturally, and why?

## B. FIRST_ROWS_1 vs FIRST_ROWS_10 vs FIRST_ROWS_100

Switch to FIRST_ROWS_1, FIRST_ROWS_10, and FIRST_ROWS_100 in turn. For each mode, execute the query from (A) without hints. Record the execution plan and execution time, then answer:

- Does the join method change across these three sub-modes?
- Does the driving table change?

- Explain conceptually why FIRST_ROWS_n modes tend to favour Nested-Loop joins over Hash joins, and what the indexes present in Set-up enable here.

## C. RULE mode

Switch to RULE mode and execute the same query without hints.

```sql
ALTER SESSION SET OPTIMIZER_MODE = RULE;
```

Note: OPTIMIZER_MODE=RULE has been desupported since Oracle 10g. On modern releases (12c+) it may be ignored, silently fall back to CBO, or produce heuristic plans that disregard many post-9i features (bitmap/function-based indexes, hash joins, star transformation). Treat any observation under RULE as historical; report exactly what you see and clearly flag any fallback behaviour. Compare the resulting plan with those from (A) and (B). Does the plan use any of the available indexes? Why or why not?

## D. Dropping and Recreating Statistics

Under ALL_ROWS mode, delete the statistics for the people table and re-execute the query from (A) without hints:

```sql
ALTER SESSION SET OPTIMIZER_MODE = ALL_ROWS;
EXEC DBMS_STATS.DELETE_TABLE_STATS(USER, 'PEOPLE');
-- execute the Task 1A query again and capture the plan
EXEC DBMS_STATS.GATHER_TABLE_STATS(USER, 'PEOPLE', cascade => TRUE);
-- execute once more and confirm the original plan is restored
```

What plan does the optimizer choose when the statistics for people are absent? Explain the role of dynamic sampling and the risk of stale or missing statistics.

## E. Summary Comparison

Construct a comparison table in your report with one row per optimizer mode / sub-mode tested and columns for: join method, driving table, index(es) used, total execution time. Provide a written analysis of the trade-offs.
