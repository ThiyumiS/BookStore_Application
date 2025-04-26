package com.BookStore.Application.Resources;


import com.BookStore.Application.Storage.Storage;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import com.BookStore.Application.Exceptions.CartNotFoundException;
import com.BookStore.Application.Services.CartService;
import com.BookStore.Application.Model.CartItem;

@Path("/customers/{customerId}/cart")
public class CartResource {


    private CartService cartService = new CartService();


    /**
     * Get the cart for a specific customer
     * @param customerId The ID of the customer
     * @return Response with the customer's cart
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") Long customerId) {
        try {
            // Get cart for the customer
            Object cart = cartService.getCartForCustomer(customerId);
            String customerFirstName = Storage.getCustomers().get(customerId.intValue()).getFirstName(); //Initialized the customerFirstName variable and assign it to customerId after turn the long val to int val
            return Response.ok(cart)
                    .entity("Cart retrieved successfully by CUSTOMER: "+ customerFirstName)
                    .build();

        } catch (CartNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error retrieving cart: " + e.getMessage())
                .build();
        }
    }

     /**
     * Add a book to the cart for a specific customer
     * @param customerId The ID of the customer
     * @param item The book item to add
     * @return Response with the added book item
     */
    @POST
    @Path("/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBookToCart(@PathParam("customerId") Long customerId, CartItem item) {
        try {
            cartService.addBookToCart(customerId, item);
            return Response.status(Response.Status.CREATED).entity(item).build();
        } catch (CartNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("[MESSAGE]Book not found: " + e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("[MESSAGE] Error adding book to cart: " + e.getMessage())
                .build();
        }
    }

    @PUT
    @Path("/items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBookQuantity(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId,
            CartItem item) {
        try {
            cartService.updateBookQuantity(customerId, bookId, item.getQuantity());
            return Response.ok(item).build();
        } catch (CartNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("[MESSAGE] Book not found: " + e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("[MESSAGE] Error updating book quantity: " + e.getMessage())
                .build();
        }
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeBookFromCart(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId) {
        try {
            cartService.removeBookFromCart(customerId, bookId);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CartNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("[MESSAGE] Book not found: " + e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("[MESSAGE] Error removing book from cart: " + e.getMessage())
                .build();
        }
    }
}
