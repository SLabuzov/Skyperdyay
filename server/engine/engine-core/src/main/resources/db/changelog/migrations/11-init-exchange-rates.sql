--liquibase formatted sql

--changeset SergeyLabuzov:create-table-exchange-rates
CREATE TABLE moneybox.exchange_rates
(
    base_currency_code   VARCHAR(3)     NOT NULL,
    target_currency_code VARCHAR(3)     NOT NULL,
    rate                 DECIMAL(19, 6) NOT NULL,
    rate_date            DATE           NOT NULL,
    provider_code        VARCHAR(32)    NOT NULL,
    CONSTRAINT uk_exchange_rates_base_target_date PRIMARY KEY ( base_currency_code, target_currency_code, rate_date )
);
--rollback DROP TABLE moneybox.exchange_rates;

--changeset SergeyLabuzov:create-table-provider-execution-status
CREATE TABLE moneybox.exchange_rate_provider_status
(
    provider_code        VARCHAR(32) PRIMARY KEY,
    last_success_date    DATE,
    last_attempt_time    TIMESTAMP,
    consecutive_failures INTEGER DEFAULT 0,
    status               VARCHAR(16)
);
--rollback DROP TABLE moneybox.exchange_rate_provider_status;
