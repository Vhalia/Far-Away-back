package be.d2l.customExceptions;

public class CommentNotFoundException extends Exception{

    public CommentNotFoundException() {
        super();
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
