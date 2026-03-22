package by.skyperdyay.engine.analytics.domain.repository;

import by.skyperdyay.engine.analytics.domain.model.TotalBalance;
import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для вычисления баланса всех кошельков пользователя.
 * Дополнительно формируется баланс в пересчёте на основную валюту.
 *
 * <p>Конвертация: {@code base = mainCurrency, target = walletCurrency},
 * курс берётся на указанную дату ({@code rate_date = rateDate}).
 */
public interface TotalBalanceRepository {

    /**
     * Формирует балансы всех кошельков пользователя,
     * дополнительно конвертируя в основную валюту профиля пользователя.
     *
     * @param owner    идентификатор владельца.
     * @param rateDate дата, по которой берётся курс.
     * @return общий баланс в основной валюте.
     */
    List<TotalBalance> calculateTotalBalance(String owner, LocalDate rateDate);
}
