package com.BookStore.Application.Model;

public class CartItem {
    private Long id;
    private Book book;
    private int quantity;
    private double subtotal;
    
    // Constructors
    public CartItem() {
    }
    
    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public Book getBook() {
        return book;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getSubtotal() {
        return subtotal;
    }

    public  double getPrice() {
        return book.getPrice();
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setBook(Book book) {
        this.book = book;
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
        if (book != null && quantity > 0) {
            return book.getPrice() * quantity;
        }
        return 0;
    }



}
