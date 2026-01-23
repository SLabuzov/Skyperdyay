--liquibase formatted sql

--changeset SergeyLabuzov:create-new-table-profiles
CREATE TABLE moneybox.profiles
(
    owner              VARCHAR(64) PRIMARY KEY,
    country_code       VARCHAR(16) NOT NULL,
    timezone_code      VARCHAR(64) NOT NULL,
    main_currency_code VARCHAR(3)  NOT NULL,
    accepted_terms     BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at         TIMESTAMP   NOT NULL,
    updated_at         TIMESTAMP,
    CONSTRAINT profiles_currencies__fk
        FOREIGN KEY ( main_currency_code ) REFERENCES moneybox.dict_currencies ( code ),
    CONSTRAINT profiles_dict_countries__fk
        FOREIGN KEY ( country_code ) REFERENCES moneybox.dict_countries ( code ),
    CONSTRAINT profiles_dict_timezones__fk
        FOREIGN KEY ( timezone_code ) REFERENCES moneybox.dict_timezones ( code )
);
--rollback DROP TABLE moneybox.profiles;
