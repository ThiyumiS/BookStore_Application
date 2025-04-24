package com.BookStore.Application.Exceptions;

public class CartNotFoundException extends RuntimeException {

  public CartNotFoundException(String message) {
    super(message);
  }

  public CartNotFoundException(Long customerId) {
    super("Cart not found for customer ID: " + customerId);
  }
}

