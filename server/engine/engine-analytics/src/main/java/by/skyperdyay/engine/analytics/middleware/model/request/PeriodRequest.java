package by.skyperdyay.engine.analytics.middleware.model.request;

import java.time.LocalDate;

public record PeriodRequest(
        LocalDate startPeriod,
        LocalDate endPeriod
) {
}
