--liquibase formatted sql

--changeset SergeyLabuzov:create-new-table-onboarding_progress
CREATE TABLE moneybox.onboarding_progress
(
    owner      VARCHAR(64) NOT NULL PRIMARY KEY,
    state      VARCHAR(64) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT category_type__check
        CHECK (state IN ( 'PENDING', 'COMPLETED' ))
);
--rollback DROP TABLE moneybox.onboarding_progress;

--changeset SergeyLabuzov:create-new-table-onboarding_actions
CREATE TABLE moneybox.onboarding_actions
(
    id          SERIAL PRIMARY KEY,
    owner       VARCHAR(64) NOT NULL,
    action_type VARCHAR(64) NOT NULL,
    created_at  TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP,
    UNIQUE ( owner, action_type ),
    CONSTRAINT onboarding_actions_onboarding_progress__fk
        FOREIGN KEY ( owner ) REFERENCES moneybox.onboarding_progress ( owner ),
    CONSTRAINT action_type__check
        CHECK (action_type IN ( 'INIT_PROFILE', 'DEFINE_WALLET', 'DEFINE_INCOME_CATEGORY', 'DEFINE_EXPENSE_CATEGORY' ))
);
--rollback DROP TABLE moneybox.onboarding_actions;
