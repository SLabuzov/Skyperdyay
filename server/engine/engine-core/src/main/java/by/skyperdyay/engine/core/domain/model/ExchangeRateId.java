package by.skyperdyay.engine.core.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ExchangeRateId implements Serializable {

    @Column(name = "base_currency_code", nullable = false, length = 3)
    private String baseCurrencyCode;

    @Column(name = "target_currency_code", nullable = false, length = 3)
    private String targetCurrencyCode;

    @Column(name = "rate_date", nullable = false)
    private LocalDate rateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateId that = (ExchangeRateId) o;
        return Objects.equals(baseCurrencyCode, that.baseCurrencyCode) &&
                Objects.equals(targetCurrencyCode, that.targetCurrencyCode) &&
                Objects.equals(rateDate, that.rateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrencyCode, targetCurrencyCode, rateDate);
    }
}
