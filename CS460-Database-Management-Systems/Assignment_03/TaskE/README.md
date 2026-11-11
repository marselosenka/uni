# Task E — Deadlock prevention with NOWAIT and deterministic order

**Points:** 15

Implement the function:

```sql
FUNCTION try_lock_account (p_iban IN accounts.iban%TYPE)
RETURN BOOLEAN;
```

It must attempt SELECT … FOR UPDATE NOWAIT on the row for p_iban, return TRUE if the lock is acquired, and FALSE if ORA-00054 (“resource busy and acquire with NOWAIT specified”) is raised. Any other exception must propagate. Remember that a row lock acquired inside the function is held by the surrounding session transaction until COMMIT or ROLLBACK; it is not released when the function returns. Then implement a calling procedure:

```sql
PROCEDURE safe_transfer (
    p_src   IN VARCHAR2,
    p_dst   IN VARCHAR2,
    p_amt   IN NUMBER
);
```

safe_transfer must:

1. Acquire locks on both accounts using try_lock_account, in deterministic order — always lock the lexicographically smaller IBAN first. This is the textbook deadlock-prevention technique.

2. If either lock fails, release the first lock (by ending the transaction with ROLLBACK) and report a clean message.

3. Otherwise, call transfer_funds from task C and COMMIT.

### Demonstration (required)

- Scenario E1 (no contention): call safe_transfer with no other session — must succeed.
- Scenario E2 (contention, NOWAIT triggers): in Session 1, run SELECT … FOR UPDATE on account X and do not commit. In Session 2, call safe_transfer involving X — show that try_lock_account returns FALSE and the transfer aborts cleanly (no error propagated to user).
- Scenario E3 (deadlock without ordering): write a deliberately broken version unsafe_transfer that locks in the order (p_src, p_dst). Run it from two sessions in opposite directions (A→B and B→A). Show the resulting ORA-00060 deadlock detection. Then demonstrate that safe_transfer with deterministic order makes the deadlock impossible.

### Recipe to reproduce the deadlock reliably

Random timing usually does not produce a cycle — you must interleave the two sessions step by step. Pick two existing accounts A and B (with A < B lexicographically) and run, in order:

1. Session 1: SELECT * FROM accounts WHERE iban = 'A' FOR UPDATE;

2. Session 2: SELECT * FROM accounts WHERE iban = 'B' FOR UPDATE;

3. Session 1: SELECT * FROM accounts WHERE iban = 'B' FOR UPDATE; — blocks, waiting for Session 2.

4. Session 2: SELECT * FROM accounts WHERE iban = 'A' FOR UPDATE; — closes the cycle. Within a few seconds Oracle detects the deadlock and raises ORA-00060 in one of the two sessions, rolling back the statement that closed the cycle (the other session keeps its locks). Then reproduce the same effect by calling unsafe_transfer from each session in opposite directions.

For each scenario, include screenshots of both sessions side by side.
