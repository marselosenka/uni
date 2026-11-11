# Task F — Isolation levels and the lost-update problem

**Points:** 15

Open two sessions to demonstrate the following — for each include exact statements run in each session, in order, and the final DB state.

1. Lost-update without locking. Both sessions execute, naively: `SELECT balance FROM accounts WHERE iban = :x; -- (compute balance - 100 in client) UPDATE accounts SET balance = :new_balance WHERE iban = :x; COMMIT;` under the default READ COMMITTED isolation level. Show how one update is lost.

2. Fix using SELECT … FOR UPDATE — repeat the experiment, now with the row locked. Show the second session blocks until the first commits, and no update is lost.

3. SERIALIZABLE behaviour. Re-run the experiment with ALTER SESSION SET ISOLATION_LEVEL = SERIALIZABLE in both sessions. Show that the second session receives ORA-08177: can't serialize access for this transaction on the conflicting DML statement or at transaction completion, depending on the exact statement order. Explain when Oracle raises ORA-08177 instead of simply blocking.

4. Briefly compare the three approaches: lost-update risk, blocking behaviour, throughput, ease of programming.
