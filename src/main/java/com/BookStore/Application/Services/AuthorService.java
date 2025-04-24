package com.BookStore.Application.Services;

import com.BookStore.Application.Exceptions.AuthorNotFoundException;
import com.BookStore.Application.Model.Author;
import com.BookStore.Application.Storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {
    
    /**
     * Returns a list of all authors
     * @return List of all authors
     */
    public List<Author> getAllAuthors() {
        return new ArrayList<>(Storage.getAuthors().values());
    }
    
    /**
     * Returns an author by their ID
     * @param id Author ID
     * @return Author object
     * @throws AuthorNotFoundException if author with given ID does not exist
     */
    public Author getAuthorById(int id) {
        if (!Storage.getAuthors().containsKey(id)) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }
        return Storage.getAuthors().get(id);
    }
    
    /**
     * Creates a new author with auto-generated ID
     * @param author Author object to create
     * @return Created author with assigned ID
     */
    public Author createAuthor(Author author) {
        int newId = Storage. generateAuthorId();
        author.setId(newId);
        Storage.getAuthors().put(newId, author);
        return author;
    }
    
    /**
     * Updates an existing author
     * @param id Author ID to update
     * @param updatedAuthor Updated author details
     * @return Updated author object
     * @throws AuthorNotFoundException if author with given ID does not exist
     */
    public Author updateAuthor(int id, Author updatedAuthor) {
        if (!Storage.getAuthors().containsKey(id)) {
            throw new AuthorNotFoundException("Cannot update author. Author not found with ID: " + id);
        }
        updatedAuthor.setId(id);
        Storage.getAuthors().put(id, updatedAuthor);
        return updatedAuthor;
    }
    
    /**
     * Deletes an author by ID
     * @param id Author ID to delete
     * @throws AuthorNotFoundException if author with given ID does not exist
     */
    public void deleteAuthor(int id) {
        if (!Storage.getAuthors().containsKey(id)) {
            throw new AuthorNotFoundException("Cannot delete author. Author not found with ID: " + id);
        }
        Storage.getAuthors().remove(id);
    }
}
