package by.skyperdyay.common.exception.api;

/**
 * Базовое исключение клиентских ошибок (4xx).
 */
public abstract class ClientException extends AppException {

    public ClientException(String title, String message) {
        super(title, message);
    }
}
