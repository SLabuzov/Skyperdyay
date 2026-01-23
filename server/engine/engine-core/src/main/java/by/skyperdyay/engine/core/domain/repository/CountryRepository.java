package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
