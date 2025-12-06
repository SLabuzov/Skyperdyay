package by.skyperdyay.common.exception.api;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {

    private final String title;

    protected AppException(String title, String message) {
        super(message);
        this.title = title;
    }

    protected AppException(String title, String message, Throwable cause) {
        super(message, cause);
        this.title = title;
    }
}
