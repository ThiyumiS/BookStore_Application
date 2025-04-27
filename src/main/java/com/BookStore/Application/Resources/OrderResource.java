package com.BookStore.Application.Resources;

import com.BookStore.Application.Services.OrderService;
import com.BookStore.Application.Model.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private OrderService orderService = new OrderService();

    @GET
    public Response getAllOrders(@PathParam("customerId") int customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        Order order = orderService.getOrderById(customerId, orderId);
        if (order == null) {
            throw new com.BookStore.Application.Exceptions.InvalidInputException("Order not found with ID: " + orderId);
        }
        return Response.ok(order).build();
    }

    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        Order newOrder = orderService.createOrderFromCart(customerId);
        orderService.clearCustomerCart(customerId);
        return Response.status(Response.Status.CREATED).entity(newOrder).build();
    }
}
