SET SERVEROUTPUT ON;

PROMPT ===== B3B: ACCOUNTS BEFORE =====
SELECT * FROM accounts;

PROMPT ===== B3B: PL/SQL PREVENTS DEBIT FROM FROZEN ACCOUNT =====

DECLARE
    V_SRC_IBAN      accounts.iban%TYPE := 'GR4301101250000000012300697';
    V_DEST_IBAN     accounts.iban%TYPE := 'GR9601101250000000012300696';

    V_AMOUNT        NUMBER := 300;
    V_FEE           NUMBER := V_AMOUNT * 0.01;

    V_SRC_STATUS    accounts.status%TYPE;
    V_DEST_STATUS   accounts.status%TYPE;
BEGIN
    SELECT status
    INTO V_SRC_STATUS
    FROM accounts
    WHERE iban = V_SRC_IBAN;

    SELECT status
    INTO V_DEST_STATUS
    FROM accounts
    WHERE iban = V_DEST_IBAN;

    IF V_SRC_STATUS <> 'ACTIVE' OR V_DEST_STATUS <> 'ACTIVE' THEN
        RAISE_APPLICATION_ERROR(-20003, 'Both source and destination accounts must be ACTIVE.');
    END IF;

    UPDATE accounts
    SET balance = balance - V_AMOUNT
    WHERE iban = V_SRC_IBAN;

    UPDATE accounts
    SET balance = balance + V_AMOUNT
    WHERE iban = V_DEST_IBAN;

    SAVEPOINT before_fee;

    BEGIN
        UPDATE accounts
        SET balance = balance - V_FEE
        WHERE iban = V_SRC_IBAN;

        DBMS_OUTPUT.PUT_LINE('Fee charged successfully.');

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK TO before_fee;
            DBMS_OUTPUT.PUT_LINE('Fee failed. Main transfer preserved.');
            DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
    END;

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transaction failed. Full transaction rolled back.');
        DBMS_OUTPUT.PUT_LINE('Error code: ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('Error message: ' || SQLERRM);
END;
/

PROMPT ===== B3B: ACCOUNTS AFTER =====
SELECT * FROM accounts;