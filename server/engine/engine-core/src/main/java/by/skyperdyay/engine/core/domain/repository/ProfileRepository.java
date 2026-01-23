package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {
}
