@?/rdbms/admin/utlxplan.sql
TRUNCATE TABLE plan_table;

ALTER SESSION SET optimizer_mode = ALL_ROWS;
ALTER SESSION SET statistics_level = ALL;

SET LINESIZE 200
SET PAGESIZE 1000
SET TIMING ON

EXEC DBMS_STATS.GATHER_TABLE_STATS(USER, 'PEOPLE', cascade => TRUE);

SELECT table_name, num_rows, blocks, avg_row_len, last_analyzed
FROM user_tables
WHERE table_name = 'PEOPLE';

EXPLAIN PLAN SET STATEMENT_ID = 'T1D_RESTORED' FOR
SELECT pl.movieID
FROM people p, plays pl
WHERE p.personID = pl.personID
  AND p.birthYear > 1990;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY(NULL, 'T1D_RESTORED', 'ALL'));

SELECT /*+ GATHER_PLAN_STATISTICS RESTORED_STATS_RUN */
       pl.movieID
FROM people p, plays pl
WHERE p.personID = pl.personID
  AND p.birthYear > 1990;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));