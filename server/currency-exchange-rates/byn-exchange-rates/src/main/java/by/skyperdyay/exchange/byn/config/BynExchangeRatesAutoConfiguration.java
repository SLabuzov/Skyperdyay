package by.skyperdyay.exchange.byn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Авто-конфигурация модуля курсов обмена BYN.
 */
@Configuration
@ComponentScan(basePackages = "by.skyperdyay.exchange.byn.*")
public class BynExchangeRatesAutoConfiguration {
}
