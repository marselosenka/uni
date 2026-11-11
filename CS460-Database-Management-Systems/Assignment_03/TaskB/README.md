# Task B — Transactions, savepoints & error scenarios

**Points:** 15

Write an anonymous PL/SQL block (no procedure yet) that, inside a single transaction, transfers 300 EUR from account A to account B and additionally charges a fee of 1% of the transferred amount, debited from the source. Use a `SAVEPOINT` named `before_fee` placed after the main transfer but before the fee deduction, so that if the fee step fails, the main transfer can still be committed. Justify this design choice in 2–3 sentences. Demonstrate the behaviour of the transaction under each of the following error scenarios. For each scenario, show: (i) the exact SQL/PL/SQL you ran, (ii) the error/warning message produced, (iii) SELECT * FROM accounts before and after, (iv) whether an automatic rollback occurred, and (v) one sentence on why.

1. Source account does not exist (use a non-existing IBAN).

2. non_neg_balance CHECK violation — drain a source account that does not have enough funds.

3. Frozen-account business rule — attempt to debit a FROZEN account and demonstrate that the schema alone does not prevent this, because the valid_status constraint only checks that the status value is one of the allowed labels. Then show how your PL/SQL code explicitly prevents the debit by requiring the source and destination accounts to be ACTIVE.

4. Currency mismatch between source and destination — explain whether the schema alone catches this, and demonstrate.

5. account_type rule violation — attempt to debit a LOAN account as the source (this rule is your own; enforce it in code).

> **Hint.** Oracle does not auto-rollback the entire transaction on a statement-level error. Only the failing statement is rolled back; previously-successful statements remain pending until the session commits or rolls back. Make sure your demonstration distinguishes statement-level from transaction-level rollback.
