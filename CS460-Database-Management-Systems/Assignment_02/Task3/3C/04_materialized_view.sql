DELETE FROM plan_table;
COMMIT;

ALTER SESSION SET optimizer_mode = ALL_ROWS;
ALTER SESSION SET statistics_level = ALL;

SET LINESIZE 200
SET PAGESIZE 1000
SET TIMING ON

EXPLAIN PLAN SET STATEMENT_ID = 'T3C_MV' FOR
SELECT ap.personName, m.movieTitle
FROM mv_active_people ap, plays pl, movie m
WHERE ap.personID = pl.personID
  AND pl.movieID = m.movieID;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY(NULL, 'T3C_MV', 'ADVANCED'));

SELECT /*+ GATHER_PLAN_STATISTICS */
       COUNT(*)
FROM (
  SELECT ap.personName, m.movieTitle
  FROM mv_active_people ap, plays pl, movie m
  WHERE ap.personID = pl.personID
    AND pl.movieID = m.movieID
);

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));