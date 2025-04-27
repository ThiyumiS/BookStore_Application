package com.BookStore.Application.Exceptions;

public class OutOfStockException extends RuntimeException {
    private int bookId;
    private int requestedQuantity;
    private int availableQuantity;

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(int bookId, int requestedQuantity, int availableQuantity) {
        super("Book with ID " + bookId + " has insufficient stock. Requested: " +
                requestedQuantity + ", Available: " + availableQuantity);
        this.bookId = bookId;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public int getBookId() {
        return bookId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }
}