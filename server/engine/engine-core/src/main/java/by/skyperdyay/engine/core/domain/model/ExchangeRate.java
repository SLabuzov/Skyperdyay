package by.skyperdyay.engine.core.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "moneybox", name = "exchange_rates")
public class ExchangeRate {

    @EmbeddedId
    private ExchangeRateId id;

    @Column(precision = 19, scale = 6)
    private BigDecimal rate;

    @Column(name = "provider_code", length = 32)
    private String providerCode;
}