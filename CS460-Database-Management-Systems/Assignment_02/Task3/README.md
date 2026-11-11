# Task 3 — Query Rewriting and Optimizer Transformations

**Points:** 20

This task investigates how logically equivalent query formulations can lead to very different execution plans due to the optimizer’s parsing and transformation rules.

## A. Subquery vs. Join

Under ALL_ROWS, compare execution plans and execution times for the following two semantically equivalent queries:

```sql
-- Version 1: nested IN subqueries
SELECT movieTitle
FROM   movie
WHERE movieID IN (
  SELECT movieID FROM plays
  WHERE personID IN (
    SELECT personID FROM people
    WHERE birthYear > 1985
  )
);
-- Version 2: explicit JOIN
SELECT DISTINCT m.movieTitle
FROM   movie m, plays pl, people p
WHERE m.movieID    = pl.movieID
  AND pl.personID = p.personID
  AND p.birthYear > 1985;
```

Does the optimizer transform the subquery form into the join form automatically? Inspect the plan for signs of subquery unnesting (the Outline section of DBMS_XPLAN.DISPLAY with FORMAT => 'ADVANCED' is useful here). Is there a measurable performance difference?

## B. EXISTS vs. IN

Compare the following two queries:

```sql
-- EXISTS
SELECT m.movieTitle
FROM   movie m
WHERE EXISTS (
  SELECT 1 FROM plays pl
  WHERE pl.movieID = m.movieID
    AND pl.personID IN (
      SELECT personID FROM people WHERE birthYear = 1970)
);
-- IN
SELECT m.movieTitle
FROM   movie m
WHERE m.movieID IN (
  SELECT pl.movieID FROM plays pl
  WHERE pl.personID IN (
    SELECT personID FROM people WHERE birthYear = 1970)
);
```

Compare execution plans. Does Oracle treat them differently? Under what conditions does EXISTS outperform IN (or vice versa)? Explain in terms of short-circuit semantics and optimizer transformations (semi-join, unnest, pushed predicates).

## C. Regular view vs. inline view vs. materialized view

Create one regular view and one materialized view over people:

```sql
CREATE OR REPLACE VIEW active_people AS
  SELECT personID, personName, birthYear
  FROM   people
  WHERE birthYear BETWEEN 1960 AND 1990;
CREATE MATERIALIZED VIEW mv_active_people
  BUILD IMMEDIATE
  REFRESH COMPLETE ON DEMAND AS
  SELECT personID, personName, birthYear
  FROM   people
  WHERE birthYear BETWEEN 1960 AND 1990;
EXEC DBMS_STATS.GATHER_TABLE_STATS(USER, 'MV_ACTIVE_PEOPLE');
```

Execute the three logically equivalent formulations below and compare plans and execution times:

```sql
-- (1) Using the regular view
SELECT ap.personName, m.movieTitle
FROM   active_people ap, plays pl, movie m
WHERE ap.personID = pl.personID
  AND pl.movieID = m.movieID;
-- (2) Using an inline view
SELECT ap.personName, m.movieTitle
FROM   (SELECT personID, personName
        FROM   people
        WHERE birthYear BETWEEN 1960 AND 1990) ap,
       plays pl, movie m
WHERE ap.personID = pl.personID
  AND pl.movieID = m.movieID;
-- (3) Using the materialized view directly
SELECT ap.personName, m.movieTitle
FROM   mv_active_people ap, plays pl, movie m
WHERE ap.personID = pl.personID
  AND pl.movieID = m.movieID;
```

Does Oracle merge the regular view into the enclosing query (view merging) or materialise it as a temporary result? Look for the VIEW keyword in the plan. How does the MV version differ, given that it is a physical table? Briefly note why automatic query rewrite against mv_active_people (where Oracle would silently rewrite formulation (1) to access the MV) is not available in XE.
