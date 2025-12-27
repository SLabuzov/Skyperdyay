package by.skyperdyay.common.exception.api;

public class ResourceNotFoundException extends ClientException {

    public ResourceNotFoundException(String title, String message) {
        super(title, message);
    }
}
