package by.skyperdyay.engine.core.middleware.model.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfileRequest(

        @NotNull(message = "Country is required")
        String country,

        @NotBlank(message = "Timezone is required")
        String timezone,

        @NotBlank(message = "Main currency code is required")
        String mainCurrencyCode,

        @NotNull(message = "Accepted terms is required")
        @AssertTrue
        Boolean acceptedTerms
) {
}
