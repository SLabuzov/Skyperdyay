package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Transfer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
