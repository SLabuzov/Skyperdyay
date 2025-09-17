--liquibase formatted sql

--changeset SergeyLabuzov:create-new-table-transactions
CREATE TABLE moneybox.transactions
(
    id               uuid PRIMARY KEY,
    owner            VARCHAR(64) NOT NULL,
    amount           DECIMAL     NOT NULL,
    wallet_id        uuid        NOT NULL,
    category_id      uuid        NOT NULL,
    transaction_date DATE        NOT NULL,
    notes            VARCHAR(128),
    created_at       TIMESTAMP   NOT NULL,
    modified_at      TIMESTAMP,
    CONSTRAINT transactions_wallets__fk
        FOREIGN KEY ( wallet_id ) REFERENCES moneybox.wallets ( id ),
    CONSTRAINT transactions_categories__fk
        FOREIGN KEY ( category_id ) REFERENCES moneybox.categories ( id )
);
--rollback DROP TABLE moneybox.transactions;
