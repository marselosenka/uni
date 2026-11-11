SELECT /*+ GATHER_PLAN_STATISTICS LEADING(p pl) USE_NL(pl) INDEX(pl idx_plays_personid) */
       p.birthYear, COUNT(DISTINCT pl.movieID) AS movies_played
FROM people p, plays pl
WHERE p.personID = pl.personID
  AND p.birthYear BETWEEN 1960 AND 1980
GROUP BY p.birthYear
ORDER BY p.birthYear;

SELECT *
FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));