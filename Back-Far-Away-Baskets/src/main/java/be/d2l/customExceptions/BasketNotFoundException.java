package be.d2l.customExceptions;

public class BasketNotFoundException extends Exception{
    public BasketNotFoundException() {
        super();
    }

    public BasketNotFoundException(String message) {
        super(message);
    }
}
