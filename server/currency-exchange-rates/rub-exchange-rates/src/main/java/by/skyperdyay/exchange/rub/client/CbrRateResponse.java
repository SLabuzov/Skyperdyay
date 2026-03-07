package by.skyperdyay.exchange.rub.client;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

/**
 * DTO, представляющий ответ с курсом от API Центрального банка России.
 * API ЦБ возвращает формат XML.
 * Документация: https://www.cbr.ru/development/DWS/
 */
@Data
@JacksonXmlRootElement(localName = "ValCurs")
public class CbrRateResponse {

    @JacksonXmlProperty(isAttribute = true, localName = "Date")
    private String date;

    @JacksonXmlProperty(isAttribute = true, localName = "name")
    private String name;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Valute")
    private List<CbrValute> valutes;

    /**
     * Получает дату как LocalDate.
     */
    public LocalDate getLocalDate() {
        if (date == null || date.isEmpty()) {
            return LocalDate.now();
        }
        // CBR format: DD.MM.YYYY
        String[] parts = date.split("\\.");
        if (parts.length == 3) {
            return LocalDate.of(
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[0])
            );
        }
        return LocalDate.now();
    }

    /**
     * Отдельный курс валюты от ЦБ.
     */
    @Data
    public static class CbrValute {

        @JacksonXmlProperty(localName = "CharCode")
        private String charCode;

        @JacksonXmlProperty(localName = "Nominal")
        private Integer nominal;

        @JacksonXmlProperty(localName = "Name")
        private String name;

        @JacksonXmlProperty(localName = "Value")
        private String value;

        /**
         * Получает значение как BigDecimal.
         * ЦБ возвращает значения с запятой в качестве десятичного разделителя.
         */
        public BigDecimal getValueAsBigDecimal() {
            if (value == null || value.isEmpty()) {
                return null;
            }
            String normalized = value.replace(",", ".");
            return new BigDecimal(normalized);
        }
    }
}
