package by.skyperdyay.common.exception.handling.config;

import by.skyperdyay.common.exception.handling.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "by.skyperdyay.common.exception.handling.*")
public class ExceptionHandlingAutoConfiguration {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
