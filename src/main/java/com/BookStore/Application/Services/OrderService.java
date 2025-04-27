package com.BookStore.Application.Services;

import com.BookStore.Application.Exceptions.OutOfStockException;
import com.BookStore.Application.Model.Order;
import com.BookStore.Application.Model.CartItem;
import com.BookStore.Application.Storage.Storage;
import com.BookStore.Application.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    /**
     * Get all orders for a specific customer
     */
    public List<Order> getOrdersByCustomerId(int customerId) {
        return Storage.getOrders().getOrDefault(customerId, new ArrayList<>());
    }

    /**
     * Get a specific order by customer ID and order ID
     */
    public Order getOrderById(int customerId, int orderId) {
        List<Order> orders = getOrdersByCustomerId(customerId);
        return orders.stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Create a new order from the customer's cart

     */

    public Order createOrderFromCart(int customerId) {
        List<CartItem> cartItems = Storage.getCarts().get(customerId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty for customer ID: " + customerId);
        }

        double totalPrice = 0.0;

        // Check stock availability first
        for (CartItem item : cartItems) {
            Book book = Storage.getBooks().get(item.getBookId());
            if (book == null) {
                throw new IllegalArgumentException("Book with ID " + item.getBookId() + " does not exist.");
            }
            if (item.getQuantity() > book.getQuantityInStock()) {
                throw new OutOfStockException(book.getId(), item.getQuantity(), book.getQuantityInStock());
            }
            totalPrice += item.getPrice() * item.getQuantity();
        }

        // Reduce book stock now
        for (CartItem item : cartItems) {
            Book book = Storage.getBooks().get(item.getBookId());
            book.setQuantityInStock(book.getQuantityInStock() - item.getQuantity());
        }

        // Create a new order
        Order newOrder = new Order();
        newOrder.setOrderId(Storage.generateOrderId()); // Proper new unique ID
        newOrder.setCustomerId(customerId);
        newOrder.setItems(new ArrayList<>(cartItems)); // Save a copy of cart
        newOrder.setTotalAmount(totalPrice);
        newOrder.setOrderDate(java.time.LocalDate.now());

        // Save the new order
        Storage.getOrders().computeIfAbsent(customerId, k -> new ArrayList<>()).add(newOrder);

        return newOrder;
    }

    /**
     * Clear the customer's cart after placing an order
     */
    public void clearCustomerCart(int customerId) {

        Storage.getCarts().remove(customerId);
    }

}
