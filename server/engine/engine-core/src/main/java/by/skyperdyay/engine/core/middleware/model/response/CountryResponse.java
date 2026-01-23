package by.skyperdyay.engine.core.middleware.model.response;

public record CountryResponse(
        String code,
        String name,
        TimezoneResponse defaultTimezone,
        CurrencyResponse defaultCurrency
) {
}
