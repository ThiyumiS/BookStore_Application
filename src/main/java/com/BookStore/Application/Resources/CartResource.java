package com.BookStore.Application.Resources;

import com.BookStore.Application.Services.CartService;
import com.BookStore.Application.Model.CartItem;
import com.BookStore.Application.Exceptions.CustomerNotFoundException;
import com.BookStore.Application.Exceptions.BookNotFoundException;
import com.BookStore.Application.Exceptions.InvalidInputException;
import com.BookStore.Application.Storage.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private CartService cartService = new CartService();    @GET
    @Path("/items")
    public Response getCartItems(@PathParam("customerId") Long customerId) {
        // Verify customer exists
        verifyCustomerExists(customerId);
        
        // CartService.getCartItems throws CartNotFoundException if cart not found
        List<CartItem> cartItems = cartService.getCartItems(customerId);
        return Response.ok(cartItems).build();
    }
    
    // Helper method to verify customer exists
    private void verifyCustomerExists(Long customerId) {
        if (!Storage.getCustomers().containsKey(customerId.intValue())) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }
    }    @POST
    @Path("/items")
    public Response addBookToCart(@PathParam("customerId") Long customerId, CartItem item) {
        // Verify customer exists
        verifyCustomerExists(customerId);
        
        // Validate input
        if (item == null || item.getQuantity() <= 0) {
            throw new InvalidInputException("Invalid cart item. Quantity must be greater than zero.");
        }
        
        // CartService.addBookToCart throws BookNotFoundException if book not found
        // and OutOfStockException if quantity exceeds stock
        cartService.addBookToCart(customerId, item);
        return Response.status(Response.Status.CREATED).entity(item).build();
    }    @PUT
    @Path("/items/{bookId}")
    public Response updateBookQuantity(@PathParam("customerId") Long customerId, @PathParam("bookId") Long bookId, CartItem item) {
        // Verify customer exists
        verifyCustomerExists(customerId);
        
        // Validate input
        if (item == null || item.getQuantity() < 0) {
            throw new InvalidInputException("Invalid quantity. Must be zero or greater.");
        }
        
        // CartService.updateBookQuantity throws CartNotFoundException if cart not found
        // Throws BookNotFoundException (implicitly) if book not in cart
        // Throws OutOfStockException if new quantity exceeds stock
        cartService.updateBookQuantity(customerId, bookId, item.getQuantity());
        return Response.ok(item).build();
    }    @DELETE
    @Path("/items/{bookId}")
    public Response removeBookFromCart(@PathParam("customerId") Long customerId, @PathParam("bookId") Long bookId) {
        // Verify customer exists
        verifyCustomerExists(customerId);
        
        // CartService.removeBookFromCart throws CartNotFoundException if cart not found
        // Throws IllegalArgumentException if book not in cart - we should convert this
        try {
            cartService.removeBookFromCart(customerId, bookId);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            // Convert to BookNotFoundException
            throw new BookNotFoundException("Book with ID " + bookId + " not found in cart");
        }
    }
}
