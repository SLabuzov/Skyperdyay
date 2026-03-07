package by.skyperdyay.exchange.byn.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO, представляющий ответ с курсом от API Национального банка Республики Беларусь.
 * Основан на официальном API: https://www.nbrb.by/apihelp/exrates
 */
public record NbrbRateResponse(

    @JsonProperty("Cur_ID")
    Integer currencyId,

    @JsonProperty("Date")
    LocalDate date,

    @JsonProperty("Cur_Abbreviation")
    String currencyAbbreviation,

    @JsonProperty("Cur_Scale")
    Integer scale,

    @JsonProperty("Cur_Name")
    String currencyName,

    @JsonProperty("Cur_OfficialRate")
    BigDecimal officialRate
) {
}
