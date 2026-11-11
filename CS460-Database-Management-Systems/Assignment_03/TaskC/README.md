# Task C — transfer_funds procedure with full error handling

**Points:** 25

Implement the procedure:

```sql
PROCEDURE transfer_funds (
    p_src_iban   IN accounts.iban%TYPE,
    p_dst_iban   IN accounts.iban%TYPE,
    p_amount     IN accounts.balance%TYPE,
    p_status     OUT VARCHAR2,           -- 'OK' or short error tag
    p_err_msg    OUT VARCHAR2
);
```

## Requirements

1. Business validation (raise meaningful, named user-defined exceptions for each):

- both accounts exist
- both accounts are ACTIVE
- source is not LOAN
- same currency
- amount > 0
- daily limit: the sum of today’s outgoing transfers from the source customer plus p_amount must not exceed customers.daily_limit. (Hint: query transfer_log for status = 'SUCCESS' rows from today.) Optional reflection (no extra points, but expected as a short paragraph in your report): does this check survive concurrent execution? Identify the time-of-check-to-time-of-use window between reading the daily total and inserting the new log row, and propose one fix — for example, locking the customer row with SELECT … FOR UPDATE at the start of the procedure, switching the session to SERIALIZABLE isolation, or applying application-level serialization through DBMS_LOCK.

2. Exception handling must cover:

- At least one predefined exception (e.g. NO_DATA_FOUND)
- At least two user-defined exceptions raised via RAISE and reported via RAISE_APPLICATION_ERROR(-20xxx, …)
- At least one exception declared with PRAGMA EXCEPTION_INIT mapping a specific Oracle error to a named exception (e.g. map ORA-02290 check constraint violated to e_check_violated).
- WHEN OTHERS as the last resort — but it must not swallow the error silently. Log SQLCODE, SQLERRM and DBMS_UTILITY.FORMAT_ERROR_BACKTRACE into p_err_msg, then ROLLBACK.

3. Diagnostic indicators. In your report, list which Oracle PL/SQL diagnostic mechanisms you used (SQLCODE, SQLERRM, DBMS_UTILITY.FORMAT_ERROR_BACKTRACE) and why each one — what does it tell you that the others don’t? Mention SQLCA/ORACA only if you deliberately use an Oracle precompiler environment; they are not required for ordinary PL/SQL execution in SQL Developer or sqlplus.

4. Use at least one SAVEPOINT internally to demonstrate partial rollback and to make sure no partial debit or credit remains pending after a validation or processing failure. You may compute the daily-limit check before the debit, which is usually the cleaner design, or after a debit if you intentionally want to demonstrate rollback to a savepoint. In either case, justify your savepoint placement.

Demonstrate the procedure by calling it for: (a) a happy path, (b) each of the named exceptions you defined, (c) one path that hits the WHEN OTHERS branch (e.g. cause an arithmetic overflow on purpose). Show the OUT parameter values and the resulting DB state in each case.
