SET SERVEROUTPUT ON;

PROMPT ===== WITHOUT AUTONOMOUS: CLEAN LOG =====
DELETE FROM transfer_log;
COMMIT;

PROMPT ===== WITHOUT AUTONOMOUS: LOG BEFORE =====
SELECT * FROM transfer_log;

PROMPT ===== WITHOUT AUTONOMOUS: INSERT LOG ROW THEN ROLLBACK =====

BEGIN
    log_transfer_no_auto(
        P_SRC      => 'GR1601101250000000012300695',
        P_DEST     => 'GR5801101250000000012300700',
        P_AMOUNT   => 300,
        P_FEE      => 3,
        P_STATUS   => 'FAILED',
        P_ERR_CODE => -20004,
        P_ERR_MSG  => 'Source and destination accounts must have the same currency.'
    );

    ROLLBACK;
END;
/

PROMPT ===== WITHOUT AUTONOMOUS: LOG AFTER ROLLBACK =====
SELECT * FROM transfer_log;