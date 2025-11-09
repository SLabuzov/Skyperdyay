package by.skyperdyay.engine.analytics.domain.repository.impl;

import by.skyperdyay.engine.analytics.domain.model.BalanceTrend;
import by.skyperdyay.engine.analytics.domain.model.CategoryType;
import by.skyperdyay.engine.analytics.domain.repository.BalanceTrendingRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static by.skyperdyay.engine.analytics.domain.model.Tables.CATEGORIES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.TRANSACTIONS;
import static by.skyperdyay.engine.analytics.domain.model.Tables.WALLETS;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.when;

@Repository
public class JooqBalanceTrendingRepository implements BalanceTrendingRepository {

    private final DSLContext context;

    public JooqBalanceTrendingRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<BalanceTrend> generatePeriodOverPeriodChangeReport(String owner, int trendPeriod, LocalDate reportDate) {
        // Базовый период
        LocalDate baselinePeriodStart = reportDate.minusDays(trendPeriod + trendPeriod - 1);
        LocalDate baselinePeriodEnd = reportDate.minusDays(trendPeriod);
        // Текущий период
        LocalDate currentPeriodStart = reportDate.minusDays(trendPeriod - 1);

        return context.select(
                        WALLETS.CURRENCY_CODE,
                        WALLETS.BALANCE,

                        // baseline_income_amount - INCOME до currentPeriodStart
                        sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.INCOME.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.le(baselinePeriodEnd)),
                                TRANSACTIONS.AMOUNT
                        ).otherwise(BigDecimal.ZERO)).as("baseline_income_amount"),

                        // baseline_expense_amount - EXPENSE до currentPeriodStart
                        sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.EXPENSE.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.le(baselinePeriodEnd)),
                                TRANSACTIONS.AMOUNT
                        ).otherwise(BigDecimal.ZERO)).as("baseline_expense_amount"),

                        // current_income_amount - INCOME начиная с currentPeriodStart
                        sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.INCOME.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.ge(currentPeriodStart)),
                                TRANSACTIONS.AMOUNT
                        ).otherwise(BigDecimal.ZERO)).as("current_income_amount"),

                        // current_expense_amount - EXPENSE начиная с currentPeriodStart
                        sum(when(
                                CATEGORIES.TYPE.eq(CategoryType.EXPENSE.name())
                                        .and(TRANSACTIONS.TRANSACTION_DATE.ge(currentPeriodStart)),
                                TRANSACTIONS.AMOUNT
                        ).otherwise(BigDecimal.ZERO)).as("current_expense_amount"),

                        // count_baseline_transactions - COUNT до currentPeriodStart
                        sum(when(
                                TRANSACTIONS.TRANSACTION_DATE.le(baselinePeriodEnd),
                                BigDecimal.ONE
                        ).otherwise(BigDecimal.ZERO)).as("count_baseline_transactions"),

                        // count_current_transactions - COUNT начиная с currentPeriodStart
                        sum(when(
                                TRANSACTIONS.TRANSACTION_DATE.ge(currentPeriodStart),
                                BigDecimal.ONE
                        ).otherwise(BigDecimal.ZERO)).as("count_current_transactions"),

                        // count_total_transactions - COUNT начиная с currentPeriodStart
                        count(TRANSACTIONS.ID).as("count_total_transactions")
                )
                .from(WALLETS)
                .leftJoin(TRANSACTIONS).on(
                        WALLETS.ID.eq(TRANSACTIONS.WALLET_ID)
                                .and(TRANSACTIONS.TRANSACTION_DATE.between(baselinePeriodStart, reportDate))
                )
                .leftJoin(CATEGORIES).on(CATEGORIES.ID.eq(TRANSACTIONS.CATEGORY_ID))
                .where(WALLETS.OWNER.eq(owner))
                .groupBy(WALLETS.CURRENCY_CODE, WALLETS.BALANCE)
                .fetchInto(BalanceTrend.class);
    }
}
