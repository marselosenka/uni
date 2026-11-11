# Task A — Schema & seed data

**Points:** 5

## Schema (use exactly this)

```sql
CREATE TABLE customers (
    cust_id      INTEGER       PRIMARY KEY,
    full_name    VARCHAR2(100) NOT NULL,
    email        VARCHAR2(120) NOT NULL UNIQUE,
    daily_limit DECIMAL(12,2) DEFAULT 5000.00 NOT NULL,
    CONSTRAINT pos_limit CHECK (daily_limit >= 0)
);
CREATE TABLE accounts (
    iban          VARCHAR2(34) PRIMARY KEY,
    cust_id       INTEGER       NOT NULL,
    balance       DECIMAL(13,2) NOT NULL,
    currency      CHAR(3)       DEFAULT 'EUR' NOT NULL,
    account_type VARCHAR2(10) NOT NULL,
    status        VARCHAR2(10) DEFAULT 'ACTIVE' NOT NULL,
    opened_at     TIMESTAMP     DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT fk_accounts_cust FOREIGN KEY (cust_id) REFERENCES customers(cust_id),
    CONSTRAINT non_neg_balance CHECK (balance >= 0.00),
    CONSTRAINT valid_status     CHECK (status        IN ('ACTIVE','FROZEN','CLOSED')),
    CONSTRAINT valid_type       CHECK (account_type IN ('CHECKING','SAVINGS','LOAN')),
    CONSTRAINT valid_ccy        CHECK (currency      IN ('EUR','USD','GBP'))
);
CREATE TABLE transfer_log (
    log_id      INTEGER       PRIMARY KEY,
    src_iban    VARCHAR2(34),
    dst_iban    VARCHAR2(34),
    amount      DECIMAL(13,2),
    fee         DECIMAL(13,2),
    status      VARCHAR2(20) NOT NULL,    -- 'SUCCESS' | 'FAILED'
    err_code    INTEGER,
    err_msg     VARCHAR2(500),
    logged_at   TIMESTAMP     DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT log_status_chk CHECK (status IN ('SUCCESS','FAILED'))
);
CREATE TABLE pending_transfer (
    pt_id      INTEGER       PRIMARY KEY,
    src_iban   VARCHAR2(34) NOT NULL,
    dst_iban   VARCHAR2(34) NOT NULL,
    amount     DECIMAL(13,2) NOT NULL,
    state      VARCHAR2(10) DEFAULT 'NEW' NOT NULL, -- 'NEW' | 'DONE' | 'FAILED'
    CONSTRAINT pt_state_chk CHECK (state IN ('NEW','DONE','FAILED'))
);
CREATE SEQUENCE seq_log_id     START WITH 1;
CREATE SEQUENCE seq_pending_id START WITH 1;
```

Note on IBAN. Real-world IBANs are alphanumeric (up to 34 chars). The chosen VARCHAR2(34) reflects this. You may use mock IBANs of any format you like (e.g. 'GR1601101250000000012300695' ) for testing, as long as the column type is respected.

## Task instructions

Create the schema above. Populate it with at least:

- 3 customers (each with a different daily_limit)
- 6 accounts spanning all account types (CHECKING, SAVINGS, LOAN), at least 2 currencies, and at least one account each in status ACTIVE, FROZEN, and CLOSED.

Show SELECT * results for both tables in the report.
