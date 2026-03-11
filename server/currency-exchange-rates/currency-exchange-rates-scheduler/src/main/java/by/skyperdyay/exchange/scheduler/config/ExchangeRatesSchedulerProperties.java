package by.skyperdyay.exchange.scheduler.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Настройки планировщика курсов валют.
 */
@Data
@ConfigurationProperties(prefix = "exchange.rates")
public class ExchangeRatesSchedulerProperties {

    private Map<String, ProviderConfig> providers = new HashMap<>();

    /**
     * Конфигурация отдельного провайдера.
     */
    @Data
    public static class ProviderConfig {

        /**
         * Выражение cron для расписания.
         */
        private String cron;

        /**
         * Количество попыток при ошибке.
         * По умолчанию: 3
         */
        private Integer maxAttempts = 3;

        /**
         * Задержка между попытками в миллисекундах.
         * По умолчанию: 5000 (5 секунд)
         */
        private Long backoffDelay = 5000L;

        /**
         * Множитель для экспоненциального увеличения задержки.
         * По умолчанию: 1.0 (фиксированная задержка)
         */
        private Double backoffMultiplier = 1.0;
    }
}