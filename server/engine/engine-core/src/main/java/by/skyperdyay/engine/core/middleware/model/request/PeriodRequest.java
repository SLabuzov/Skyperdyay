package by.skyperdyay.engine.core.middleware.model.request;

import java.time.LocalDate;

public record PeriodRequest(
        LocalDate startPeriod,
        LocalDate endPeriod
) {
}
