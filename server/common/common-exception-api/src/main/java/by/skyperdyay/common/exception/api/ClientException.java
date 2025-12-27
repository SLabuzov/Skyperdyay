package by.skyperdyay.common.exception.api;


public abstract class ClientException extends AppException {

    public ClientException(String title, String message) {
        super(title, message);
    }
}
