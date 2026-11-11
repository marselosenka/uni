DELETE FROM plan_table;
COMMIT;

ALTER SESSION SET optimizer_mode = ALL_ROWS;
ALTER SESSION SET statistics_level = ALL;

SET LINESIZE 200
SET PAGESIZE 1000
SET TIMING ON

EXPLAIN PLAN SET STATEMENT_ID = 'T3A_JOIN' FOR
SELECT DISTINCT m.movieTitle
FROM movie m, plays pl, people p
WHERE m.movieID = pl.movieID
  AND pl.personID = p.personID
  AND p.birthYear > 1985;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY(NULL, 'T3A_JOIN', 'ADVANCED'));

SELECT /*+ GATHER_PLAN_STATISTICS */
       COUNT(*)
FROM (
  SELECT DISTINCT m.movieTitle
  FROM movie m, plays pl, people p
  WHERE m.movieID = pl.movieID
    AND pl.personID = p.personID
    AND p.birthYear > 1985
);

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));