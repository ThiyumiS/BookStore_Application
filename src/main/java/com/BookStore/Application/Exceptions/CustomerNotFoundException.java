package com.BookStore.Application.Exceptions;

public class CustomerNotFoundException extends RuntimeException {



    public CustomerNotFoundException(String message) {
        super(message);

    }
}
