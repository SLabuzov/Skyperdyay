package by.skyperdyay.common.exception.api;

/**
 * Исключение, выбрасываемое при ошибке внешнего API курсов обмена валют.
 * Используется для всех провайдеров курсов (ЦБ, НБРБ и др.), чтобы слой API оставался агностичным.
 */
public class ExchangeRateApiException extends ServerException {

    public ExchangeRateApiException(String message) {
        super("Exchange Rate API Error", message);
    }

    public ExchangeRateApiException(String message, Throwable cause) {
        super("Exchange Rate API Error", message, cause);
    }
}
