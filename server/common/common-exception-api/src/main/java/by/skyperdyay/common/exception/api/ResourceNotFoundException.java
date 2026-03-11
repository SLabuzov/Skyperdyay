package by.skyperdyay.common.exception.api;

/**
 * Исключение, выбрасываемое при отсутствии ресурса (404).
 */
public class ResourceNotFoundException extends ClientException {

    public ResourceNotFoundException(String title, String message) {
        super(title, message);
    }
}
