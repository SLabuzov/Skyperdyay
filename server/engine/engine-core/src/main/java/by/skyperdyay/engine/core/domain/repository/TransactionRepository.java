package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Transaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
