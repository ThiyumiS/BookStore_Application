package com.BookStore.Application.Resources;

import com.BookStore.Application.Exceptions.AuthorNotFoundException;
import com.BookStore.Application.Model.Author;
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
    
    /**
     * Get all authors
     * @return List of all authors
     */
    @GET
    public Response getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return Response.ok(authors).build();
    }
    
    /**
     * Get author by ID
     * @param id Author ID
     * @return Author with the specified ID
     */
    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        try {
            Author author = authorService.getAuthorById(id);
            return Response.ok(author).build();
        } catch (AuthorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Author not found with ID: " + id)
                    .build();
        }
    }
    
    /**
     * Create a new author
     * @param author Author object from request body
     * @return Created author with generated ID
     */
    @POST
    public Response createAuthor(Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return Response.status(Response.Status.CREATED)
                .entity(createdAuthor)
                .build();
    }
    
    /**
     * Update an existing author
     * @param id Author ID to update
     * @param author Updated author details
     * @return Updated author object
     */
    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author author) {
        try {
            Author updatedAuthor = authorService.updateAuthor(id, author);
            return Response.ok(updatedAuthor).build();
        } catch (AuthorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
    
    /**
     * Delete an author by ID
     * @param id Author ID to delete
     * @return Response indicating success or failure
     */
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        try {
            authorService.deleteAuthor(id);
            return Response.noContent().build();
        } catch (AuthorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
