package vending.exeptions;

public class SoldOutException extends RuntimeException {

    private final String message;

    public SoldOutException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
