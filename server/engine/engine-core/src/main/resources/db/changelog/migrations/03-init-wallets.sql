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

--changeset SergeyLabuzov:drop-wallets-constraint-wallets_name_key
ALTER TABLE moneybox.wallets
    DROP CONSTRAINT wallets_name_key;
--rollback ALTER TABLE moneybox.wallets ADD CONSTRAINT wallets_name_key UNIQUE (name);

--changeset SergeyLabuzov:add-wallets-constraint-wallet_owner_name__unique
ALTER TABLE moneybox.wallets
    ADD CONSTRAINT wallet_owner_name__unique UNIQUE ( owner, name );
--rollback ALTER TABLE moneybox.wallets DROP CONSTRAINT wallet_owner_name__unique;

--changeset SergeyLabuzov:add-wallets-constraint-wallet_owner_currency_code__unique
ALTER TABLE moneybox.wallets
    ADD CONSTRAINT wallet_owner_currency_code__unique UNIQUE ( owner, currency_code );
--rollback ALTER TABLE moneybox.wallets DROP CONSTRAINT wallet_owner_currency_code__unique;

--changeset SergeyLabuzov:add-wallets-index-idx_wallets_currencies__fk
CREATE INDEX IF NOT EXISTS idx_wallets_currencies__fk ON moneybox.wallets ( currency_code );
--rollback DROP INDEX IF EXISTS idx_wallets_currencies__fk;
