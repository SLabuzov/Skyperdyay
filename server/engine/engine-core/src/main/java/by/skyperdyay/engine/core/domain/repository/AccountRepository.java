package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Account;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
