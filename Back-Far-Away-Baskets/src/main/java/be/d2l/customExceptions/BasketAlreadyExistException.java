package be.d2l.customExceptions;

public class BasketAlreadyExistException extends Exception{
    public BasketAlreadyExistException() {
        super();
    }

    public BasketAlreadyExistException(String message) {
        super(message);
    }
}
