package com.BookStore.Application.Model;

/**
 * Author class represents an author entity in the BookStore application.
 * This class is used for JSON serialization/deserialization in the Bookstore REST API.
 */
public class Author {
    // Private fields for encapsulation
    private int id;
    private String name;
    private String biography;
    

    //Default constructor required for JSON deserialization

    public Author() {
    }
    
    /**
     * Parameterized constructor to create an Author with all fields
     * 
     * @param id The unique identifier for the author
     * @param name The full name of the author
     * @param biography A short description about the author
     */
    public Author(int id, String name, String biography) {
        this.id = id;
        this.name = name;
        this.biography = biography;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
