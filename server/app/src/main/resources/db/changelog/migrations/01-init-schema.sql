--liquibase formatted sql

--changeset SergeyLabuzov:create-new-schema-moneybox
CREATE SCHEMA moneybox;
--rollback DROP SCHEMA moneybox;
