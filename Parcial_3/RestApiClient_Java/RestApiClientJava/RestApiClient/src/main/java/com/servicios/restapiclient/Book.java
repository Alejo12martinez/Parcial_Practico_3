
package com.servicios.restapiclient;

/**
 *
 * @author alejo
 */
public class Book {

    private String id;
    private String title;
    private String description;
    private String author;

    public Book(String id, String title, String description, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    // Otros métodos y getters y setters
    @Override
    public String toString() {
        return "Book{"
                + "id='" + id + '\''
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", author='" + author + '\''
                + '}';
    }
}
