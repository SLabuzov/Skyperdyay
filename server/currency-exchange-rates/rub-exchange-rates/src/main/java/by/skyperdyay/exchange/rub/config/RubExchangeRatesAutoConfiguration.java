package by.skyperdyay.exchange.rub.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Авто-конфигурация для модуля курсов обмена RUB.
 */
@Configuration
@ComponentScan(basePackages = "by.skyperdyay.exchange.rub.*")
public class RubExchangeRatesAutoConfiguration {
}
