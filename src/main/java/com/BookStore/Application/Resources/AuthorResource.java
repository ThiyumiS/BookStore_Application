package com.BookStore.Application.Resources;

import com.BookStore.Application.Model.Author;
import com.BookStore.Application.Model.Book;
import com.BookStore.Application.Services.AuthorService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private final AuthorService authorService;

    public AuthorResource() {
        this.authorService = new AuthorService();
    }

    @GET
    public Response getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return Response.ok(authors).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Author author = authorService.getAuthorById(id);
        return Response.ok(author).build();
    }

    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthorId(@PathParam("id") int id) {
        List<Book> books = authorService.getBooksByAuthorId(id);
        return Response.ok(books).build();
    }

    @POST
    public Response createAuthor(Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return Response.status(Response.Status.CREATED)
                .entity(createdAuthor)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        authorService.deleteAuthor(id);
        return Response.noContent().build();
    }
}
