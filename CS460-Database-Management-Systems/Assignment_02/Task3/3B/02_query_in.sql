DELETE FROM plan_table;
COMMIT;

ALTER SESSION SET optimizer_mode = ALL_ROWS;
ALTER SESSION SET statistics_level = ALL;

SET LINESIZE 200
SET PAGESIZE 1000
SET TIMING ON

EXPLAIN PLAN SET STATEMENT_ID = 'T3B_IN' FOR
SELECT m.movieTitle
FROM movie m
WHERE m.movieID IN (
  SELECT pl.movieID
  FROM plays pl
  WHERE pl.personID IN (
    SELECT personID
    FROM people
    WHERE birthYear = 1970
  )
);

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY(NULL, 'T3B_IN', 'ADVANCED'));

SELECT /*+ GATHER_PLAN_STATISTICS */
       COUNT(*)
FROM (
  SELECT m.movieTitle
  FROM movie m
  WHERE m.movieID IN (
    SELECT pl.movieID
    FROM plays pl
    WHERE pl.personID IN (
      SELECT personID
      FROM people
      WHERE birthYear = 1970
    )
  )
);

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));