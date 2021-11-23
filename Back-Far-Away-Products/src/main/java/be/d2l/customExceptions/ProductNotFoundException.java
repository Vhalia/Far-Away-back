package be.d2l.customExceptions;

import be.d2l.model.Product;

public class ProductNotFoundException extends Exception{
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
