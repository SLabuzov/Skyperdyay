package by.skyperdyay.engine.analytics.domain.repository.impl;


import by.skyperdyay.engine.analytics.domain.model.CategoryBreakdown;
import by.skyperdyay.engine.analytics.domain.repository.CategoriesRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import static by.skyperdyay.engine.analytics.domain.model.Tables.CATEGORIES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.EXCHANGE_RATES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.PROFILES;
import static by.skyperdyay.engine.analytics.domain.model.Tables.TRANSACTIONS;
import static by.skyperdyay.engine.analytics.domain.model.Tables.WALLETS;
import static org.jooq.impl.DSL.coalesce;
import static org.jooq.impl.DSL.round;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.val;
import static org.jooq.impl.DSL.when;

@Repository
public class JooqCategoriesRepository implements CategoriesRepository {

    private final DSLContext context;

    public JooqCategoriesRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<CategoryBreakdown> categoriesBreakdown(String owner, LocalDate startDate, LocalDate endDate) {
        // Создаем алиас для подзапроса
        Table<?> prepare = context
                .select(
                        CATEGORIES.ID,
                        CATEGORIES.NAME,
                        CATEGORIES.TYPE,
                        PROFILES.MAIN_CURRENCY_CODE,
                        sum(
                                when(
                                        WALLETS.CURRENCY_CODE.eq(PROFILES.MAIN_CURRENCY_CODE), TRANSACTIONS.AMOUNT
                                ).otherwise(TRANSACTIONS.AMOUNT.mul(coalesce(EXCHANGE_RATES.RATE, val(BigDecimal.ONE))))
                        ).as("total_amount")
                )
                .from(TRANSACTIONS)
                .join(WALLETS).on(WALLETS.ID.eq(TRANSACTIONS.WALLET_ID))
                .join(CATEGORIES).on(CATEGORIES.ID.eq(TRANSACTIONS.CATEGORY_ID))
                .join(PROFILES).on(CATEGORIES.OWNER.eq(PROFILES.OWNER))
                .leftJoin(EXCHANGE_RATES).on(
                        EXCHANGE_RATES.BASE_CURRENCY_CODE.eq(PROFILES.MAIN_CURRENCY_CODE)
                                .and(EXCHANGE_RATES.TARGET_CURRENCY_CODE.eq(WALLETS.CURRENCY_CODE))
                                .and(EXCHANGE_RATES.RATE_DATE.eq(TRANSACTIONS.TRANSACTION_DATE))
                )
                .where(TRANSACTIONS.OWNER.eq(owner))
                .and(TRANSACTIONS.TRANSACTION_DATE.between(startDate, endDate))
                .groupBy(CATEGORIES.ID, CATEGORIES.NAME, CATEGORIES.TYPE, PROFILES.MAIN_CURRENCY_CODE)
                .asTable("prepare");

        // Получаем поля из алиаса
        Field<String> prepareId = prepare.field("id", String.class);
        Field<String> prepareType = prepare.field("type", String.class);
        Field<String> prepareName = prepare.field("name", String.class);
        Field<String> prepareCurrency = prepare.field("main_currency_code", String.class);
        Field<BigDecimal> prepareTotalAmount = prepare.field("total_amount", BigDecimal.class);

        // Внешний запрос с оконной функцией
        return context
                .select(
                        prepareId,
                        prepareName,
                        prepareType,
                        prepareCurrency,
                        prepareTotalAmount,
                        round(val(100.0).mul(prepareTotalAmount).div(
                                sum(prepareTotalAmount).over().partitionBy(prepareType)
                        )).as("percent_rate")
                )
                .from(prepare)
                .fetchInto(CategoryBreakdown.class);
    }
}
