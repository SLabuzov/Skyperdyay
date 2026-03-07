package by.skyperdyay.exchange.scheduler.service;

import by.skyperdyay.exchange.api.CurrencyExchangeRatesProvider;
import by.skyperdyay.exchange.scheduler.config.ExchangeRatesSchedulerProperties;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * Динамический планировщик задач для получения курсов валют.
 * Настраивает расписание для всех доступных провайдеров на основе конфигурации.
 */
@Slf4j
@Component
public class DynamicExchangeRatesScheduler {

    private final List<CurrencyExchangeRatesProvider> providers;
    private final ProviderExecutor providerExecutor;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final ExchangeRatesSchedulerProperties properties;

    /**
     * Создает новый экземпляр планировщика.
     *
     * @param providers        список доступных провайдеров курсов валют
     * @param providerExecutor сервис выполнения провайдеров с retry
     * @param taskScheduler    планировщик задач
     * @param properties       настройки расписания
     */
    public DynamicExchangeRatesScheduler(
            List<CurrencyExchangeRatesProvider> providers,
            ProviderExecutor providerExecutor,
            ThreadPoolTaskScheduler taskScheduler,
            ExchangeRatesSchedulerProperties properties) {
        this.providers = providers;
        this.providerExecutor = providerExecutor;
        this.taskScheduler = taskScheduler;
        this.properties = properties;
    }

    /**
     * Настраивает расписание для всех провайдеров после инициализации бина.
     */
    @PostConstruct
    public void scheduleProviders() {
        for (CurrencyExchangeRatesProvider provider : providers) {
            String providerCode = provider.getProviderCode();
            String cron = resolveCron(providerCode);

            CronTrigger trigger = new CronTrigger(cron);
            taskScheduler.schedule(() -> providerExecutor.execute(provider), trigger);

            log.info("Настроено расписание для провайдера {} с выражением cron: {}", providerCode, cron);
        }
    }

    /**
     * Определяет выражение cron для провайдера.
     * Если в конфигурации не указано, используется значение по умолчанию (9:00).
     *
     * @param providerCode код провайдера
     * @return выражение cron
     */
    private String resolveCron(String providerCode) {
        ExchangeRatesSchedulerProperties.ProviderConfig config =
                properties.getProviders().get(providerCode);

        if (config != null && config.getCron() != null) {
            return config.getCron();
        }

        return "0 0 9 * * *";
    }
}