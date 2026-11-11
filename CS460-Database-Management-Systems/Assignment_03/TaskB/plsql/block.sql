SET SERVEROUTPUT ON;

DECLARE
    V_SRC_IBAN      accounts.iban%TYPE := 'GR1601101250000000012300695';
    V_DEST_IBAN     accounts.iban%TYPE := 'GR9601101250000000012300696';

    V_AMOUNT        NUMBER := 300;
    V_FEE           NUMBER := V_AMOUNT * 0.01;

    V_SRC_STATUS    accounts.status%TYPE;
    V_DEST_STATUS   accounts.status%TYPE;
    V_SRC_CURRENCY  accounts.currency%TYPE;
    V_DEST_CURRENCY accounts.currency%TYPE;
BEGIN
    SELECT status, currency
    INTO V_SRC_STATUS, V_SRC_CURRENCY
    FROM accounts
    WHERE iban = V_SRC_IBAN;

    SELECT status, currency
    INTO V_DEST_STATUS, V_DEST_CURRENCY
    FROM accounts
    WHERE iban = V_DEST_IBAN;

    IF V_SRC_STATUS <> 'ACTIVE' OR V_DEST_STATUS <> 'ACTIVE' THEN
        RAISE_APPLICATION_ERROR(-20001, 'Both accounts must be ACTIVE.');
    END IF;

    IF V_SRC_CURRENCY <> V_DEST_CURRENCY THEN
        RAISE_APPLICATION_ERROR(-20002, 'Source and destination accounts must have the same currency.');
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

        DBMS_OUTPUT.PUT_LINE('Main transfer completed.');
        DBMS_OUTPUT.PUT_LINE('Fee charged successfully.');
        DBMS_OUTPUT.PUT_LINE('Transferred amount: ' || V_AMOUNT);
        DBMS_OUTPUT.PUT_LINE('Fee: ' || V_FEE);

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK TO before_fee;

            DBMS_OUTPUT.PUT_LINE('Fee deduction failed.');
            DBMS_OUTPUT.PUT_LINE('Main transfer was preserved.');
            DBMS_OUTPUT.PUT_LINE('Error code: ' || SQLCODE);
            DBMS_OUTPUT.PUT_LINE('Error message: ' || SQLERRM);
    END;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Transaction committed.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;

        DBMS_OUTPUT.PUT_LINE('Account not found.');
        DBMS_OUTPUT.PUT_LINE('Error code: ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('Error message: ' || SQLERRM);

    WHEN OTHERS THEN
        ROLLBACK;

        DBMS_OUTPUT.PUT_LINE('Transaction failed. Full transaction rolled back.');
        DBMS_OUTPUT.PUT_LINE('Error code: ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('Error message: ' || SQLERRM);
END;
/