package by.skyperdyay.common.exception.api;

/**
 * Исключение, выбрасываемое при недостаточности средств на счете.
 */
public class InsufficientFundsException extends ClientException {

    public InsufficientFundsException(String title, String message) {
        super(title, message);
    }
}
