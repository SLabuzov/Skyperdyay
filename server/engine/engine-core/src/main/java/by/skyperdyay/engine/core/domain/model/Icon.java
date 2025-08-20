package by.skyperdyay.engine.core.domain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "moneybox", name = "icons")
public class Icon {

    @Id
    private String id;

    private String name;
}
