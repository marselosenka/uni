CREATE TABLE customers (
    cust_id      INTEGER       PRIMARY KEY,
    full_name    VARCHAR2(100) NOT NULL,
    email        VARCHAR2(120) NOT NULL UNIQUE,
    daily_limit  DECIMAL(12,2) DEFAULT 5000.00 NOT NULL,
    CONSTRAINT pos_limit CHECK (daily_limit >= 0)
);

CREATE TABLE accounts (
    iban          VARCHAR2(34)  PRIMARY KEY,
    cust_id       INTEGER       NOT NULL,
    balance       DECIMAL(13,2) NOT NULL,
    currency      CHAR(3)       DEFAULT 'EUR' NOT NULL,
    account_type  VARCHAR2(10)  NOT NULL,
    status        VARCHAR2(10)  DEFAULT 'ACTIVE' NOT NULL,
    opened_at     TIMESTAMP     DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT fk_accounts_cust FOREIGN KEY (cust_id) REFERENCES customers(cust_id),
    CONSTRAINT non_neg_balance  CHECK (balance >= 0.00),
    CONSTRAINT valid_status     CHECK (status        IN ('ACTIVE','FROZEN','CLOSED')),
    CONSTRAINT valid_type       CHECK (account_type  IN ('CHECKING','SAVINGS','LOAN')),
    CONSTRAINT valid_ccy        CHECK (currency      IN ('EUR','USD','GBP'))
);

CREATE TABLE transfer_log (
    log_id      INTEGER       PRIMARY KEY,
    src_iban    VARCHAR2(34),
    dst_iban    VARCHAR2(34),
    amount      DECIMAL(13,2),
    fee         DECIMAL(13,2),
    status      VARCHAR2(20)  NOT NULL,
    err_code    INTEGER,
    err_msg     VARCHAR2(500),
    logged_at   TIMESTAMP     DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT  log_status_chk CHECK (status IN ('SUCCESS','FAILED'))
);

CREATE TABLE pending_transfer (
    pt_id      INTEGER       PRIMARY KEY,
    src_iban   VARCHAR2(34)  NOT NULL,
    dst_iban   VARCHAR2(34)  NOT NULL,
    amount     DECIMAL(13,2) NOT NULL,
    state      VARCHAR2(10)  DEFAULT 'NEW' NOT NULL,
    CONSTRAINT pt_state_chk CHECK (state IN ('NEW','DONE','FAILED'))
);

CREATE SEQUENCE seq_log_id     START WITH 1;
CREATE SEQUENCE seq_pending_id START WITH 1;

-- toy costumers 
INSERT INTO customers (cust_id, full_name, email, daily_limit)
VALUES (1, 'Marselo Senka', 'senkamarselo@gmail.com', 6767.00);

INSERT INTO customers (cust_id, full_name, email, daily_limit)
VALUES (2, 'Ioanna Touni', 'jtouni@example.com', 3000.00);

INSERT INTO customers (cust_id, full_name, email, daily_limit)
VALUES (3, 'Kali Uchis', 'kaliuchis@gmail.com', 7000.00);

-- toy accounts
INSERT INTO accounts (iban, cust_id, balance, currency, account_type, status)
VALUES ('GR1601101250000000012300695', 1, 1000.00, 'EUR', 'CHECKING', 'ACTIVE');

INSERT INTO accounts (iban, cust_id, balance, currency, account_type, status)
VALUES ('GR9601101250000000012300696', 1, 1100.00, 'EUR', 'SAVINGS', 'ACTIVE');

INSERT INTO accounts (iban, cust_id, balance, currency, account_type, status)
VALUES ('GR4301101250000000012300697', 2, 1200.00, 'EUR', 'CHECKING', 'FROZEN');

INSERT INTO accounts (iban, cust_id, balance, currency, account_type, status)
VALUES ('GR2701101250000000012300698', 2, 1300.00, 'USD', 'SAVINGS', 'CLOSED');

INSERT INTO accounts (iban, cust_id, balance, currency, account_type, status)
VALUES ('GR7401101250000000012300699', 3, 1400.00, 'EUR', 'LOAN', 'ACTIVE');

INSERT INTO accounts (iban, cust_id, balance, currency, account_type, status)
VALUES ('GR5801101250000000012300700', 3, 1600.00, 'USD', 'CHECKING', 'ACTIVE');

COMMIT;