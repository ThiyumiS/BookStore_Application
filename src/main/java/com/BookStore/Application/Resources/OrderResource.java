package com.BookStore.Application.Resources;

import com.BookStore.Application.Services.OrderService;
import com.BookStore.Application.Model.Order;
import com.BookStore.Application.Exceptions.CustomerNotFoundException;
import com.BookStore.Application.Exceptions.CartNotFoundException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private OrderService orderService = new OrderService();    @GET
    public Response getAllOrders(@PathParam("customerId") int customerId) {
        // Verify customer exists
        verifyCustomerExists(customerId);
        
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return Response.ok(orders).build();
    }
    
    // Helper method to verify if a customer exists
    private void verifyCustomerExists(int customerId) {
        if (!com.BookStore.Application.Storage.Storage.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }
    }    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        // Verify customer exists
        verifyCustomerExists(customerId);
        
        Order order = orderService.getOrderById(customerId, orderId);
        if (order == null) {
            // Use a more specific exception
            throw new com.BookStore.Application.Exceptions.InvalidInputException("Order not found with ID: " + orderId);
        }
        return Response.ok(order).build();
    }    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        // Verify customer exists
        verifyCustomerExists(customerId);
        
        // Check if cart exists and is not empty
        try {
            // OrderService.createOrderFromCart should throw:
            // - CartNotFoundException if cart is empty or doesn't exist
            // - OutOfStockException if any book has insufficient stock
            Order newOrder = orderService.createOrderFromCart(customerId);
            orderService.clearCustomerCart(customerId);
            return Response.status(Response.Status.CREATED).entity(newOrder).build();
        } catch (IllegalStateException e) {
            // Convert generic exceptions to our custom exceptions
            throw new CartNotFoundException("Cart is empty or not found for customer ID: " + customerId);
        }
    }
}
