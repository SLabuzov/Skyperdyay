package by.skyperdyay.exchange.scheduler.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Авто-конфигурация модуля планировщика курсов валют.
 */
@Configuration
@EnableRetry
@EnableScheduling
@EnableConfigurationProperties(ExchangeRatesSchedulerProperties.class)
@EntityScan(basePackages = "by.skyperdyay.exchange.scheduler.domain.model")
@EnableJpaRepositories(basePackages = "by.skyperdyay.exchange.scheduler.domain.repository")
@ComponentScan(basePackages = "by.skyperdyay.exchange.scheduler.*")
public class SchedulerAutoConfiguration {

    /**
     * Создает и настраивает пул потоков для выполнения задач получения курсов валют.
     *
     * @return настроенный планировщик задач
     */
    @Bean
    public ThreadPoolTaskScheduler exchangeRatesTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("exchange-rates-");
        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }
}