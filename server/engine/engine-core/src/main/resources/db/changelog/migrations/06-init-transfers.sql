--liquibase formatted sql

--changeset SergeyLabuzov:create-new-table-transfers
CREATE TABLE moneybox.transfers
(
    id               uuid PRIMARY KEY,
    owner            VARCHAR(64) NOT NULL,
    source_wallet_id uuid        NOT NULL,
    target_wallet_id uuid        NOT NULL,
    source_amount    DECIMAL     NOT NULL,
    target_amount    DECIMAL     NOT NULL,
    transfer_date    DATE        NOT NULL,
    notes            TEXT,
    created_at       TIMESTAMP   NOT NULL,
    modified_at      TIMESTAMP,
    CONSTRAINT transfers_source_wallet_fk
        FOREIGN KEY ( source_wallet_id ) REFERENCES moneybox.wallets ( id ),
    CONSTRAINT transfers_target_wallet_fk
        FOREIGN KEY ( target_wallet_id ) REFERENCES moneybox.wallets ( id ),
    CONSTRAINT different_wallets_check
        CHECK (source_wallet_id != target_wallet_id)
);
--rollback DROP TABLE moneybox.transfers;

--changeset SergeyLabuzov:add-transactions-index-idx_transfers_source_wallet__fk
CREATE INDEX IF NOT EXISTS idx_transfers_source_wallet__fk ON moneybox.transfers ( source_wallet_id );
--rollback DROP INDEX IF EXISTS idx_transfers_source_wallet__fk;

--changeset SergeyLabuzov:add-transactions-index-idx_transfers_target_wallet__fk
CREATE INDEX IF NOT EXISTS idx_transfers_target_wallet__fk ON moneybox.transfers ( target_wallet_id );
--rollback DROP INDEX IF EXISTS idx_transfers_target_wallet__fk;
