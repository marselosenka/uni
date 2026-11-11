SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE process_next_transfer (
    P_PROCESSED OUT INTEGER
)
AS
    V_PT_ID   pending_transfer.pt_id%TYPE;
    V_SRC     pending_transfer.src_iban%TYPE;
    V_DEST    pending_transfer.dst_iban%TYPE;
    V_AMT     pending_transfer.amount%TYPE;

    V_STATUS  VARCHAR2(50);
    V_ERR_MSG VARCHAR2(2000);

    CURSOR C_NEXT_TRANSFER IS
        SELECT pt_id, src_iban, dst_iban, amount
        FROM pending_transfer
        WHERE state = 'NEW'
        ORDER BY pt_id
        FOR UPDATE SKIP LOCKED;
BEGIN
    P_PROCESSED := NULL;

    OPEN C_NEXT_TRANSFER;

    FETCH C_NEXT_TRANSFER
    INTO V_PT_ID, V_SRC, V_DEST, V_AMT;

    IF C_NEXT_TRANSFER%NOTFOUND THEN
        CLOSE C_NEXT_TRANSFER;
        P_PROCESSED := NULL;
        COMMIT;
        RETURN;
    END IF;

    CLOSE C_NEXT_TRANSFER;

    P_PROCESSED := V_PT_ID;

    DBMS_OUTPUT.PUT_LINE('Picked pending transfer PT_ID = ' || V_PT_ID);

    DBMS_LOCK.SLEEP(2);

    transfer_funds(
        P_SRC_IBAN  => V_SRC,
        P_DEST_IBAN => V_DEST,
        P_AMOUNT    => V_AMT,
        P_STATUS    => V_STATUS,
        P_ERR_MSG   => V_ERR_MSG
    );

    IF V_STATUS = 'OK' THEN
        UPDATE pending_transfer
        SET state = 'DONE'
        WHERE pt_id = V_PT_ID;

        DBMS_OUTPUT.PUT_LINE('PT_ID ' || V_PT_ID || ' marked DONE.');
    ELSE
        UPDATE pending_transfer
        SET state = 'FAILED'
        WHERE pt_id = V_PT_ID;

        DBMS_OUTPUT.PUT_LINE('PT_ID ' || V_PT_ID || ' marked FAILED.');
        DBMS_OUTPUT.PUT_LINE('Reason: ' || V_ERR_MSG);
    END IF;

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;

        DBMS_OUTPUT.PUT_LINE('process_next_transfer failed.');
        DBMS_OUTPUT.PUT_LINE('SQLCODE = ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('SQLERRM = ' || SQLERRM);

        P_PROCESSED := NULL;
END;
/

SHOW ERRORS PROCEDURE process_next_transfer;