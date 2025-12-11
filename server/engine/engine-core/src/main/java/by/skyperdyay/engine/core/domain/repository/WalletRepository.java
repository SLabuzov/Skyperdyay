package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.domain.model.Wallet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    boolean existsByOwnerAndCurrency(String owner, Currency currency);

    List<Wallet> findAllByOwner(String owner);

    Optional<Wallet> findByIdAndOwner(UUID id, String owner);

    int countByOwner(String owner);
}
