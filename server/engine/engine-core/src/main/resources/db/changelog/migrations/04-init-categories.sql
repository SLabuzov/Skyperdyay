--liquibase formatted sql

--changeset SergeyLabuzov:create-new-table-categories
CREATE TABLE moneybox.categories
(
    id    uuid PRIMARY KEY,
    name  VARCHAR(64) NOT NULL,
    owner VARCHAR(64) NOT NULL,
    icon  VARCHAR(32) NOT NULL,
    type  VARCHAR(8)  NOT NULL,
    CONSTRAINT owner_category__unique
        UNIQUE ( name, owner ),
    CONSTRAINT category_type__check
        CHECK (type IN ( 'INCOME', 'EXPENSE' ))
);
--rollback DROP TABLE moneybox.categories;
