package by.skyperdyay.exchange.scheduler.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность для отслеживания статуса выполнения провайдеров курсов обмена.
 */
@Getter
@Setter
@Entity
@Table(schema = "moneybox", name = "exchange_rate_provider_status")
public class ProviderExecutionStatus {

    @Id
    @Column(name = "provider_code", length = 32)
    private String providerCode;

    @Column(name = "last_success_date")
    private LocalDate lastSuccessDate;

    @Column(name = "last_attempt_time")
    private LocalDateTime lastAttemptTime;

    @Column(name = "consecutive_failures")
    private Integer consecutiveFailures = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private ExecutionStatus status;

    public void incrementFailures() {
        if (consecutiveFailures == null) {
            consecutiveFailures = 0;
        }
        consecutiveFailures++;
    }

    public void resetFailures() {
        consecutiveFailures = 0;
    }
}
