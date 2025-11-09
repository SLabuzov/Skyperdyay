package by.skyperdyay.engine.analytics.domain.repository;

import by.skyperdyay.engine.analytics.domain.model.BalanceTrend;
import java.time.LocalDate;
import java.util.List;

public interface BalanceTrendingRepository {

    /**
     * Формирует отчёт о динамике доходов и расходов за два последовательных периода
     * одинаковой продолжительности, заданной параметром {@code trendPeriod}.
     *
     * <p>Первый (базовый) период: {@code [reportDate - 2 * trendPeriod + 1, reportDate - trendPeriod]}<br>
     * Второй (текущий) период: {@code [reportDate - trendPeriod + 1, reportDate]}<br>
     * где {@code endDate} — дата формирования отчёта (обычно "сегодня" или последний день доступных данных).
     *
     * <p>Данные агрегируются по валюте кошелька. Для каждой валюты возвращаются суммы доходов и расходов
     * за оба периода, что позволяет рассчитать относительные изменения (например, рост расходов на 20%).
     *
     * <p>Пример: при {@code trendPeriod = 30} и дате отчёта 2025-09-01:
     * <ul>
     *   <li>Базовый период: 2025-07-03 — 2025-08-01 (30 дней)</li>
     *   <li>Текущий период: 2025-08-02 — 2025-09-01 (30 дней)</li>
     * </ul>
     *
     * @param owner       идентификатор владелеца аккаунта.
     * @param trendPeriod продолжительность одного периода в днях (например, 7, 14, 30, 90).
     *                    Должна быть положительной и разумной (обычно от 7 до 365).
     * @param reportDate  дата отчета, соответствующая окончанию текущего периода.
     * @return список записей {@link BalanceTrend} с агрегированными данными по каждой валюте.
     * Если по валюте нет операций в одном из периодов, соответствующие поля будут равны {@code BigDecimal.ZERO}.
     */
    List<BalanceTrend> generatePeriodOverPeriodChangeReport(String owner, int trendPeriod, LocalDate reportDate);
}
