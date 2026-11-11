SET SERVEROUTPUT ON;

PROMPT ===== SKIP LOCKED WORKER 1 START =====

DECLARE
    V_PROCESSED INTEGER;
BEGIN
    LOOP
        process_next_transfer(V_PROCESSED);

        IF V_PROCESSED IS NULL THEN
            DBMS_OUTPUT.PUT_LINE('Worker 1: queue is empty.');
            EXIT;
        END IF;

        DBMS_OUTPUT.PUT_LINE('Worker 1 processed PT_ID = ' || V_PROCESSED);
    END LOOP;
END;
/

PROMPT ===== SKIP LOCKED WORKER 1 FINAL QUEUE VIEW =====
SELECT pt_id, src_iban, dst_iban, amount, state
FROM pending_transfer
ORDER BY pt_id;