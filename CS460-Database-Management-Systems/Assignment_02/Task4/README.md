# Task 4 — Join Methods and Plan Forcing via Hints

**Points:** 20

Parallel query execution is not available in Oracle XE, so this task focuses on the other main lever for controlling runtime behaviour: forcing the optimizer to choose a specific join method and a specific join order via hints. You will compare Nested-Loop, Hash, and Sort-Merge joins on the same query and analyse the trade-offs.

## Setup

Useful hints for this task:

- USE_NL(t1 t2) / USE_HASH(t1 t2) / USE_MERGE(t1 t2) — force a join method
- ORDERED — use the table order listed in the FROM clause as the join order
- LEADING(t1 t2 ...) — force a specific join order independently of FROM
- INDEX(t idx) / NO_INDEX(t idx) / FULL(t) — force or forbid an access path Use SET TIMING ON to measure elapsed time, and DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST') to inspect actual row counts after execution.

## A. Forced join methods on the same query

Use the following query as a probe:

```sql
SELECT p.birthYear, COUNT(DISTINCT pl.movieID) AS movies_played
FROM   people p, plays pl
WHERE p.personID = pl.personID
  AND p.birthYear BETWEEN 1960 AND 1980
GROUP BY p.birthYear
ORDER BY p.birthYear;
```

Run the query four times:

- Without any hint (let the optimizer decide).
- With /*+ LEADING(p pl) USE_NL(pl) INDEX(pl idx_plays_personid) */ to force an index-driven nested-loop join.
- With /*+ LEADING(p pl) USE_HASH(pl) */ to force a hash join.
- With /*+ LEADING(p pl) USE_MERGE(pl) */ to force a sort-merge join. For each run, record: the execution plan, the join method, the driving table, the access paths on each table, and the execution time. Identify which method the optimizer selects natively and justify the choice in terms of the number of rows expected after the birthYear filter.

## B. Driving table and join order

Keeping the hash-join variant from (A), run it twice: once with LEADING(p pl) and once with LEADING(pl p). Compare the two plans.

- Which table becomes the build side of the hash join in each case?
- How does the choice of build side affect memory usage and execution time?
- Given the row counts (people: ~289k, plays: ~811k, and the filter birthYear BETWEEN 1960 AND 1980), which order should the optimizer prefer and why?

## C. Forcing access paths

On the following simpler query:

```sql
SELECT COUNT(*) FROM people WHERE birthYear = 1975;
```

capture three plans:

- Natural (let the optimizer choose).
- With /*+ FULL(people) */ to force a full table scan.
- With /*+ INDEX(people idx_people_birthyear) */ to force an index range scan. Report actual elapsed times and logical I/O (STATISTICS output or the ‘Buffers’ column of DBMS_XPLAN with ALLSTATS LAST). For which selectivity would each access path be preferable? Relate your answer to the well-known ~5–10 % selectivity rule of thumb for B+-tree access.

## D. Trade-offs — discussion

Answer the following conceptually, citing the measurements from (A)–(C):

- When is a Nested-Loop join preferable? What role does the inner-table index play?
- When is a Hash join preferable? What determines whether the build side fits in memory, and what happens otherwise?
- When is a Sort-Merge join preferable? In which circumstances can it beat a Hash join?
- What happens to all three join methods when table statistics are missing or stale? Refer back to Task 1(D) for your evidence.
