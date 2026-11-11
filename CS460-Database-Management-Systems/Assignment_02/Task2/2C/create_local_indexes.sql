SET TIMING ON
SET LINESIZE 200
SET PAGESIZE 1000

CREATE INDEX idx_movie_pre1950_year
ON movie_pre1950(year);

CREATE INDEX idx_movie_1950s_year
ON movie_1950s(year);

CREATE INDEX idx_movie_1960s_year
ON movie_1960s(year);

CREATE INDEX idx_movie_1970s_year
ON movie_1970s(year);

CREATE INDEX idx_movie_1980s_year
ON movie_1980s(year);

CREATE INDEX idx_movie_1990s_year
ON movie_1990s(year);

CREATE INDEX idx_movie_2000s_year
ON movie_2000s(year);

CREATE INDEX idx_movie_2010_plus_year
ON movie_2010_plus(year);

BEGIN
  FOR r IN (
    SELECT table_name
    FROM user_tables
    WHERE table_name IN (
      'MOVIE_PRE1950',
      'MOVIE_1950S',
      'MOVIE_1960S',
      'MOVIE_1970S',
      'MOVIE_1980S',
      'MOVIE_1990S',
      'MOVIE_2000S',
      'MOVIE_2010_PLUS'
    )
  ) LOOP
    DBMS_STATS.GATHER_TABLE_STATS(USER, r.table_name, cascade => TRUE);
  END LOOP;
END;
/

-- verify
SELECT index_name, table_name
FROM user_indexes
WHERE index_name LIKE 'IDX_MOVIE_%_YEAR'
ORDER BY table_name, index_name;