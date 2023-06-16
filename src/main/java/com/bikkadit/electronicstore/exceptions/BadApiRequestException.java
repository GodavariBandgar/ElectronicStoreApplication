package com.bikkadit.electronicstore.exceptions;

public class BadApiRequestException extends RuntimeException{
    public BadApiRequestException(String message){
        super(message);
    }

    public BadApiRequestException(){
        super("BadRequest !!");
    }
}
