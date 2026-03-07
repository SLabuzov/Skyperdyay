package by.skyperdyay.exchange.rub.client;

import by.skyperdyay.common.exception.api.ExchangeRateApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * HTTP клиент для API Центрального банка России с использованием WebClient.
 * Документация: https://www.cbr.ru/development/DWS/
 */
@Slf4j
@Component
public class CbrApiClient {

    private static final String BASE_URL_DEFAULT = "https://www.cbr.ru/scripts/XML_daily.asp";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private final WebClient webClient;
    private final XmlMapper xmlMapper;

    public CbrApiClient(
            WebClient.Builder webClientBuilder,
            @Value("${cbr.api.url:}") String baseUrl) {

        String url = baseUrl.isEmpty() ? BASE_URL_DEFAULT : baseUrl;
        this.webClient = webClientBuilder
                .baseUrl(url)
                .build();

        this.xmlMapper = new XmlMapper();
        this.xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    /**
     * Получает все текущие курсы на указанную дату.
     * Если дата равна null, получает курсы на сегодня.
     *
     * @param date дата для получения курсов (null для сегодня)
     * @return ответ с курсами, содержащий все валюты
     * @throws IllegalArgumentException если дата в будущем
     * @throws ExchangeRateApiException при ошибке API
     */
    public CbrRateResponse fetchRates(LocalDate date) {
        LocalDate targetDate = Objects.requireNonNullElse(date, LocalDate.now());

        if (targetDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Дата не может быть в будущем: " + targetDate);
        }

        String url = UriComponentsBuilder
                .fromUriString("")
                .queryParam("date_req", targetDate.format(DATE_FORMATTER))
                .toUriString();

        log.info("Получение курсов RUB из API ЦБ на дату: {}", targetDate);

        String response = executeRequest(url);
        CbrRateResponse result = parseResponse(response);
        log.info("Успешно получено {} валют из API ЦБ",
                result.getValutes() != null ? result.getValutes().size() : 0);
        return result;
    }

    private String executeRequest(String url) {
        try {
            return webClient.get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            clientResponse -> clientResponse
                                    .bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new ExchangeRateApiException(
                                            "API ЦБ вернул ошибку: " + clientResponse.statusCode() + ", тело: " + errorBody))))
                    .bodyToMono(String.class)
                    .timeout(REQUEST_TIMEOUT)
                    .block();
        } catch (WebClientResponseException e) {
            throw new ExchangeRateApiException("HTTP ошибка при запросе к API ЦБ: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ExchangeRateApiException("Ошибка при выполнении запроса к API ЦБ: " + e.getMessage(), e);
        }
    }

    private CbrRateResponse parseResponse(String response) {
        if (response == null || response.isBlank()) {
            throw new ExchangeRateApiException("Пустой ответ от API ЦБ");
        }

        try {
            return xmlMapper.readValue(response, CbrRateResponse.class);
        } catch (JsonProcessingException e) {
            throw new ExchangeRateApiException("Ошибка парсинга XML ответа от API ЦБ: " + e.getMessage(), e);
        }
    }
}