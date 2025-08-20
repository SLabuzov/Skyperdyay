package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
