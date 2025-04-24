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
    
    /**
     * Gets the author's ID
     * 
     * @return The author's ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the author's ID
     * 
     * @param id The author's ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets the author's name
     * 
     * @return The author's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the author's name
     * 
     * @param name The author's name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the author's biography
     * 
     * @return The author's biography
     */
    public String getBiography() {
        return biography;
    }
    
    /**
     * Sets the author's biography
     * 
     * @param biography The author's biography to set
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }
}
