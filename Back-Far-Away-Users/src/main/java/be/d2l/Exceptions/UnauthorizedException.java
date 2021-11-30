package be.d2l.Exceptions;

public class UnauthorizedException extends Exception{

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
