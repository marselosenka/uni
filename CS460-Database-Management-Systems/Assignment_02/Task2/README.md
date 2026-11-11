# Task 2 — Manual Range Partitioning and Partition Pruning

**Points:** 20

Native table partitioning is an Enterprise-Edition option and is not available in Oracle XE. The same optimizer behaviour — partition pruning based on predicates on the partitioning key — can be observed by building a manual partitioned design: a set of CHECK-constrained base tables exposed through a UNION ALL view. Oracle’s optimizer recognises the CHECK constraints and eliminates branches that cannot contribute rows to the result, which is the core idea of partition pruning.

### Hint

Keep the original movie table in place; build the manual-partition design as a separate set of tables plus a view. This lets you compare runtime behaviour side by side.

## A. Create the manual partitions

Create one table per year range, each with a CHECK constraint on year, and load movie data into the appropriate table. Then expose them through a single UNION ALL view:

```sql
-- One table per "partition" with an explicit CHECK constraint
CREATE TABLE movie_pre1950   AS SELECT * FROM movie WHERE 1=0;
ALTER TABLE movie_pre1950    ADD CONSTRAINT chk_pre1950   CHECK (year <                       1950)
ENABLE VALIDATE;
CREATE TABLE movie_1950s            AS SELECT * FROM movie WHERE 1=0;
ALTER TABLE movie_1950s             ADD CONSTRAINT chk_1950s     CHECK (year >= 1950 AND year
< 1960)    ENABLE VALIDATE;
CREATE TABLE movie_1960s            AS SELECT * FROM movie WHERE 1=0;
ALTER TABLE movie_1960s             ADD CONSTRAINT chk_1960s     CHECK (year >= 1960 AND year
< 1970)    ENABLE VALIDATE;
CREATE TABLE movie_1970s            AS SELECT * FROM movie WHERE 1=0;
ALTER TABLE movie_1970s             ADD CONSTRAINT chk_1970s     CHECK (year >= 1970 AND year
< 1980)    ENABLE VALIDATE;
CREATE TABLE movie_1980s            AS SELECT * FROM movie WHERE 1=0;
ALTER TABLE movie_1980s             ADD CONSTRAINT chk_1980s     CHECK (year >= 1980 AND year
< 1990)    ENABLE VALIDATE;
CREATE TABLE movie_1990s            AS SELECT * FROM movie WHERE 1=0;
ALTER TABLE movie_1990s             ADD CONSTRAINT chk_1990s     CHECK (year >= 1990 AND year
< 2000)    ENABLE VALIDATE;
CREATE TABLE movie_2000s            AS SELECT * FROM movie WHERE 1=0;
ALTER TABLE movie_2000s             ADD CONSTRAINT chk_2000s     CHECK (year >= 2000 AND year
< 2010)    ENABLE VALIDATE;
CREATE TABLE movie_2010_plus AS SELECT * FROM movie WHERE 1=0;
ALTER TABLE movie_2010_plus ADD CONSTRAINT chk_2010_plus CHECK (year >= 2010)
ENABLE VALIDATE;
-- Load each partition
INSERT /*+ APPEND */ INTO movie_pre1950              SELECT * FROM movie WHERE year < 1950;
INSERT /*+ APPEND */ INTO movie_1950s                SELECT * FROM movie WHERE year >= 1950 AND
year < 1960;
INSERT /*+ APPEND */ INTO movie_1960s     SELECT * FROM movie WHERE year >= 1960 AND
year < 1970;
INSERT /*+ APPEND */ INTO movie_1970s     SELECT * FROM movie WHERE year >= 1970 AND
year < 1980;
INSERT /*+ APPEND */ INTO movie_1980s     SELECT * FROM movie WHERE year >= 1980 AND
year < 1990;
INSERT /*+ APPEND */ INTO movie_1990s     SELECT * FROM movie WHERE year >= 1990 AND
year < 2000;
INSERT /*+ APPEND */ INTO movie_2000s     SELECT * FROM movie WHERE year >= 2000 AND
year < 2010;
INSERT /*+ APPEND */ INTO movie_2010_plus SELECT * FROM movie WHERE year >= 2010;
COMMIT;
-- Unified view
CREATE OR REPLACE VIEW movie_part AS
  SELECT * FROM movie_pre1950
  UNION ALL SELECT * FROM movie_1950s
  UNION ALL SELECT * FROM movie_1960s
  UNION ALL SELECT * FROM movie_1970s
  UNION ALL SELECT * FROM movie_1980s
  UNION ALL SELECT * FROM movie_1990s
  UNION ALL SELECT * FROM movie_2000s
  UNION ALL SELECT * FROM movie_2010_plus;
-- Regather stats on every base table
BEGIN
  FOR r IN (SELECT table_name FROM user_tables
            WHERE table_name LIKE 'MOVIE_%') LOOP
    DBMS_STATS.GATHER_TABLE_STATS(USER, r.table_name, cascade => TRUE);
  END LOOP;
END;
/
```

## B. Observe partition pruning

Under ALL_ROWS, capture the execution plan of each of the following queries against movie_part. For each plan, identify which base tables Oracle actually reads (look for FILTER predicates that eliminate branches, or NULL IS NOT NULL filters on branches the optimizer has pruned).

```sql
SELECT movieTitle FROM movie_part WHERE year = 1975;
SELECT movieTitle FROM movie_part WHERE year BETWEEN 1965 AND 1975;
SELECT movieTitle FROM movie_part WHERE year > 1990;
SELECT movieTitle FROM movie_part WHERE movieTitle LIKE 'The%';
```

Which queries benefit from pruning? Why does the last query not prune any branch?

## C. Local indexes on the partitions

Create a B+-tree index on year on every base table (this is the manual analogue of a local partitioned index):

```sql
CREATE INDEX idx_movie_pre1950_year            ON movie_pre1950(year);
CREATE INDEX idx_movie_1950s_year               ON movie_1950s(year);
CREATE INDEX idx_movie_1960s_year               ON movie_1960s(year);
CREATE INDEX idx_movie_1970s_year               ON movie_1970s(year);
CREATE INDEX idx_movie_1980s_year               ON movie_1980s(year);
CREATE INDEX idx_movie_1990s_year              ON movie_1990s(year);
CREATE INDEX idx_movie_2000s_year              ON movie_2000s(year);
CREATE INDEX idx_movie_2010_plus_year          ON movie_2010_plus(year);
```

Re-run the queries from (B) and compare the execution plans. Does the combination of pruning plus per-partition index access produce plans that are strictly better than querying the original (unpartitioned) movie table with idx_movie_year? Quantify.

## D. Join against plays

Execute the following join against movie_part and then against the original movie table. Compare plans and execution times:

```sql
SELECT m.year, COUNT(*) AS total_plays
FROM   movie_part m, plays pl
WHERE m.movieID = pl.movieID
  AND m.year BETWEEN 1980 AND 1995
GROUP BY m.year
ORDER BY m.year;
```

Discuss how the manual partitioning changes resource usage for range predicates on year, and where it helps or hurts compared with a single heap table plus idx_movie_year. Mention at least two drawbacks of the manual-partition design (insert complexity, constraint maintenance, plan complexity) that native partitioning would hide.
