SET SERVEROUTPUT ON;

PROMPT ===== ACCOUNTS BEFORE =====
SELECT * FROM accounts;

PROMPT ===== LOAN ACCOUNT CANNOT BE USED AS SOURCE =====

DECLARE
    V_SRC_IBAN       accounts.iban%TYPE := 'GR7401101250000000012300699';
    V_DEST_IBAN      accounts.iban%TYPE := 'GR9601101250000000012300696';

    V_AMOUNT         NUMBER := 300;
    V_FEE            NUMBER := V_AMOUNT * 0.01;

    V_SRC_TYPE       accounts.account_type%TYPE;
BEGIN
    SELECT account_type
    INTO V_SRC_TYPE
    FROM accounts
    WHERE iban = V_SRC_IBAN;

    IF V_SRC_TYPE = 'LOAN' THEN
        RAISE_APPLICATION_ERROR(-20005, 'LOAN account cannot be used as transfer source.');
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