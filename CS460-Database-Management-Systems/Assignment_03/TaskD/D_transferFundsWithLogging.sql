SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE transfer_funds (
    P_SRC_IBAN   IN  accounts.iban%TYPE,
    P_DEST_IBAN  IN  accounts.iban%TYPE,
    P_AMOUNT     IN  accounts.balance%TYPE,
    P_STATUS     OUT VARCHAR2,
    P_ERR_MSG    OUT VARCHAR2
)
AS
    V_SRC_CUST_ID        accounts.cust_id%TYPE;
    V_SRC_CURRENCY       accounts.currency%TYPE;
    V_SRC_TYPE           accounts.account_type%TYPE;
    V_SRC_STATUS         accounts.status%TYPE;

    V_DEST_CURRENCY      accounts.currency%TYPE;
    V_DEST_STATUS        accounts.status%TYPE;

    V_DAILY_LIMIT        customers.daily_limit%TYPE;
    V_TODAY_TOTAL        NUMBER := 0;
    V_FEE                NUMBER := 0;

    E_INVALID_AMOUNT         EXCEPTION;
    E_ACCOUNT_NOT_ACTIVE     EXCEPTION;
    E_LOAN_SOURCE            EXCEPTION;
    E_CURRENCY_MISMATCH      EXCEPTION;
    E_DAILY_LIMIT_EXCEEDED   EXCEPTION;
    E_CHECK_VIOLATED         EXCEPTION;

    PRAGMA EXCEPTION_INIT(E_CHECK_VIOLATED, -2290);

