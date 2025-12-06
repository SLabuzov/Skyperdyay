package by.skyperdyay.common.exception.api;

public class ServerException extends AppException {

    public ServerException(String title, String message) {
        super(title, message);
    }

    public ServerException(String title, String message, Throwable cause) {
        super(title, message, cause);
    }
}
