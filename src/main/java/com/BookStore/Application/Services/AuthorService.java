package com.BookStore.Application.Services;

import com.BookStore.Application.Exceptions.AuthorNotFoundException;
import com.BookStore.Application.Model.Author;
import com.BookStore.Application.Model.Book;
import com.BookStore.Application.Storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing Author operations such as create, retrieve, update, and delete.
 */
public class AuthorService {

    /**
     * Retrieve all authors from storage.
     * @return List of all authors
     */
    public List<Author> getAllAuthors() {
        return new ArrayList<>(Storage.getAuthors().values());
    }

    /**
     * Retrieve an author by their ID.
     * @param id Author ID
     * @return Author object if found
     * @throws AuthorNotFoundException if author does not exist
     */
    public Author getAuthorById(int id) {
        if (!Storage.getAuthors().containsKey(id)) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }
        return Storage.getAuthors().get(id);
    }

    /**
     * Retrieve all books written by a specific author.
     * @param authorId Author's ID
     * @return List of books associated with the author
     * @throws AuthorNotFoundException if author does not exist
     */
    public List<Book> getBooksByAuthorId(int authorId) {
        // Validate author existence
        if (!Storage.getAuthors().containsKey(authorId)) {
            throw new AuthorNotFoundException("Author not found with ID: " + authorId);
        }

        // Filter and return books by authorId
        return Storage.getBooks().values().stream()
                .filter(book -> book.getAuthorId() == authorId)
                .collect(Collectors.toList());
    }

    /**
     * Create a new author with a generated ID.
     * @param author Author object to be created
     * @return Created author with assigned ID
     */
    public Author createAuthor(Author author) {
        int newId = Storage.generateAuthorId();
        author.setId(newId);
        Storage.getAuthors().put(newId, author);
        return author;
    }

    /**
     * Update an existing author's details.
     * @param id ID of the author to update
     * @param updatedAuthor Updated author object
     * @return Updated author object
     * @throws AuthorNotFoundException if author does not exist
     */
    public Author updateAuthor(int id, Author updatedAuthor) {
        if (!Storage.getAuthors().containsKey(id)) {
            throw new AuthorNotFoundException("Cannot update author. Author not found with ID: " + id);
        }
        updatedAuthor.setId(id); // Preserve the ID
        Storage.getAuthors().put(id, updatedAuthor);
        return updatedAuthor;
    }

    /**
     * Delete an author by their ID.
     * @param id ID of the author to delete
     * @throws AuthorNotFoundException if author does not exist
     */
    public void deleteAuthor(int id) {
        if (!Storage.getAuthors().containsKey(id)) {
            throw new AuthorNotFoundException("Cannot delete author. Author not found with ID: " + id);
        }
        Storage.getAuthors().remove(id);
    }
}
