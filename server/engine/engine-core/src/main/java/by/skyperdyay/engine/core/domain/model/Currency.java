package by.skyperdyay.engine.core.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "moneybox", name = "dict_currencies")
public class Currency {
    @Id
    private String code;

    private String name;

    private String symbol;
}
