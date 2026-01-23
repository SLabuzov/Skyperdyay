package by.skyperdyay.engine.core.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "moneybox", name = "dict_countries")
public class Country {
    @Id
    private String code;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_timezone_code")
    private Timezone defaultTimezone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_currency_code")
    private Currency defaultCurrency;
}
