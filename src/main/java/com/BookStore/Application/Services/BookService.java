package com.BookStore.Application.Services;

import com.BookStore.Application.Model.Book;
import com.BookStore.Application.Storage.Storage;
import com.BookStore.Application.Exceptions.BookNotFoundException;
import java.util.List;

public class BookService {
    
    /**
     * Get all books from the storage
     */
    public List<Book> getAllBooks() {
        return (List<Book>) Storage.getBooks().values();
    }
    
    /**
     * Get a book by its ID
     * @param id The ID of the book to find
     * @return The found book
     * @throws BookNotFoundException If no book with the given ID exists
     */
    public Book getBookById(int id) {
        return Storage.getBooks().values().stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }
    
    /**
     * Create a new book after validating the author exists
     * @param book The book to create
     * @return The created book with generated ID
     */
    public Book createBook(Book book) {
        // Validate author exists
        boolean authorExists = Storage.getAuthors().values().stream()
                .anyMatch(author -> author.getId() == book.getAuthorId());
        
        if (!authorExists) {
            throw new IllegalArgumentException("Author with ID " + book.getAuthorId() + " does not exist");
        }
        
        // Generate new ID (max ID + 1 or 1 if empty)
        int  newId = Storage.getBooks().values().stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0) + 1;
        
        book.setId(newId);
       Storage.getBooks().put(newId, book);
        return book;
    }
    
    /**
     * Update an existing book
     * @param id The ID of the book to update
     * @param updatedBook The updated book information
     * @return The updated book
     * @throws BookNotFoundException If no book with the given ID exists
     */
    public Book updateBook(int id, Book updatedBook) {
        Book existingBook = getBookById(id); // Throws if not found
        
        // Validate author exists if being changed
        if (updatedBook.getAuthorId() != existingBook.getAuthorId()) {
            // Check if the new author ID exists
            boolean authorExists = Storage.getAuthors().values().stream()
                    .anyMatch(author -> author.getId() == updatedBook.getAuthorId());

            
            if (!authorExists) {
                throw new IllegalArgumentException("Author with ID " + updatedBook.getAuthorId() + " does not exist");
            }
        }
        
        // Preserve the ID
        updatedBook.setId(id);
        
        // Replace in storage
        Book book = Storage.getBooks().get(id); //I dont know if this correct
        Storage.getBooks().put(id, updatedBook);

        return updatedBook;
    }
    
    /**
     * Delete a book by ID
     * @param id The ID of the book to delete
     * @throws BookNotFoundException If no book with the given ID exists
     */
    public void deleteBook(int id) {
        Book book = getBookById(id); // Throws if not found
        Storage.getBooks().remove(book);
    }
}
