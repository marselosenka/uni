
@?/rdbms/admin/utlxplan.sql
TRUNCATE TABLE plan_table;
ALTER SESSION SET optimizer_mode = RULE;
ALTER SESSION SET statistics_level = ALL;

-- Make output readable
SET LINESIZE 200
SET PAGESIZE 1000
SET TIMING ON


EXPLAIN PLAN
SET STATEMENT_ID = 'T1C_RULE'
FOR
SELECT pl.movieID
FROM people p, plays pl
WHERE p.personID = pl.personID
  AND p.birthYear > 1990;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY(NULL, 'T1C_RULE', 'ALL'));


SELECT /*+ GATHER_PLAN_STATISTICS */
       pl.movieID
FROM people p, plays pl
WHERE p.personID = pl.personID
  AND p.birthYear > 1990;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));


ALTER SESSION SET optimizer_mode = ALL_ROWS;