package com.BookStore.Application.Model;

/**
 * Represents a book entity in the BookStore application.
 * This class stores all relevant information about books available in the store,
 * including identification, publication details, pricing, and inventory information.
 */
public class Book {
    // Unique identifier for the book in the system
    private int id;
    
    // Title of the book
    private String title;
    
    // Author(s) of the book
    private String author;
    
    // International Standard Book Number - unique identifier for books
    private String isbn;
    
    // Price of the book in the store (in local currency)
    private double price;
    
    // Genre or category the book belongs to (e.g., Fiction, Science, History)
    private String category;
    
    // Date when the book was published
    private String publicationDate;
    
    // Publishing company of the book
    private String publisher;
    
    // Brief summary or description of the book's content
    private String description;
    
    // Number of copies available in stock
    private int quantityInStock;

    // ID of the author associated with this book
    private int authorId;

    /**
     * Default constructor for the Book class.
     */
    public Book() {
    }



    /**
     * Full parameterized constructor for creating a book with all available details.
     * 
     * @param id Unique identifier for the book
     * @param title Title of the book
     * @param author Author(s) of the book
     * @param isbn ISBN of the book
     * @param price Price of the book

     * @param publicationDate Publication date of the book
     * @param quantityInStock Quantity available in stock
     */

    public Book(int id, String title, String author, String isbn, double price,
                String publicationDate, int quantityInStock, int authorId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.isbn = isbn;
        this.price = price;
        this.publicationDate = publicationDate;
        this.quantityInStock = quantityInStock;
    }

    // Getters and Setters
    
    /**
     * Returns the unique identifier of the book.
     * 
     * @return The book's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the book.
     * 
     * @param id The book ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the title of the book.
     * 
     * @return The book's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * 
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the author(s) of the book.
     * 
     * @return The book's author(s)
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author(s) of the book.
     * 
     * @param author The author(s) to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the ISBN of the book.
     * 
     * @return The book's ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * 
     * @param isbn The ISBN to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the price of the book.
     * 
     * @return The book's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the book.
     * 
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }



    /**
     * Returns the current quantity of this book in stock.
     * 
     * @return The quantity in stock
     */
    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * Sets the quantity of this book in stock.
     * 
     * @param quantityInStock The quantity to set
     */
    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    /**
     * Checks if the book is available in stock.
     * 
     * @return true if at least one copy is available, false otherwise
     */
    public boolean isInStock() {
        return quantityInStock > 0;
    }

    /**
     * Returns a string representation of the book.
     * 
     * @return A string containing the title, author, and price of the book
     */
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }

    public int getAuthorId() {
        return authorId;
    }
}
