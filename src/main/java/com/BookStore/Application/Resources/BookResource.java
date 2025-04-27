package com.BookStore.Application.Resources;

import com.BookStore.Application.Exceptions.InvalidInputException;
import com.BookStore.Application.Model.Book;
import com.BookStore.Application.Services.BookService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private BookService bookService = new BookService();

    @GET
    public Response getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return Response.ok(books).build();
    }    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        // BookService.getBookById will throw BookNotFoundException if not found
        Book book = bookService.getBookById(id);
        return Response.ok(book).build();
    }    @POST
    public Response createBook(Book book) {
        // Validate required fields
        if (book.getTitle() == null || book.getTitle().trim().isEmpty() || 
            book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new InvalidInputException("Book title and ISBN are required");
        }
        
        // The service throws AuthorNotFoundException for invalid author ID
        Book createdBook = bookService.createBook(book);
        return Response.status(Response.Status.CREATED)
                .entity(createdBook)
                .build();
    }    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book book) {
        // Validate required fields
        if (book.getTitle() == null || book.getTitle().trim().isEmpty() || 
            book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new InvalidInputException("Book title and ISBN are required");
        }
        
        // BookService.updateBook throws BookNotFoundException if book not found
        // and AuthorNotFoundException if author ID is invalid
        Book updatedBook = bookService.updateBook(id, book);
        return Response.ok(updatedBook).build();
    }    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        // BookService.deleteBook throws BookNotFoundException if book not found
        bookService.deleteBook(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
