# Task H — MySQL comparison

**Points:** Bonus: 20

Using the latest MySQL Community Server with InnoDB and MySQL Workbench CE, repeat tasks A, B, C, E, F and G. In your report, contrast the two systems on the following points (be specific — quote actual code or error messages):

```sql
 Topic                       Oracle XE                   MySQL 8.x
 Exception block syntax      EXCEPTION WHEN … THEN       DECLARE … HANDLER FOR
                             …                           …
 Diagnostic area             SQLCODE, SQLERRM            GET DIAGNOSTICS,
                                                         SQLSTATE
 Autonomous transactions     PRAGMA                      not supported — what’s
                             AUTONOMOUS_TRANSACTION      your workaround?
 NOWAIT / SKIP LOCKED        both supported              both supported in
                                                         modern MySQL
                                                         8.x/InnoDB locking reads
                                                         — verify on your
                                                         installed version
 Deadlock detection          victim chosen by Oracle     InnoDB victim chosen by
                                                         row-modification weight
Topic                       Oracle XE                    MySQL 8.x
Default isolation level     READ COMMITTED               REPEATABLE READ —
                                                         reproduce the lost-
                                                         update experiment under
                                                         both defaults
SERIALIZABLE semantics      snapshot + ORA-08177         locking-based in InnoDB
```

Conclude with one paragraph stating which system you found easier for each of: error handling, deadlock debugging, audit logging.
