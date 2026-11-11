CREATE INDEX idx_people_upper_name
ON people (UPPER(personName));

EXEC DBMS_STATS.GATHER_TABLE_STATS(USER, 'PEOPLE', cascade => TRUE);