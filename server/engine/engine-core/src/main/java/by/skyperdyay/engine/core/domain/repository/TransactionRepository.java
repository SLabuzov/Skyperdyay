package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Transaction;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByOwnerAndTransactionDateBetween(String owner, LocalDate startDate, LocalDate endDate);
}
