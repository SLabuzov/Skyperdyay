package by.skyperdyay.exchange.scheduler.service;

import by.skyperdyay.exchange.scheduler.domain.model.ExecutionStatus;
import by.skyperdyay.exchange.scheduler.domain.model.ProviderExecutionStatus;
import by.skyperdyay.exchange.scheduler.domain.repository.ProviderExecutionRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для отслеживания статуса выполнения провайдеров.
 */
@Slf4j
@Service
public class ProviderExecutionService {

    private final ProviderExecutionRepository executionRepository;

    public ProviderExecutionService(ProviderExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }

    /**
     * Возвращает дату последнего успешного выполнения для указанного провайдера.
     *
     * @param providerCode код провайдера
     * @return дата последнего успеха или null, если не было успешных выполнений
     */
    @Transactional(readOnly = true)
    public Optional<LocalDate> getLastSuccessDate(String providerCode) {
        Optional<ProviderExecutionStatus> status = executionRepository.findByProviderCode(providerCode);
        return status.map(ProviderExecutionStatus::getLastSuccessDate);
    }

    /**
     * Записывает успешное выполнение.
     *
     * @param providerCode код провайдера
     * @param successDate  дата успешного получения данных
     */
    @Transactional
    public void recordSuccess(String providerCode, LocalDate successDate) {
        ProviderExecutionStatus status = executionRepository
                .findByProviderCode(providerCode)
                .orElse(new ProviderExecutionStatus());

        status.setProviderCode(providerCode);
        status.setLastSuccessDate(successDate);
        status.setLastAttemptTime(LocalDateTime.now());
        status.setStatus(ExecutionStatus.SUCCESS);
        status.resetFailures();

        executionRepository.save(status);
        log.info("Успешная загрузка курсов валют провайдера {} на дату {}", providerCode, successDate);
    }

    /**
     * Записывает неудачное выполнение.
     *
     * @param providerCode код провайдера
     * @param error        возникшая ошибка
     */
    @Transactional
    public void recordFailure(String providerCode, Exception error) {
        ProviderExecutionStatus status = executionRepository
                .findByProviderCode(providerCode)
                .orElse(new ProviderExecutionStatus());

        status.setProviderCode(providerCode);
        status.setLastAttemptTime(LocalDateTime.now());
        status.setStatus(ExecutionStatus.FAILED);
        status.incrementFailures();

        executionRepository.save(status);
        log.error("Ошибка при загрузке данных для провайдера {}. Подряд ошибок: {}. Ошибка: {}",
                providerCode, status.getConsecutiveFailures(), error.getMessage(), error);
    }

    /**
     * Записывает, что провайдер выполняется повторно.
     *
     * @param providerCode код провайдера
     */
    @Transactional
    public void recordRetrying(String providerCode) {
        ProviderExecutionStatus status = executionRepository
                .findByProviderCode(providerCode)
                .orElse(new ProviderExecutionStatus());

        status.setProviderCode(providerCode);
        status.setLastAttemptTime(LocalDateTime.now());
        status.setStatus(ExecutionStatus.RETRYING);

        executionRepository.save(status);
        log.info("Повторная загрузка данных провайдера {}", providerCode);
    }

    /**
     * Возвращает текущий статус выполнения для указанного провайдера.
     *
     * @param providerCode код провайдера
     * @return Optional со статусом
     */
    @Transactional(readOnly = true)
    public Optional<ProviderExecutionStatus> getStatus(String providerCode) {
        return executionRepository.findByProviderCode(providerCode);
    }
}