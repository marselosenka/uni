SET SERVEROUTPUT ON;

PROMPT ===== RESET QUEUE AND TEST DATA =====

ROLLBACK;

DELETE FROM pending_transfer;
DELETE FROM transfer_log;

UPDATE customers
SET daily_limit = 1000000
WHERE cust_id = 1;

UPDATE accounts
SET balance = 2000
WHERE iban = 'GR1601101250000000012300695';

UPDATE accounts
SET balance = 1500
WHERE iban = 'GR9601101250000000012300696';

COMMIT;

PROMPT ===== G: INSERT NEW PENDING TRANSFERS =====

INSERT INTO pending_transfer (pt_id, src_iban, dst_iban, amount, state)
VALUES (seq_pending_id.NEXTVAL, 'GR1601101250000000012300695', 'GR9601101250000000012300696', 1, 'NEW');

INSERT INTO pending_transfer (pt_id, src_iban, dst_iban, amount, state)
VALUES (seq_pending_id.NEXTVAL, 'GR1601101250000000012300695', 'GR9601101250000000012300696', 42, 'NEW');

INSERT INTO pending_transfer (pt_id, src_iban, dst_iban, amount, state)
VALUES (seq_pending_id.NEXTVAL, 'GR1601101250000000012300695', 'GR9601101250000000012300696', 67, 'NEW');

INSERT INTO pending_transfer (pt_id, src_iban, dst_iban, amount, state)
VALUES (seq_pending_id.NEXTVAL, 'GR1601101250000000012300695', 'GR9601101250000000012300696', 69, 'NEW');

INSERT INTO pending_transfer (pt_id, src_iban, dst_iban, amount, state)
VALUES (seq_pending_id.NEXTVAL, 'GR1601101250000000012300695', 'GR9601101250000000012300696', 99, 'NEW');

COMMIT;

PROMPT ===== QUEUE AFTER INSERT =====
SELECT * FROM pending_transfer ORDER BY pt_id;

PROMPT ===== ACCOUNTS BEFORE WORKERS =====
SELECT iban, balance, currency, status, account_type
FROM accounts
WHERE iban IN (
    'GR1601101250000000012300695',
    'GR9601101250000000012300696'
)
ORDER BY iban;