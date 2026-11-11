SET SERVEROUTPUT ON;

PROMPT ===== FINAL QUEUE STATE =====
SELECT pt_id, src_iban, dst_iban, amount, state
FROM pending_transfer
ORDER BY pt_id;

PROMPT ===== G FINAL ACCOUNTS =====
SELECT iban, balance, currency, status, account_type
FROM accounts
WHERE iban IN (
    'GR1601101250000000012300695',
    'GR9601101250000000012300696'
)
ORDER BY iban;

PROMPT ===== TRANSFER LOG =====
SELECT log_id, src_iban, dst_iban, amount, fee, status, err_code, err_msg
FROM transfer_log
ORDER BY log_id;