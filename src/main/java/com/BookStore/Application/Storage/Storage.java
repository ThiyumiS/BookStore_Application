package com.BookStore.Application.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Assuming these classes exist in your application
import com.BookStore.Application.Model.Book;
import com.BookStore.Application.Model.Author;
import com.BookStore.Application.Model.Customer;
import com.BookStore.Application.Model.CartItem;
import com.BookStore.Application.Model.Order;

public class Storage {
    // Static maps to store entities
    private static Map<Integer, Book> books = new HashMap<>();
    private static Map<Integer, Author> authors = new HashMap<>();
    private static Map<Integer, Customer> customers = new HashMap<>();
    private static Map<Integer, List<CartItem>> carts = new HashMap<>();
    private static Map<Integer, List<Order>> orders = new HashMap<>();
    
    // Static counters for ID generation
    private static int bookIdCounter = 1;
    private static int authorIdCounter = 1 ;
    private static int customerIdCounter= 1;
    private static int cartIdCounter = 1;
    private static int orderIdCounter = 1;


    
    // Accessor methods for maps
    public static Map<Integer, Book> getBooks() {
        return books;
    }
    
    public static Map<Integer, Author> getAuthors() {
        return authors;
    }
    
    public static Map<Integer, Customer> getCustomers() {
        return customers;
    }
    
    public static Map<Integer, List<CartItem>> getCarts() {
        return carts;
    }
    
    public static Map<Integer, List<Order>> getOrders() {
        return orders;
    }
    
    // ID generation methods
    public static int generateBookId() {
        return bookIdCounter++;
    }
    
    public static int generateAuthorId() {
        return authorIdCounter++;
    }
    
    public static int generateCustomerId() {
        return customerIdCounter++;
    }
    
    public static int generateCartId() {
        return cartIdCounter++;
    }
    
    public static int generateOrderId() {
        return orderIdCounter++;
    }
}
