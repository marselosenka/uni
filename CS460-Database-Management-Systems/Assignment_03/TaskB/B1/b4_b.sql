SET SERVEROUTPUT ON;

PROMPT ===== ACCOUNTS BEFORE =====
SELECT * FROM accounts;

PROMPT ===== PREVENTS CURRENCY MISMATCH =====

DECLARE
    V_SRC_IBAN       accounts.iban%TYPE := 'GR1601101250000000012300695';
    V_DEST_IBAN      accounts.iban%TYPE := 'GR5801101250000000012300700';

    V_AMOUNT         NUMBER := 300;
    V_FEE            NUMBER := V_AMOUNT * 0.01;

    V_SRC_CURRENCY   accounts.currency%TYPE;
    V_DEST_CURRENCY  accounts.currency%TYPE;
BEGIN
    SELECT currency
    INTO V_SRC_CURRENCY
    FROM accounts
    WHERE iban = V_SRC_IBAN;

    SELECT currency
    INTO V_DEST_CURRENCY
    FROM accounts
    WHERE iban = V_DEST_IBAN;

    IF V_SRC_CURRENCY <> V_DEST_CURRENCY THEN
        RAISE_APPLICATION_ERROR(-20004, 'Source and destination accounts must have the same currency.');
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

PROMPT ===== ACCOUNTS AFTER =====
SELECT * FROM accounts;