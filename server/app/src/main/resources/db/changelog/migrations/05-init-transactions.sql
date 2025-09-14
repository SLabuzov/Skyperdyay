--liquibase formatted sql

--changeset SergeyLabuzov:create-new-table-transactions
CREATE TABLE moneybox.transactions
(
    id               UUID PRIMARY KEY,
    owner            VARCHAR(64) NOT NULL,
    amount           DECIMAL     NOT NULL,
    wallet_id        UUID        NOT NULL,
    category_id      UUID        NOT NULL,
    transaction_date DATE        NOT NULL,
    notes            VARCHAR(128),
    created_at       TIMESTAMP   NOT NULL,
    modified_at      TIMESTAMP
);
--rollback DROP TABLE moneybox.transactions;
