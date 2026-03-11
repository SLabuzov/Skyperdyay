package by.skyperdyay.exchange.byn.mapper;

import by.skyperdyay.common.dm.ExchangeRateData;
import by.skyperdyay.exchange.byn.client.NbrbRateResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * MapStruct маппер для преобразования ответов API НБРБ в ExchangeRateData.
 */
@Mapper(componentModel = "spring")
public interface NbrbRateMapper {

    /**
     * Преобразует ответ с курсом НБРБ в ExchangeRateData.
     * Курсы НБРБ предоставляются в виде "номинал единиц иностранной валюты = X BYN"
     * Необходимо преобразовать в "1 единица иностранной валюты = X BYN"
     */
    @Mapping(source = "response.currencyAbbreviation", target = "targetCurrencyCode")
    @Mapping(source = "response", target = "rate", qualifiedByName = "calculateRate")
    @Mapping(source = "date", target = "rateDate")
    @Mapping(constant = "NBRB", target = "source")
    @Mapping(constant = "BYN", target = "baseCurrencyCode")
    @Mapping(constant = "nbrb-byn", target = "providerCode")
    ExchangeRateData convert(NbrbRateResponse response, LocalDate date);

    /**
     * Вычисляет фактический курс на 1 единицу иностранной валюты.
     * НБРБ предоставляет: номинал * иностранная = значение рублей
     * Нам нужно: 1 * иностранная = курс рублей
     * Следовательно: курс = значение / номинал
     */
    @Named("calculateRate")
    default BigDecimal calculateRate(NbrbRateResponse response) {
        if (response.scale() == null || response.officialRate() == null) {
            return null;
        }
        return response.officialRate()
                .divide(BigDecimal.valueOf(response.scale()), 6, RoundingMode.HALF_UP);
    }
}
