package by.skyperdyay.common.exception.api;

/**
 * Исключение, выбрасываемое при конфликте ресурса (409).
 */
public class ResourceConflictException extends ClientException {

    public ResourceConflictException(String title, String message) {
        super(title, message);
    }
}
