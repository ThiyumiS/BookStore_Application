package com.BookStore.Application.Model;

import com.BookStore.Application.Storage.Storage;

public class CartItem {
    private Long id;
    private Book book;
    private int bookId;
    private int quantity;
    private double subtotal;
    
    // Constructors
    public CartItem() {
    }
    
    public CartItem(Book book, int bookId, int quantity) {
        this.book = book;
        this.bookId = bookId;
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }
    
    // Constructor with just bookId - will fetch book from storage
    public CartItem(int bookId, int quantity) {
        this.bookId = bookId;
        this.book = Storage.getBooks().get(bookId);
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }
    
    // Getters
    public Long getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }
    
    public Book getBook() {
        // If book is null but we have a bookId, try to get it from storage
        if (book == null && bookId > 0) {
            book = Storage.getBooks().get(bookId);
        }
        return book;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getSubtotal() {
        return subtotal;
    }

    public double getPrice() {
        if (book != null) {
            return book.getPrice();
        }
        return 0;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setBook(Book book) {
        this.book = book;
        if (book != null) {
            this.bookId = book.getId();
        }
        this.subtotal = calculateSubtotal();
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
        this.book = Storage.getBooks().get(bookId);
        this.subtotal = calculateSubtotal();
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }
    
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    // Helper method
    private double calculateSubtotal() {
        Book actualBook = getBook(); // Use getter to ensure book is loaded
        if (actualBook != null && quantity > 0) {
            return actualBook.getPrice() * quantity;
        }
        return 0;
    }
}
