package by.skyperdyay.exchange.rub.mapper;


import by.skyperdyay.common.dm.ExchangeRateData;
import by.skyperdyay.exchange.rub.client.CbrRateResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * MapStruct маппер для конвертации ответов API ЦБ в ExchangeRateData.
 */
@Mapper(componentModel = "spring")
public interface CbrRateMapper {

    /**
     * Преобразует ответ с курсом ЦБ в ExchangeRateData.
     * Курсы ЦБ предоставляются в формате "номинал единиц иностранной валюты = значение рублей"
     * Необходимо конвертировать в "1 единица иностранной валюты = X рублей"
     */
    @Mapping(source = "valute.charCode", target = "targetCurrencyCode")
    @Mapping(source = "valute", target = "rate", qualifiedByName = "calculateRate")
    @Mapping(source = "date", target = "rateDate")
    @Mapping(constant = "RUB", target = "baseCurrencyCode")
    @Mapping(constant = "cbr-rub", target = "providerCode")
    @Mapping(constant = "CBR", target = "source")
    ExchangeRateData convert(CbrRateResponse.CbrValute valute, LocalDate date);


    /**
     * Вычисляет актуальный курс за 1 единицу иностранной валюты.
     * ЦБ предоставляет: номинал * иностранная = значение рублей
     * Нужно: 1 * иностранная = курс рублей
     * Следовательно: курс = значение / номинал
     */
    @Named("calculateRate")
    default BigDecimal calculateRate(CbrRateResponse.CbrValute valute) {
        if (valute.getNominal() == null || valute.getValueAsBigDecimal() == null) {
            return null;
        }
        return valute
                .getValueAsBigDecimal()
                .divide(
                        BigDecimal.valueOf(valute.getNominal()),
                        6,
                        RoundingMode.HALF_UP
                );
    }
}
