package by.skyperdyay.exchange.byn.client;

import by.skyperdyay.common.exception.api.ExchangeRateApiException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * HTTP-клиент для API Национального банка Республики Беларусь с использованием WebClient.
 * Документация: https://www.nbrb.by/apihelp/exrates
 */
@Slf4j
@Component
public class NbrbApiClient {

    private static final String BASE_URL_DEFAULT = "https://api.nbrb.by/exrates/rates";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private final WebClient webClient;

    public NbrbApiClient(
            WebClient.Builder webClientBuilder,
            @Value("${nbrb.api.url:}") String baseUrl) {

        String url = baseUrl.isEmpty() ? BASE_URL_DEFAULT : baseUrl;
        this.webClient = webClientBuilder
                .baseUrl(url)
                .build();
    }

    /**
     * Получает все текущие курсы на указанную дату.
     * Если дата null, получает курсы на сегодня.
     *
     * @param date дата для получения курсов (null для сегодня)
     * @return список ответов с курсами
     * @throws IllegalArgumentException если дата в будущем
     * @throws ExchangeRateApiException при ошибке API
     */
    public List<NbrbRateResponse> fetchRates(LocalDate date) {
        LocalDate targetDate = Objects.requireNonNullElse(date, LocalDate.now());

        if (targetDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Дата не может быть в будущем: " + targetDate);
        }

        String url = UriComponentsBuilder
                .fromUriString("")
                .queryParam("ondate", targetDate.format(DATE_FORMATTER))
                .queryParam("periodicity", "0") // 0 = ежедневные курсы
                .toUriString();

        log.info("Получение курсов BYN из API НБРБ на дату: {}", targetDate);

        List<NbrbRateResponse> rates = executeRequest(url);
        log.info("Успешно получено {} курсов из API НБРБ", rates.size());
        return rates;
    }

    private List<NbrbRateResponse> executeRequest(String url) {
        try {
            List<NbrbRateResponse> rates = webClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            clientResponse -> clientResponse
                                    .bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new ExchangeRateApiException(
                                            "API НБРБ вернул ошибку: " + clientResponse.statusCode() + ", тело: " + errorBody))))
                    .bodyToMono(new ParameterizedTypeReference<List<NbrbRateResponse>>() {
                    })
                    .timeout(REQUEST_TIMEOUT)
                    .block();

            return rates != null ? rates : Collections.emptyList();
        } catch (WebClientResponseException e) {
            throw new ExchangeRateApiException("HTTP ошибка при запросе к API НБРБ: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ExchangeRateApiException("Ошибка при выполнении запроса к API НБРБ: " + e.getMessage(), e);
        }
    }
}