package com.BookStore.Application.Resources;

import com.BookStore.Application.Exceptions.BookNotFoundException;
import com.BookStore.Application.Model.Book;
import com.BookStore.Application.Services.BookService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    
    private BookService bookService = new BookService();
    
    /**
     * GET all books
     * @return Response with list of all books
     */
    @GET
    public Response getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return Response.ok(books).build();
    }
    
    /**
     * GET a specific book by ID
     * @param id The ID of the book to retrieve
     * @return Response with the requested book
     */
    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        try {
            Book book = bookService.getBookById(id);
            return Response.ok(book).build();
        } catch (BookNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                   .entity("[MESSAGE] Book not found with ID: " + id)
                   .build();
        }
    }
    
    /**
     * CREATE a new book
     * @param book The book to create
     * @return Response with the created book
     */
    @POST
    public Response createBook(Book book) {
        try {
            Book createdBook = bookService.createBook(book);
            return Response.status(Response.Status.CREATED)
                   .entity(createdBook)
                   .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                   .entity(e.getMessage())
                   .build();
        }
    }
    
    /**
     * UPDATE an existing book
     * @param id The ID of the book to update
     * @param book The updated book information
     * @return Response with the updated book
     */
    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book book) {
        try {
            Book updatedBook = bookService.updateBook(id, book);
            return Response.ok(updatedBook).build();
        } catch (BookNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                   .entity("[MESSAGE] Book not found with ID: " + id)
                   .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                   .entity(e.getMessage())
                   .build();
        }
    }
    
    /**
     * DELETE a book by ID
     * @param id The ID of the book to delete
     * @return Response indicating success or failure
     */
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        try {
            bookService.deleteBook(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (BookNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                   .entity("[MESSAGE] Book not found with ID: " + id)
                   .build();
        }
    }
}
