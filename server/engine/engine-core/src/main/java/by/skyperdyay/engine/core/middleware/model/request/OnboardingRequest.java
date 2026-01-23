package by.skyperdyay.engine.core.middleware.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OnboardingRequest(
        @NotNull
        ProfileRequest profile,

        @NotNull
        @NotEmpty
        List<DefineCategoryRequest> incomeCategories,

        @NotNull
        @NotEmpty
        List<DefineCategoryRequest> expenseCategories,

        @NotNull
        RegisterWalletRequest wallet
) {
}
