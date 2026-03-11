package by.skyperdyay.common.exception.api;

/**
 * Исключение, выбрасываемое при невалидном переводе.
 */
public class InvalidTransferException extends ClientException {

    public InvalidTransferException(String title, String message) {
        super(title, message);
    }
}
