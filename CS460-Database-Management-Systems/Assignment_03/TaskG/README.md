# Task G — Non-blocking queue with SKIP LOCKED

**Points:** 10

Insert at least 5 rows into pending_transfer (state = 'NEW'). Implement:

```sql
PROCEDURE process_next_transfer (p_processed OUT INTEGER);
```

It must:

1. Pick one row from pending_transfer where state = 'NEW' using Oracle-compatible locking syntax (this requires Oracle 12c or later, which all current Oracle XE 21c and Oracle Database Free 23ai releases satisfy). Prefer the form below, where FETCH FIRST appears before FOR UPDATE SKIP LOCKED:

```sql
SELECT pt_id, src_iban, dst_iban, amount
INTO v_pt_id, v_src, v_dst, v_amt
FROM pending_transfer
WHERE state = 'NEW'
ORDER BY pt_id
FETCH FIRST 1 ROW ONLY
FOR UPDATE SKIP LOCKED;
```

2. Call transfer_funds for that row.

3. Update the row’s state to 'DONE' or 'FAILED' accordingly and COMMIT.

4. Return the pt_id it processed in p_processed, or NULL if the queue is empty.

### Demonstration

Open two sessions, in both call process_next_transfer in a tight loop until the queue is empty. Show that the two workers never block each other and never process the same row twice. Repeat the experiment with FOR UPDATE (no SKIP LOCKED) and show that Session 2 blocks instead.
