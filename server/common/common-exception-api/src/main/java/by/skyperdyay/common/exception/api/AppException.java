package by.skyperdyay.common.exception.api;

import lombok.Getter;

/**
 * Базовое исключение приложения.
 * Содержит заголовок для отображения пользователю.
 */
@Getter
public abstract class AppException extends RuntimeException {

    private final String title;

    public AppException(String title, String message) {
        super(message);
        this.title = title;
    }

    public AppException(String title, String message, Throwable cause) {
        super(message, cause);
        this.title = title;
    }
}
