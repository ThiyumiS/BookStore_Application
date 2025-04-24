package com.BookStore.Application.Resources;

import com.BookStore.Application.Services.OrderService;
import com.BookStore.Application.Model.Order;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/orders")
public class OrderResource {

    @Inject
    private OrderService orderService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders(@PathParam("customerId") int customerId) { // Turn customerId, orderId from Long to int
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("customerId") int customerId,
                               @PathParam("orderId") int orderId) {
        Order order = orderService.getOrderById(customerId, orderId);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(order).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrder(@PathParam("customerId") int customerId) {
        // Convert cart to order and calculate total price
        Order newOrder = orderService.createOrderFromCart(customerId);
        
        // Clear the customer's cart after order is placed
        orderService.clearCustomerCart(customerId);
        
        return Response.status(Response.Status.CREATED)
                      .entity(newOrder)
                      .build();
    }
}
