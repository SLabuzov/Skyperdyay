--liquibase formatted sql

--changeset SergeyLabuzov:create-new-table-dict_timezones
CREATE TABLE moneybox.dict_timezones
(
    code         VARCHAR(64) PRIMARY KEY,
    name         VARCHAR(64) UNIQUE NOT NULL,
    utc_offset   VARCHAR(6)         NOT NULL,
    country_code VARCHAR(3)         NOT NULL,
    description  TEXT               NOT NULL,
    CONSTRAINT dict_timezones_dict_countries__fk
        FOREIGN KEY ( country_code ) REFERENCES moneybox.dict_countries ( code )
);
--rollback DROP TABLE moneybox.dict_timezones;

--changeset SergeyLabuzov:create-fill-table-dict_timezones
INSERT INTO moneybox.dict_timezones (code, name, utc_offset, country_code, description)
VALUES
    -- Таймзоны для Беларуси
    ('europe_minsk', 'Europe/Minsk', '+03:00', 'BLR', 'Минское время'),

    -- Таймзоны для России (несколько основных)
    ('europe_moscow', 'Europe/Moscow', '+03:00', 'RUS', 'Московское время'),
    ('europe_kaliningrad', 'Europe/Kaliningrad', '+02:00', 'RUS', 'Калининградское время'),
    ('asia_yekaterinburg', 'Asia/Yekaterinburg', '+05:00', 'RUS', 'Екатеринбургское время'),
    ('asia_irkutsk', 'Asia/Irkutsk', '+08:00', 'RUS', 'Иркутское время'),
    ('asia_vladivostok', 'Asia/Vladivostok', '+10:00', 'RUS', 'Владивостокское время'),
    ('asia_kamchatka', 'Asia/Kamchatka', '+12:00', 'RUS', 'Камчатское время'),

    -- Таймзоны для Украины
    ('europe_kiev', 'Europe/Kiev', '+02:00', 'UKR', 'Киевское время'),

    -- Таймзоны для Казахстана
    ('asia_almaty', 'Asia/Almaty', '+06:00', 'KAZ', 'Алматинское время'),
    ('asia_astana', 'Asia/Astana', '+06:00', 'KAZ', 'Астанинское время'),
    ('asia_aktobe', 'Asia/Aqtobe', '+05:00', 'KAZ', 'Актюбинское время'),
    ('asia_oral', 'Asia/Oral', '+05:00', 'KAZ', 'Уральское время');
--rollback TRUNCATE TABLE moneybox.dict_currencies;

--changeset SergeyLabuzov:add-dict_countries-constraint-dict_countries_dict_timezones__fk
ALTER TABLE moneybox.dict_countries
    ADD CONSTRAINT dict_countries_default_timezones__fk FOREIGN KEY ( default_timezone_code )
        REFERENCES moneybox.dict_timezones ( code );
--rollback ALTER TABLE moneybox.dict_countries DROP CONSTRAINT dict_countries_dict_timezones__fk;
