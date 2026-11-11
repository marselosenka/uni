SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE safe_transfer (
    P_SRC  IN VARCHAR2,
    P_DEST IN VARCHAR2,
    P_AMT  IN NUMBER
)
AS
    V_FIRST_IBAN    accounts.iban%TYPE;
    V_SECOND_IBAN   accounts.iban%TYPE;

    V_FIRST_LOCKED  BOOLEAN;
    V_SECOND_LOCKED BOOLEAN;

    V_STATUS        VARCHAR2(50);
    V_ERR_MSG       VARCHAR2(2000);
BEGIN
    /*
        Always lock the lexicographically smaller IBAN first.
    */
    IF P_SRC < P_DEST THEN
        V_FIRST_IBAN := P_SRC;
        V_SECOND_IBAN := P_DEST;
    ELSE
        V_FIRST_IBAN := P_DEST;
        V_SECOND_IBAN := P_SRC;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Trying to lock first account: ' || V_FIRST_IBAN);

    V_FIRST_LOCKED := try_lock_account(V_FIRST_IBAN);

    IF NOT V_FIRST_LOCKED THEN
        DBMS_OUTPUT.PUT_LINE('Could not lock first account. Transfer aborted cleanly.');
        ROLLBACK;
        RETURN;
    END IF;

    DBMS_OUTPUT.PUT_LINE('First account locked.');

    DBMS_OUTPUT.PUT_LINE('Trying to lock second account: ' || V_SECOND_IBAN);

    V_SECOND_LOCKED := try_lock_account(V_SECOND_IBAN);

    IF NOT V_SECOND_LOCKED THEN
        DBMS_OUTPUT.PUT_LINE('Could not lock second account. Releasing first lock and aborting transfer.');
        ROLLBACK;
        RETURN;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Second account locked.');

    transfer_funds(
        P_SRC_IBAN  => P_SRC,
        P_DEST_IBAN => P_DEST,
        P_AMOUNT    => P_AMT,
        P_STATUS    => V_STATUS,
        P_ERR_MSG   => V_ERR_MSG
    );

    DBMS_OUTPUT.PUT_LINE('transfer_funds status: ' || V_STATUS);
    DBMS_OUTPUT.PUT_LINE('transfer_funds message: ' || V_ERR_MSG);

    IF V_STATUS = 'OK' THEN
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Transfer committed successfully.');
    ELSE
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer failed and was rolled back.');
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('safe_transfer failed unexpectedly.');
        DBMS_OUTPUT.PUT_LINE('SQLCODE = ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('SQLERRM = ' || SQLERRM);
END;
/

SHOW ERRORS PROCEDURE safe_transfer;