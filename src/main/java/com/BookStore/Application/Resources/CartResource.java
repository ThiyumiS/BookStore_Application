package com.BookStore.Application.Resources;

import com.BookStore.Application.Services.CartService;
import com.BookStore.Application.Model.CartItem;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private CartService cartService = new CartService();

    @GET
    @Path("/items")
    public Response getCartItems(@PathParam("customerId") Long customerId) {
        List<CartItem> cartItems = cartService.getCartItems(customerId);
        return Response.ok(cartItems).build();
    }

    @POST
    @Path("/items")
    public Response addBookToCart(@PathParam("customerId") Long customerId, CartItem item) {
        cartService.addBookToCart(customerId, item);
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateBookQuantity(@PathParam("customerId") Long customerId, @PathParam("bookId") Long bookId, CartItem item) {
        cartService.updateBookQuantity(customerId, bookId, item.getQuantity());
        return Response.ok(item).build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeBookFromCart(@PathParam("customerId") Long customerId, @PathParam("bookId") Long bookId) {
        cartService.removeBookFromCart(customerId, bookId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
