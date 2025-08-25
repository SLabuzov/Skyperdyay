--liquibase formatted sql

--changeset SergeyLabuzov:create-new-table-wallets
CREATE TABLE moneybox.wallets
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(64) UNIQUE NOT NULL,
    owner         VARCHAR(64)        NOT NULL,
    currency_code VARCHAR(3)         NOT NULL,
    balance       DECIMAL            NOT NULL,
    is_active     BOOLEAN            NOT NULL,
    created_at    TIMESTAMP          NOT NULL,
    modified_at   TIMESTAMP,
    CONSTRAINT wallets_currencies__fk
        FOREIGN KEY (currency_code) REFERENCES moneybox.dict_currencies (code)
);
--rollback DROP TABLE moneybox.wallets;