BEGIN
    P_STATUS := NULL;
    P_ERR_MSG := NULL;

    IF P_AMOUNT <= 0 THEN
        RAISE E_INVALID_AMOUNT;
    END IF;

    V_FEE := P_AMOUNT * 0.01;

    SELECT cust_id, currency, account_type, status
    INTO V_SRC_CUST_ID, V_SRC_CURRENCY, V_SRC_TYPE, V_SRC_STATUS
    FROM accounts
    WHERE iban = P_SRC_IBAN;

    SELECT currency, status
    INTO V_DEST_CURRENCY, V_DEST_STATUS
    FROM accounts
    WHERE iban = P_DEST_IBAN;

    SELECT daily_limit
    INTO V_DAILY_LIMIT
    FROM customers
    WHERE cust_id = V_SRC_CUST_ID;

    IF V_SRC_STATUS <> 'ACTIVE' OR V_DEST_STATUS <> 'ACTIVE' THEN
        RAISE E_ACCOUNT_NOT_ACTIVE;
    END IF;

    IF V_SRC_TYPE = 'LOAN' THEN
        RAISE E_LOAN_SOURCE;
    END IF;

    IF V_SRC_CURRENCY <> V_DEST_CURRENCY THEN
        RAISE E_CURRENCY_MISMATCH;
    END IF;

    SELECT NVL(SUM(amount), 0)
    INTO V_TODAY_TOTAL
    FROM transfer_log
    WHERE src_iban = P_SRC_IBAN
      AND status = 'SUCCESS'
      AND TRUNC(logged_at) = TRUNC(SYSTIMESTAMP);

    IF V_TODAY_TOTAL + P_AMOUNT > V_DAILY_LIMIT THEN
        RAISE E_DAILY_LIMIT_EXCEEDED;
    END IF;

    SAVEPOINT before_transfer;

    UPDATE accounts
    SET balance = balance - P_AMOUNT
    WHERE iban = P_SRC_IBAN;

    UPDATE accounts
    SET balance = balance + P_AMOUNT
    WHERE iban = P_DEST_IBAN;

    UPDATE accounts
    SET balance = balance - V_FEE
    WHERE iban = P_SRC_IBAN;

    P_STATUS := 'OK';
    P_ERR_MSG := 'Transfer completed successfully.';

    log_transfer(
        P_SRC      => P_SRC_IBAN,
        P_DEST     => P_DEST_IBAN,
        P_AMOUNT   => P_AMOUNT,
        P_FEE      => V_FEE,
        P_STATUS   => 'SUCCESS',
        P_ERR_CODE => NULL,
        P_ERR_MSG  => NULL
    );

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        P_STATUS := 'ERR_NO_ACCOUNT';
        P_ERR_MSG := 'One of the accounts or the related customer does not exist. '
                     || 'SQLCODE=' || SQLCODE
                     || ', SQLERRM=' || SQLERRM;

        log_transfer(P_SRC_IBAN, P_DEST_IBAN, P_AMOUNT, V_FEE, 'FAILED', SQLCODE, P_ERR_MSG);

    WHEN E_INVALID_AMOUNT THEN
        ROLLBACK;
        P_STATUS := 'ERR_AMOUNT';
        P_ERR_MSG := 'Transfer amount must be greater than zero.';

        log_transfer(P_SRC_IBAN, P_DEST_IBAN, P_AMOUNT, V_FEE, 'FAILED', -20001, P_ERR_MSG);

    WHEN E_ACCOUNT_NOT_ACTIVE THEN
        ROLLBACK;
        P_STATUS := 'ERR_NOT_ACTIVE';
        P_ERR_MSG := 'Both source and destination accounts must be ACTIVE.';

        log_transfer(P_SRC_IBAN, P_DEST_IBAN, P_AMOUNT, V_FEE, 'FAILED', -20002, P_ERR_MSG);

        RAISE_APPLICATION_ERROR(-20002, P_ERR_MSG);

    WHEN E_LOAN_SOURCE THEN
        ROLLBACK;
        P_STATUS := 'ERR_LOAN_SRC';
        P_ERR_MSG := 'LOAN account cannot be used as transfer source.';

        log_transfer(P_SRC_IBAN, P_DEST_IBAN, P_AMOUNT, V_FEE, 'FAILED', -20003, P_ERR_MSG);

        RAISE_APPLICATION_ERROR(-20003, P_ERR_MSG);

    WHEN E_CURRENCY_MISMATCH THEN
        ROLLBACK;
        P_STATUS := 'ERR_CURRENCY';
        P_ERR_MSG := 'Source and destination accounts must have the same currency.';

        log_transfer(P_SRC_IBAN, P_DEST_IBAN, P_AMOUNT, V_FEE, 'FAILED', -20004, P_ERR_MSG);

    WHEN E_DAILY_LIMIT_EXCEEDED THEN
        ROLLBACK;
        P_STATUS := 'ERR_LIMIT';
        P_ERR_MSG := 'Daily transfer limit exceeded.';

        log_transfer(P_SRC_IBAN, P_DEST_IBAN, P_AMOUNT, V_FEE, 'FAILED', -20005, P_ERR_MSG);

    WHEN E_CHECK_VIOLATED THEN
        ROLLBACK TO before_transfer;
        ROLLBACK;
        P_STATUS := 'ERR_CHECK';
        P_ERR_MSG := 'Check constraint violated. '
                     || 'SQLCODE=' || SQLCODE
                     || ', SQLERRM=' || SQLERRM;

        log_transfer(P_SRC_IBAN, P_DEST_IBAN, P_AMOUNT, V_FEE, 'FAILED', SQLCODE, P_ERR_MSG);

    WHEN OTHERS THEN
        ROLLBACK;
        P_STATUS := 'ERR_OTHER';
        P_ERR_MSG := 'Unexpected error. '
                     || 'SQLCODE=' || SQLCODE
                     || ', SQLERRM=' || SQLERRM
                     || ', BACKTRACE=' || DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;

        log_transfer(P_SRC_IBAN, P_DEST_IBAN, P_AMOUNT, V_FEE, 'FAILED', SQLCODE, P_ERR_MSG);
END;
/

SHOW ERRORS PROCEDURE transfer_funds;