package be.d2l.Exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
