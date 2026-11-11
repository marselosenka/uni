# Task D — Autonomous-transaction audit log

**Points:** 15

Implement:

```sql
PROCEDURE log_transfer (
    p_src       IN VARCHAR2,
    p_dst       IN VARCHAR2,
    p_amount    IN NUMBER,
    p_fee       IN NUMBER,
    p_status    IN VARCHAR2,
    p_err_code IN NUMBER,
    p_err_msg   IN VARCHAR2
);
```

This procedure must use PRAGMA AUTONOMOUS_TRANSACTION and COMMIT at its end. Modify transfer_funds from task C to call log_transfer on every success and on every failure path (including inside WHEN OTHERS). In your report:

1. Demonstrate that on a failed transfer, the main transaction’s changes are rolled back, but the transfer_log row written by log_transfer persists.

2. Briefly show what would happen without PRAGMA AUTONOMOUS_TRANSACTION (you may simulate by removing the pragma from a copy of the procedure) — the log entry would be rolled back together with the failing transfer.

3. Explain in 3–4 sentences when autonomous transactions are appropriate and when they are dangerous (think: read consistency, deadlocks against the parent transaction, audit integrity).
