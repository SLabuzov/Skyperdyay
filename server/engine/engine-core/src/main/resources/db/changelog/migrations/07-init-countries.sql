--liquibase formatted sql

--changeset SergeyLabuzov:create-new-table-dict_countries
CREATE TABLE moneybox.dict_countries
(
    code                  VARCHAR(3) PRIMARY KEY,
    name                  VARCHAR(64) UNIQUE NOT NULL,
    default_timezone_code VARCHAR(64)        NOT NULL,
    default_currency_code VARCHAR(3)         NOT NULL,
    CONSTRAINT dict_countries_currencies__fk
        FOREIGN KEY ( default_currency_code ) REFERENCES moneybox.dict_currencies ( code )
);
--rollback DROP TABLE moneybox.dict_countries;

--changeset SergeyLabuzov:create-fill-table-dict_countries
INSERT INTO moneybox.dict_countries (code, name, default_timezone_code, default_currency_code)
VALUES ('BLR', 'Беларусь', 'europe_minsk', 'BYN'),
       ('RUS', 'Россия', 'europe_moscow', 'RUB'),
       ('UKR', 'Украина', 'europe_kiev', 'UAH'),
       ('KAZ', 'Казахстан', 'asia_almaty', 'KZT');
--rollback TRUNCATE TABLE moneybox.dict_currencies;
