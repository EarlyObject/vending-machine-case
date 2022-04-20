package vending.exeptions;

public class NotSufficientChangeException extends RuntimeException {

    private final String message;

    public NotSufficientChangeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
