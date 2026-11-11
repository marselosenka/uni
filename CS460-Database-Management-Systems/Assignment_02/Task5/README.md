# Task 5 — Function-Based and Invisible Indexes

**Points:** 10

Standard B+-tree indexes cannot be used by queries that apply a function to the indexed column. Function-based indexes solve this. Invisible indexes allow what-if experimentation without affecting the live optimizer.

## A. Function-based index

Consider the following query:

```sql
SELECT personName, birthYear
FROM   people
WHERE UPPER(personName) LIKE 'JOHN%';
```

Without any index on UPPER(personName), capture the execution plan. Then create a function-based index, regather stats, and re-execute:

```sql
CREATE INDEX idx_people_upper_name ON people (UPPER(personName));
EXEC DBMS_STATS.GATHER_TABLE_STATS(USER, 'PEOPLE', cascade => TRUE);
```

Does the optimizer use the new index? What must be true of the query predicate (and of the session’s NLS settings) for the function-based index to be applicable?

## B. Invisible index for what-if analysis

The plays(movieID) index created in Set-up is normally visible; for this part drop it and replace it with an invisible version: DROP INDEX idx_plays_movieid;

```sql
CREATE INDEX idx_plays_movieID_personID ON plays(movieID, personID) INVISIBLE;
EXEC DBMS_STATS.GATHER_INDEX_STATS(USER, 'IDX_PLAYS_MOVIEID_PERSONID');
```

Run the probe query and verify that the optimizer does not use the index by default:

```sql
-- Replace <MID> with a movieID that actually exists in plays, e.g. the result of
--   SELECT movieID FROM plays FETCH FIRST 1 ROWS ONLY;
-- Treat movieID as NUMBER; if your schema defines it as VARCHAR2, enclose the
-- literal in single quotes.
SELECT pl.personID
FROM   plays pl
WHERE pl.movieID = 46790;           -- numeric form
-- WHERE pl.movieID = '0046790';    -- string form (use this if movieID is VARCHAR2)
```

Now enable invisible indexes at the session level and re-run:

```sql
ALTER SESSION SET optimizer_use_invisible_indexes = TRUE;
-- re-execute the query and capture the new plan
```

Does the invisible index improve the plan? What is the practical value of invisible indexes in a production environment, and under what circumstances would you make an invisible index visible?

Bonus Task — Adaptive Query Optimization                                                              5 points Oracle 12c and later support Adaptive Query Optimization: the optimizer can switch between plan alternatives at runtime based on actual row counts. Some adaptive features are available in XE; others are restricted. Enable adaptive plans and statistics at the session level and execute the query below:

```sql
ALTER SESSION SET optimizer_adaptive_plans      = TRUE;
ALTER SESSION SET optimizer_adaptive_statistics = TRUE;
SELECT m.year, p.birthYear, COUNT(*) AS combinations
FROM   movie m, plays pl, people p
WHERE m.movieID    = pl.movieID
  AND pl.personID = p.personID
  AND m.year       > 1990
  AND p.birthYear > 1985
GROUP BY m.year, p.birthYear
ORDER BY m.year, p.birthYear;
```

Inspect the run with:

```sql
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST +ADAPTIVE'));
```

## Answer

- At which step is the optimizer’s cardinality estimate furthest from the actual row count?
- Does the plan show an adaptive join (a final join method different from the originally planned one)? If yes, describe the switch; if no, explain why the conditions for an adaptive switch were not met.
- What does this experiment tell us about the quality of single-column statistics when predicates on multiple columns interact? If adaptive features are unavailable or disabled on your build of XE, describe what adaptive query optimization is and the conditions under which it would be triggered, citing the official documentation and course materials.
