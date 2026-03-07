package by.skyperdyay.engine.core.api.service;

import by.skyperdyay.common.dm.ExchangeRateData;
import java.util.List;

/**
 * Сервисный интерфейс для сохранения курсов валют.
 * Вызывается провайдерами курсов валют для сохранения полученных данных.
 * Реализация находится в модуле engine-core.
 */
public interface ExchangeRatesApiService {

    /**
     * Сохраняет курсы валют в базу данных.
     * Реализует логику upsert: вставляет новые курсы, обновляет существующие при изменении.
     *
     * @param rates список данных курсов валют для сохранения
     */
    void saveExchangeRates(List<ExchangeRateData> rates);

}