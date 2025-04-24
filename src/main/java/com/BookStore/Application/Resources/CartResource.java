package com.BookStore.Application.Resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;

import com.BookStore.Application.Exceptions.CartNotFoundException;
import com.BookStore.Application.Services.CartService;
import com.BookStore.Application.Model.CartItem;

@Path("/customers/{customerId}/cart")
public class CartResource {

    @Inject
    private CartService cartService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") Long customerId) {
        try {
            // Get cart for the customer
            Object cart = cartService.getCartForCustomer(customerId);
            return Response.ok(cart).build();
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
                .entity("Book not found: " + e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error adding book to cart: " + e.getMessage())
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
                .entity("Book not found: " + e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error updating book quantity: " + e.getMessage())
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
                .entity("Book not found: " + e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error removing book from cart: " + e.getMessage())
                .build();
        }
    }
}
