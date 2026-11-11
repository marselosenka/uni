DELETE FROM plan_table;
COMMIT;

ALTER SESSION SET optimizer_mode = ALL_ROWS;
ALTER SESSION SET statistics_level = ALL;

SET LINESIZE 200
SET PAGESIZE 1000
SET TIMING ON


-- Create original year index if not already created
CREATE INDEX idx_movie_year ON movie(year);

EXEC DBMS_STATS.GATHER_TABLE_STATS(USER, 'MOVIE', cascade => TRUE);

EXPLAIN PLAN SET STATEMENT_ID = 'T2D_MOVIE_JOIN' FOR
SELECT m.year, COUNT(*) AS total_plays
FROM movie m, plays pl
WHERE m.movieID = pl.movieID
  AND m.year BETWEEN 1980 AND 1995
GROUP BY m.year
ORDER BY m.year;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY(NULL, 'T2D_MOVIE_JOIN', 'ALL'));

SELECT /*+ GATHER_PLAN_STATISTICS */
       m.year, COUNT(*) AS total_plays
FROM movie m, plays pl
WHERE m.movieID = pl.movieID
  AND m.year BETWEEN 1980 AND 1995
GROUP BY m.year
ORDER BY m.year;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));