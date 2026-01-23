package by.skyperdyay.engine.core.middleware.model.response;

public record TimezoneResponse(
        String code,
        String name,
        String utcOffset,
        String countryCode,
        String description
) {
}
