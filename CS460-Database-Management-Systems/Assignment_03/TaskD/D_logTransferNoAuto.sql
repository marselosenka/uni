SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE log_transfer_no_auto (
    P_SRC       IN VARCHAR2,
    P_DEST      IN VARCHAR2,
    P_AMOUNT    IN NUMBER,
    P_FEE       IN NUMBER,
    P_STATUS    IN VARCHAR2,
    P_ERR_CODE  IN NUMBER,
    P_ERR_MSG   IN VARCHAR2
)
AS
BEGIN
    INSERT INTO transfer_log (
        log_id,
        src_iban,
        dst_iban,
        amount,
        fee,
        status,
        err_code,
        err_msg
    )
    VALUES (
        seq_log_id.NEXTVAL,
        P_SRC,
        P_DEST,
        P_AMOUNT,
        P_FEE,
        P_STATUS,
        P_ERR_CODE,
        SUBSTR(P_ERR_MSG, 1, 500)
    );
END;
/

SHOW ERRORS PROCEDURE log_transfer_no_auto;