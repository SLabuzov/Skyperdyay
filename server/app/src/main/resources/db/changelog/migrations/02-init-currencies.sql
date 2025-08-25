--liquibase formatted sql

--changeset SergeyLabuzov:create-new-dictionary-dict_currencies
CREATE TABLE moneybox.dict_currencies
(
    code   VARCHAR(3) PRIMARY KEY,
    name   VARCHAR(64) UNIQUE NOT NULL,
    symbol VARCHAR(4)         NOT NULL
);
--rollback DROP TABLE moneybox.dict_currencies;

--changeset SergeyLabuzov:create-fill-table-dict_currencies
INSERT INTO moneybox.dict_currencies (code, name, symbol)
VALUES ('CNY', 'Китайский Юань', '¥'),
       ('RUB', 'Российский Рубль', '₽'),
       ('BYN', 'Белорусский Рубль', 'Br'),
       ('USD', 'Доллар США', '$'),
       ('EUR', 'Евро', '€'),
       ('GBP', 'Фунт Стерлинга', '£');
--rollback TRUNCATE TABLE moneybox.dict_currencies;
