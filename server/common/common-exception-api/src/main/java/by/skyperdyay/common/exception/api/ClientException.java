package by.skyperdyay.common.exception.api;

public abstract class ClientException extends AppException {

    public ClientException(String title, String message) {
        super(title, message);
    }

    public ClientException(String title, String message, Throwable cause) {
        super(title, message, cause);
    }
}
