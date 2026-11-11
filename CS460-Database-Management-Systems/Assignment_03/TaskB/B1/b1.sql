SET SERVEROUTPUT ON;

PROMPT ===== B1: ACCOUNTS BEFORE RUNNING =====
SELECT * FROM accounts;

PROMPT ===== B1: SOURCE ACCOUNT THAT DOES NOT EXIST =====

DECLARE
    V_SRC_IBAN   accounts.iban%TYPE := 'GR_DOES_NOT_EXIST';
    V_DEST_IBAN  accounts.iban%TYPE := 'GR9601101250000000012300696';

    V_AMOUNT     NUMBER := 300;
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

        DBMS_OUTPUT.PUT_LINE('Fee charged!.');

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

PROMPT ===== B1: ACCOUNTS AFTER =====
SELECT * FROM accounts;