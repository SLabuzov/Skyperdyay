package by.skyperdyay.engine.analytics.domain.repository;

import by.skyperdyay.engine.analytics.domain.model.PeriodMetrics;
import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для расчёта метрик доходов/расходов за два последовательных периода.
 *
 * <p>Все суммы транзакций дополнительно конвертируются в основную валюту профиля пользователя
 * через LEFT JOIN {@code exchange_rates} с {@code rate_date = transaction_date}.
 *
 * <p>Направление курса: {@code base = mainCurrency, target = walletCurrency},
 * т.е. {@code rate} = стоимость 1 ед. walletCurrency в mainCurrency.
 */
public interface PeriodMetricsRepository {

    /**
     * Формирует метрики доходов и расходов за два последовательных периода.
     *
     * <p>Базовый период: {@code [reportDate - 2 * trendPeriod + 1, reportDate - trendPeriod]}<br>
     * Текущий период: {@code [reportDate - trendPeriod + 1, reportDate]}<br>
     *
     * @param owner       идентификатор владельца.
     * @param trendPeriod длительность одного периода в днях.
     * @param reportDate  дата отчёта (конец текущего периода).
     * @return агрегированные метрики в валюте.
     */
    List<PeriodMetrics> generatePeriodOverPeriodMetrics(String owner, int trendPeriod, LocalDate reportDate);
}
