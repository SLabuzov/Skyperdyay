package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Timezone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimezoneRepository extends JpaRepository<Timezone, String> {
}
