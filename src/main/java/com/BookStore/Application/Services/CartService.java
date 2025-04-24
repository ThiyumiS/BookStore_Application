package com.BookStore.Application.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.BookStore.Application.Exceptions.CartNotFoundException;
import com.BookStore.Application.Model.Book;
import com.BookStore.Application.Model.CartItem;
import com.BookStore.Application.Storage.Storage;

public class CartService {
    
    /**
     * Get the cart for a specific customer
     */
    public List<CartItem> getCartForCustomer(Long customerId) {
        List<CartItem> cart = Storage.getCarts().get(customerId.intValue());
        if (cart == null) {
            // Initialize an empty cart for new customers
            cart = new ArrayList<>();
            Storage.getCarts().put(customerId.intValue(), cart);
        }
        return cart;
    }
    
    /**
     * Add a book to the customer's cart
     */
    public void addBookToCart(Long customerId, CartItem newItem) {
        // Validate the book exists
        Book book = newItem.getBook();
        if (book == null || Storage.getBooks().get(book.getId()) == null) { // took out intValue()
            throw new IllegalArgumentException("Book does not exist");
        }
        
        // Check if cart exists for customer
        if (!Storage.getCarts().containsKey(customerId.intValue())) {
            throw new CartNotFoundException(customerId);
        }
        
        List<CartItem> cart = getCartForCustomer(customerId);
        
        // Check if the book is already in the cart
        boolean bookFound = false;
        for (CartItem item : cart) {
            if (item.getBook().getId()== newItem.getBook().getId()) { //changed .equal to == for Long comparison
                // Book already exists, increase quantity
                int newQuantity = item.getQuantity() + newItem.getQuantity();
                item.setQuantity(newQuantity);
                bookFound = true;
                break;
            }
        }
        
        // If book not found, add it to cart
        if (!bookFound) {
            cart.add(newItem);
        }
        
        // Update the cart in storage
        Storage.getCarts().put(customerId.intValue(), cart);
    }
    
    /**
     * Update the quantity of a book in the customer's cart
     */
    public void updateBookQuantity(Long customerId, Long bookId, int newQuantity) {
        // Check if cart exists for customer
        if (!Storage.getCarts().containsKey(customerId.intValue())) {
            throw new CartNotFoundException(customerId);
        }
        
        List<CartItem> cart = getCartForCustomer(customerId);
        boolean bookFound = false;
        
        for (CartItem item : cart) {
            if (item.getBook().getId() == bookId) {
                if (newQuantity <= 0) {
                    // If quantity is 0 or negative, remove the item
                    removeBookFromCart(customerId, bookId);
                } else {
                    // Update the quantity
                    item.setQuantity(newQuantity);
                }
                bookFound = true;
                break;
            }
        }
        
        if (!bookFound) {
            throw new IllegalArgumentException("Book not found in cart");
        }
    }
    
    /**
     * Remove a book from the customer's cart
     */
    public void removeBookFromCart(Long customerId, Long bookId) {
        // Check if cart exists for customer
        if (!Storage.getCarts().containsKey(customerId.intValue())) {
            throw new CartNotFoundException(customerId);
        }
        
        List<CartItem> cart = getCartForCustomer(customerId);
        
        // Find and remove the item
        boolean removed = cart.removeIf(item -> item.getBook().getId() == bookId );
        
        if (!removed) {
            throw new IllegalArgumentException("Book not found in cart");
        }
        
        // Update the cart in storage
        Storage.getCarts().put(customerId.intValue(), cart);
    }
    
    /**
     * Calculate the total price of items in the cart
     */
    public double calculateCartTotal(Long customerId) {
        // Check if cart exists for customer
        if (!Storage.getCarts().containsKey(customerId.intValue())) {
            throw new CartNotFoundException(customerId);
        }
        
        List<CartItem> cart = getCartForCustomer(customerId);
        double total = 0;
        
        for (CartItem item : cart) {
            total += item.getSubtotal();
        }
        
        return total;
    }
}
