package by.skyperdyay.common.exception.api;

public class ResourceConflictException extends ClientException {
    public ResourceConflictException(String title, String message) {
        super(title, message);
    }
}
