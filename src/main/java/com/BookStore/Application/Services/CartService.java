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

    public List<CartItem> getCartItems(Long customerId) {
        List<CartItem> cart = Storage.getCarts().get(customerId.intValue());
        if (cart == null) {
            throw new CartNotFoundException("Cart for customer ID " + customerId + " not found.");
        }
        return cart;
    }


    public void addBookToCart(Long customerId, CartItem newItem) {
        int bookId = newItem.getBookId();

        // Check if the book exists
        if (!Storage.getBooks().containsKey(bookId)) {
            throw new IllegalArgumentException("Book does not exist.");
        }

        // Get the actual Book object
        Book book = Storage.getBooks().get(bookId);
        newItem.setBook(book); // Set the Book inside CartItem

        //  Check if the requested quantity exceeds available stock
        if (newItem.getQuantity() > book.getQuantityInStock()) {
            throw new IllegalArgumentException(
                    "Only " + book.getQuantityInStock() + " items available in stock. You requested " + newItem.getQuantity() + "."
            );
        }

        // Get or create the customer's cart
        List<CartItem> cart = Storage.getCarts().get(customerId.intValue());
        if (cart == null) {
            cart = new ArrayList<>();
            Storage.getCarts().put(customerId.intValue(), cart);
        }

        // Check if the book is already in the cart
        boolean bookFound = false;
        for (CartItem item : cart) {
            if (item.getBookId() == newItem.getBookId()) {
                int newQuantity = item.getQuantity() + newItem.getQuantity();

                // Check if combined quantity exceeds stock
                if (newQuantity > book.getQuantityInStock()) {
                    throw new IllegalArgumentException(
                            "Only " + book.getQuantityInStock() + " items available. Cannot update to " + newQuantity + "."
                    );
                }

                item.setQuantity(newQuantity);
                bookFound = true;
                break;
            }
        }

        // If the book is not already in the cart, add it
        if (!bookFound) {
            cart.add(newItem);
        }
    }


    /**
     * Update the quantity of a book in the customer's cart
     */
    public void updateBookQuantity(Long customerId, Long bookId, int newQuantity) {
        // Check if cart exists for customer
        if (!Storage.getCarts().containsKey(customerId.intValue())) {
            throw new CartNotFoundException(customerId);
        }
        
        List<CartItem> cart = getCartItems(customerId);
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
        
        List<CartItem> cart =getCartItems(customerId);
        
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
        
        List<CartItem> cart = getCartItems(customerId);
        double total = 0;
        
        for (CartItem item : cart) {
            total += item.getSubtotal();
        }
        
        return total;
    }
}
