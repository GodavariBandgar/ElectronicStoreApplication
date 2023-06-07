package com.bikkadit.electronicstore.exceptions;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);

    }

    public ApiException() {                    //default constructor
        super();

    }
}
