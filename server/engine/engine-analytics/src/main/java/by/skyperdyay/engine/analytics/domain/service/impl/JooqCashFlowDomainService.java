package by.skyperdyay.engine.analytics.domain.service.impl;

import by.skyperdyay.engine.analytics.domain.model.CashFlowView;
import by.skyperdyay.engine.analytics.domain.model.CategoryType;
import by.skyperdyay.engine.analytics.domain.service.CashFlowDomainService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jooq.CommonTableExpression;
import org.jooq.DSLContext;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.springframework.stereotype.Service;

import static by.skyperdyay.engine.analytics.domain.model.Tables.CATEGORIES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.TRANSACTIONS;
import static by.skyperdyay.engine.analytics.domain.model.Tables.WALLETS;
import static org.jooq.impl.DSL.choose;
import static org.jooq.impl.DSL.coalesce;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.selectDistinct;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.trunc;

@Slf4j
@Service
public class JooqCashFlowDomainService implements CashFlowDomainService {

    private final DSLContext context;

    private final String MONTH_COLUMN = "month";
    private final String CURRENCY_COLUMN = "currencyCode";
    private final String INCOME_COLUMN = "incomeAmount";
    private final String EXPENSE_COLUMN = "expenseAmount";

    public JooqCashFlowDomainService(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<CashFlowView> aggregateOwnerCashFlowMonthly(String owner, LocalDate startPeriod, LocalDate endPeriod) {

        CommonTableExpression<Record1<LocalDate>> monthSeries = monthSeriesTable(startPeriod, endPeriod);
        CommonTableExpression<Record1<String>> distinctCurrencies = distinctCurrenciesTable(owner);
        CommonTableExpression<Record4<LocalDate, String, BigDecimal, BigDecimal>> cashFlow = cashFlowTable(owner, startPeriod, endPeriod);

        // Определим возвращаемые поля
        Field<LocalDate> month = monthSeries.field(MONTH_COLUMN, LocalDate.class);
        Field<String> currency = distinctCurrencies.field(CURRENCY_COLUMN, String.class);
        Field<BigDecimal> income = cashFlow.field(INCOME_COLUMN, BigDecimal.class);
        Field<BigDecimal> expense = cashFlow.field(EXPENSE_COLUMN, BigDecimal.class);

        // Определим поля связи
        Field<LocalDate> joinMonthSeries = monthSeries.field(MONTH_COLUMN, LocalDate.class);
        Field<LocalDate> joinMonthCashFlow = cashFlow.field(MONTH_COLUMN, LocalDate.class);
        Field<String> joinCurrencyWallet = distinctCurrencies.field(CURRENCY_COLUMN, String.class);
        Field<String> joinCurrencyCashFlow = cashFlow.field(CURRENCY_COLUMN, String.class);

        assert joinCurrencyWallet != null;
        assert joinMonthSeries != null;

        String sql = context
                .with(monthSeries)
                .with(distinctCurrencies)
                .with(cashFlow)
                .select(
                        month,
                        currency,
                        coalesce(income, 0),
                        coalesce(expense, 0)
                )
                .from(monthSeries)
                .crossJoin(distinctCurrencies)
                .leftJoin(cashFlow)
                .on(joinMonthSeries.eq(joinMonthCashFlow).and(joinCurrencyWallet.eq(joinCurrencyCashFlow)))
                .getSQL();

        log.info(sql);


        return context
                .with(monthSeries)
                .with(distinctCurrencies)
                .with(cashFlow)
                .select(
                        month,
                        currency,
                        coalesce(income, 0),
                        coalesce(expense, 0)
                )
                .from(monthSeries)
                .crossJoin(distinctCurrencies)
                .leftJoin(cashFlow)
                .on(joinMonthSeries.eq(joinMonthCashFlow).and(joinCurrencyWallet.eq(joinCurrencyCashFlow)))
                .fetchInto(CashFlowView.class);
    }


    private CommonTableExpression<Record1<LocalDate>> monthSeriesTable(LocalDate startPeriod, LocalDate endPeriod) {
        return name("cte_date_series")
                .fields(MONTH_COLUMN)
                .as(
                        select(field(name("date_series"), LocalDate.class))
                                .from(
                                        table(
                                                "generate_series({0}, {1}, interval '1 month')",
                                                inline(startPeriod),
                                                inline(endPeriod)
                                        ).as("date_series")
                                )
                );
    }

    private CommonTableExpression<Record1<String>> distinctCurrenciesTable(String owner) {
        return name("cte_distinct_currencies")
                .fields(CURRENCY_COLUMN)
                .as(
                        selectDistinct(WALLETS.CURRENCY_CODE)
                                .from(WALLETS)
                                .where(WALLETS.OWNER.eq(owner))
                );
    }

    private CommonTableExpression<Record4<LocalDate, String, BigDecimal, BigDecimal>> cashFlowTable(String owner, LocalDate startPeriod, LocalDate endPeriod) {
        return name("cte_cash_flow")
                .fields(MONTH_COLUMN, CURRENCY_COLUMN, INCOME_COLUMN, EXPENSE_COLUMN)
                .as(
                        select(
                                trunc(TRANSACTIONS.TRANSACTION_DATE, DatePart.MONTH)
                                        .as(MONTH_COLUMN),
                                WALLETS.CURRENCY_CODE
                                        .as(CURRENCY_COLUMN),
                                sum(
                                        choose(CATEGORIES.TYPE)
                                                .when(CategoryType.INCOME.name(), TRANSACTIONS.AMOUNT)
                                                .otherwise(BigDecimal.valueOf(0))
                                )
                                        .as(INCOME_COLUMN),
                                sum(
                                        choose(CATEGORIES.TYPE)
                                                .when(CategoryType.EXPENSE.name(), TRANSACTIONS.AMOUNT.multiply(-1))
                                                .otherwise(BigDecimal.valueOf(0))
                                )
                                        .as(EXPENSE_COLUMN)
                        )
                                .from(TRANSACTIONS)
                                .join(WALLETS).on(TRANSACTIONS.WALLET_ID.eq(WALLETS.ID))
                                .join(CATEGORIES).on(TRANSACTIONS.CATEGORY_ID.eq(CATEGORIES.ID))
                                .where(TRANSACTIONS.OWNER.eq(owner))
                                .and(TRANSACTIONS.TRANSACTION_DATE.between(startPeriod, endPeriod))
                                .groupBy(
                                        trunc(TRANSACTIONS.TRANSACTION_DATE, DatePart.MONTH),
                                        WALLETS.CURRENCY_CODE
                                )
                );
    }
}
