SET SERVEROUTPUT ON;

PROMPT ===== B2: ACCOUNTS BEFORE RUNNING =====
SELECT * FROM accounts;

PROMPT ===== B2: NON_NEG_BALANCE CHECK VIOLATION =====

DECLARE
    V_SRC_IBAN   accounts.iban%TYPE := 'GR1601101250000000012300695';
    V_DEST_IBAN  accounts.iban%TYPE := 'GR9601101250000000012300696';

    V_AMOUNT     NUMBER := 999999999;
    V_FEE        NUMBER := V_AMOUNT * 0.01;
BEGIN
    UPDATE accounts
    SET balance = balance - V_AMOUNT
    WHERE iban = V_SRC_IBAN;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Source account does not exist.');
    END IF;

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

PROMPT ===== B2: ACCOUNTS AFTER RUNNING =====
SELECT * FROM accounts;