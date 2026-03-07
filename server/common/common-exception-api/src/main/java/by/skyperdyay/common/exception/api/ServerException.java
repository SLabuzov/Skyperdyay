package by.skyperdyay.common.exception.api;

/**
 * Базовое исключение клиентских ошибок (5xx).
 */
public abstract class ServerException extends AppException {

    public ServerException(String title, String message) {
        super(title, message);
    }

    public ServerException(String title, String message, Throwable cause) {
        super(title, message, cause);
    }
}
