package by.skyperdyay.exchange.scheduler.service;

import by.skyperdyay.exchange.api.CurrencyExchangeRatesProvider;
import by.skyperdyay.exchange.scheduler.config.ExchangeRatesSchedulerProperties;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * Сервис для выполнения провайдеров курсов валют с поддержкой повторных попыток.
 * Поддерживает индивидуальную конфигурацию retry для каждого провайдера.
 */
@Slf4j
@Service
public class ProviderExecutor {

    private final ProviderExecutionService executionService;
    private final ExchangeRatesSchedulerProperties properties;

    public ProviderExecutor(ProviderExecutionService executionService,
                            ExchangeRatesSchedulerProperties properties) {
        this.executionService = executionService;
        this.properties = properties;
    }

    /**
     * Выполняет провайдер с повторными попытками при ошибке.
     * Настройки retry берутся из конфигурации для конкретного провайдера.
     * Если последнее успешное выполнение было раньше текущей даты,
     * выполняет добивание курсов за все пропущенные дни.
     *
     * @param provider провайдер курсов валют
     */
    public void execute(CurrencyExchangeRatesProvider provider) {
        String providerCode = provider.getProviderCode();
        RetryTemplate retryTemplate = createRetryTemplate(providerCode);

        try {
            retryTemplate.execute((RetryCallback<Void, RuntimeException>) context -> {
                executionService.recordRetrying(providerCode);
                LocalDate lastRunDate = executionService
                        .getLastSuccessDate(providerCode)
                        .orElse(LocalDate.now());
                LocalDate today = LocalDate.now();

                // Добивание курсов за все пропущенные дни
                LocalDate currentDate = lastRunDate;
                while (!currentDate.isAfter(today)) {
                    log.info("Загрузка курсов провайдера {} за дату: {}", providerCode, currentDate);
                    provider.fetchRatesSince(currentDate);
                    executionService.recordSuccess(providerCode, currentDate);
                    currentDate = currentDate.plusDays(1);
                }

                return null;
            });
        } catch (Exception ex) {
            log.error("Не удалось загрузить курсы валют провайдера {} после всех попыток: {}",
                    providerCode, ex.getMessage(), ex);
            executionService.recordFailure(providerCode, ex);
        }
    }

    /**
     * Создает и настраивает RetryTemplate для указанного провайдера.
     *
     * @param providerCode код провайдера
     * @return настроенный RetryTemplate
     */
    private RetryTemplate createRetryTemplate(String providerCode) {
        ExchangeRatesSchedulerProperties.ProviderConfig config = properties
                .getProviders()
                .getOrDefault(
                        providerCode,
                        new ExchangeRatesSchedulerProperties.ProviderConfig()
                );

        RetryTemplate retryTemplate = new RetryTemplate();

        // Настройка количества попыток
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(config.getMaxAttempts());
        retryTemplate.setRetryPolicy(retryPolicy);

        // Настройка задержки между попытками
        if (config.getBackoffMultiplier() != null && config.getBackoffMultiplier() > 1.0) {
            ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
            backOffPolicy.setInitialInterval(config.getBackoffDelay());
            backOffPolicy.setMultiplier(config.getBackoffMultiplier());
            retryTemplate.setBackOffPolicy(backOffPolicy);
        } else {
            FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
            backOffPolicy.setBackOffPeriod(config.getBackoffDelay());
            retryTemplate.setBackOffPolicy(backOffPolicy);
        }

        return retryTemplate;
    }
}