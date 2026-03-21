package by.skyperdyay.engine.analytics.domain.repository.impl;

import by.skyperdyay.engine.analytics.domain.model.TotalBalance;
import by.skyperdyay.engine.analytics.domain.repository.TotalBalanceRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static by.skyperdyay.engine.analytics.domain.model.Tables.EXCHANGE_RATES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.PROFILES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.WALLETS;
import static org.jooq.impl.DSL.coalesce;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.val;
import static org.jooq.impl.DSL.when;

@Repository
public class JooqTotalBalanceRepository implements TotalBalanceRepository {

    private final DSLContext context;

    public JooqTotalBalanceRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<TotalBalance> calculateTotalBalance(String owner, LocalDate rateDate) {
        return context.select(
                        WALLETS.CURRENCY_CODE.as("currency_code"),
                        PROFILES.MAIN_CURRENCY_CODE.as("main_currency_code"),
                        coalesce(sum(WALLETS.BALANCE), val(BigDecimal.ZERO)).as("total_balance"),
                        coalesce(sum(
                                when(WALLETS.CURRENCY_CODE.eq(PROFILES.MAIN_CURRENCY_CODE), WALLETS.BALANCE)
                                        .otherwise(WALLETS.BALANCE.mul(coalesce(EXCHANGE_RATES.RATE, val(BigDecimal.ONE))))
                        ), val(BigDecimal.ZERO)).as("total_balance_in_main_currency")
                )
                .from(WALLETS)
                .join(PROFILES).on(PROFILES.OWNER.eq(WALLETS.OWNER))
                .leftJoin(EXCHANGE_RATES).on(
                        EXCHANGE_RATES.BASE_CURRENCY_CODE.eq(PROFILES.MAIN_CURRENCY_CODE)
                                .and(EXCHANGE_RATES.TARGET_CURRENCY_CODE.eq(WALLETS.CURRENCY_CODE))
                                .and(EXCHANGE_RATES.RATE_DATE.eq(rateDate))
                )
                .where(WALLETS.OWNER.eq(owner).and(PROFILES.OWNER.eq(owner)))
                .groupBy(WALLETS.CURRENCY_CODE, PROFILES.MAIN_CURRENCY_CODE)
                .fetchInto(TotalBalance.class);
    }
}
