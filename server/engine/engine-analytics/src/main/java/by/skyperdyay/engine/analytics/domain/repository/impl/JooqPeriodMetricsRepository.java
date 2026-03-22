package by.skyperdyay.engine.analytics.domain.repository.impl;

import by.skyperdyay.engine.analytics.domain.model.CategoryType;
import by.skyperdyay.engine.analytics.domain.model.PeriodMetrics;
import by.skyperdyay.engine.analytics.domain.repository.PeriodMetricsRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;

import static by.skyperdyay.engine.analytics.domain.model.Tables.CATEGORIES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.EXCHANGE_RATES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.PROFILES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.TRANSACTIONS;
import static by.skyperdyay.engine.analytics.domain.model.Tables.WALLETS;
import static org.jooq.impl.DSL.coalesce;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.val;
import static org.jooq.impl.DSL.when;

@Repository
public class JooqPeriodMetricsRepository implements PeriodMetricsRepository {

    private final DSLContext context;

    public JooqPeriodMetricsRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<PeriodMetrics> generatePeriodOverPeriodMetrics(String owner, int trendPeriod, LocalDate reportDate) {
        // Базовый период
        LocalDate baselinePeriodStart = reportDate.minusDays(trendPeriod + trendPeriod - 1);
        LocalDate baselinePeriodEnd = reportDate.minusDays(trendPeriod);
        // Текущий период
        LocalDate currentPeriodStart = reportDate.minusDays(trendPeriod - 1);

        Field<BigDecimal> txAmount = when(
                WALLETS.CURRENCY_CODE.eq(PROFILES.MAIN_CURRENCY_CODE), TRANSACTIONS.AMOUNT
        ).otherwise(TRANSACTIONS.AMOUNT.mul(coalesce(EXCHANGE_RATES.RATE, val(BigDecimal.ONE))));

        return context.select(
                        WALLETS.CURRENCY_CODE.as("currency_code"),
                        PROFILES.MAIN_CURRENCY_CODE.as("main_currency_code"),
                        coalesce(sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.INCOME.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.le(baselinePeriodEnd)),
                                txAmount
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("baseline_income"),

                        coalesce(sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.INCOME.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.le(baselinePeriodEnd)),
                                TRANSACTIONS.AMOUNT
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("main_currency_baseline_income"),

                        coalesce(sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.EXPENSE.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.le(baselinePeriodEnd)),
                                txAmount
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("baseline_expense"),

                        coalesce(sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.EXPENSE.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.le(baselinePeriodEnd)),
                                TRANSACTIONS.AMOUNT
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("main_currency_baseline_expense"),

                        coalesce(sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.INCOME.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.ge(currentPeriodStart)),
                                txAmount
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("current_income"),

                        coalesce(sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.INCOME.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.ge(currentPeriodStart)),
                                TRANSACTIONS.AMOUNT
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("main_currency_current_income"),

                        coalesce(sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.EXPENSE.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.ge(currentPeriodStart)),
                                txAmount
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("current_expense"),

                        coalesce(sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.EXPENSE.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.ge(currentPeriodStart)),
                                TRANSACTIONS.AMOUNT
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("main_currency_current_expense"),

                        coalesce(sum(when(
                                TRANSACTIONS.TRANSACTION_DATE.le(baselinePeriodEnd), BigDecimal.ONE
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("count_baseline"),

                        coalesce(sum(when(
                                TRANSACTIONS.TRANSACTION_DATE.ge(currentPeriodStart), BigDecimal.ONE
                        ).otherwise(BigDecimal.ZERO)), val(BigDecimal.ZERO)).as("count_current"),

                        count(TRANSACTIONS.ID).as("count_total")
                )
                .from(WALLETS)
                .join(PROFILES).on(WALLETS.OWNER.eq(PROFILES.OWNER))
                .leftJoin(TRANSACTIONS).on(
                        WALLETS.ID.eq(TRANSACTIONS.WALLET_ID)
                                .and(TRANSACTIONS.TRANSACTION_DATE.between(baselinePeriodStart, reportDate))
                )
                .leftJoin(CATEGORIES).on(CATEGORIES.ID.eq(TRANSACTIONS.CATEGORY_ID))
                .leftJoin(EXCHANGE_RATES).on(
                        EXCHANGE_RATES.BASE_CURRENCY_CODE.eq(PROFILES.MAIN_CURRENCY_CODE)
                                .and(EXCHANGE_RATES.TARGET_CURRENCY_CODE.eq(WALLETS.CURRENCY_CODE))
                                .and(EXCHANGE_RATES.RATE_DATE.eq(TRANSACTIONS.TRANSACTION_DATE))
                )
                .where(WALLETS.OWNER.eq(owner).and(PROFILES.OWNER.eq(owner)))
                .groupBy(WALLETS.CURRENCY_CODE, PROFILES.MAIN_CURRENCY_CODE)
                .fetchInto(PeriodMetrics.class);
    }
}
