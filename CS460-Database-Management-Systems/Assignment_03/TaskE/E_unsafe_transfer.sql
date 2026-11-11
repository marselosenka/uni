SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE unsafe_transfer (
    P_SRC  IN VARCHAR2,
    P_DEST IN VARCHAR2,
    P_AMT  IN NUMBER
)
AS
    V_DUMMY   accounts.iban%TYPE;
    V_STATUS  VARCHAR2(50);
    V_ERR_MSG VARCHAR2(2000);
BEGIN
    DBMS_OUTPUT.PUT_LINE('Locking source account first: ' || P_SRC);

    SELECT iban
    INTO V_DUMMY
    FROM accounts
    WHERE iban = P_SRC
    FOR UPDATE;

    DBMS_OUTPUT.PUT_LINE('Source account locked.');

    DBMS_OUTPUT.PUT_LINE('Waiting 10 seconds before locking ');
    DBMS_LOCK.SLEEP(10);

    DBMS_OUTPUT.PUT_LINE('Locking destination account second: ' || P_DEST);

    SELECT iban
    INTO V_DUMMY
    FROM accounts
    WHERE iban = P_DEST
    FOR UPDATE;

    DBMS_OUTPUT.PUT_LINE('Dest account locked.');

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
        DBMS_OUTPUT.PUT_LINE('Unsafe transfer committed.');
    ELSE
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Unsafe transfer rolled back.');
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('unsafe_transfer failed.');
        DBMS_OUTPUT.PUT_LINE('SQLCODE = ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('SQLERRM = ' || SQLERRM);
END;
/

SHOW ERRORS PROCEDURE unsafe_transfer;