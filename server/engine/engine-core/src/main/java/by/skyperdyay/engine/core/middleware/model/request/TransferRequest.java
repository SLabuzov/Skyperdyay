package by.skyperdyay.engine.core.middleware.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransferRequest(
        @NotNull(message = "Идентификатор исходного кошелька должен быть заполнен")
        UUID sourceWalletId,

        @NotNull(message = "Идентификатор целевого кошелька должен быть заполнен")
        UUID targetWalletId,

        @NotNull(message = "Сумма к списанию должена быть заполнена")
        @DecimalMin(value = "0.01", message = "Сумма к списанию должена быть больше 0")
        BigDecimal sourceAmount,

        @NotNull(message = "Сумма к зачислению должена быть заполнена")
        @DecimalMin(value = "0.01", message = "Сумма к зачислению должена быть больше 0")
        BigDecimal targetAmount,

        @NotNull(message = "Дата перевода должна быть заполнена")
        @PastOrPresent(message = "Дата перевода не может быть в будущем")
        LocalDate transferDate,

        String notes
) {
}
