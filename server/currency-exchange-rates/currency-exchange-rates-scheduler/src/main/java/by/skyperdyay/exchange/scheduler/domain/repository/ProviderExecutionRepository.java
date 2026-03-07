package by.skyperdyay.exchange.scheduler.domain.repository;

import by.skyperdyay.exchange.scheduler.domain.model.ProviderExecutionStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для статуса выполнения провайдеров.
 */
@Repository
public interface ProviderExecutionRepository extends JpaRepository<ProviderExecutionStatus, String> {

    /**
     * Находит статус выполнения по коду провайдера.
     *
     * @param providerCode код провайдера
     * @return optional со статусом, если найден
     */
    Optional<ProviderExecutionStatus> findByProviderCode(String providerCode);
}
