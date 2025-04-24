package com.BookStore.Application.Services;

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

        // Calculate total price
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Create a new order
        Order newOrder = new Order();
        newOrder.setOrderId(newOrder.getOrderId());
        newOrder.setCustomerId(customerId);
        newOrder.setItems(new ArrayList<>(cartItems));
        newOrder.setTotalAmount(totalPrice);

        // Save the order
        Storage.getOrders().computeIfAbsent(customerId, k -> new ArrayList<>()).add(newOrder);

        return newOrder;
    }

    /**
     * Clear the customer's cart after placing an order
     */
    public void clearCustomerCart(int customerId) {
        Storage.getCarts().remove(customerId);
    }

    /**
     * Generate a unique order ID (for simplicity, using current time)

    private Long generateOrderId() {
        return System.currentTimeMillis();
    }
     */
}
