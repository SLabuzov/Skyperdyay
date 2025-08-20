package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Wallet;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
