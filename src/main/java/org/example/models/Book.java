package org.example.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;

    @NotEmpty(message = "Book title should not be empty")
    @Size(min = 2, max = 50, message = "Book itle has incorrect size!")
    private String title;

    @NotEmpty(message = "Author name should not be empty")
    @Size(min = 2, max = 50, message = "Author name has incorrect size!")
    private String author;

    @Min(value = 1, message = "Book year should be greater than 1")
    private int year;
    private int personId;

    public Book() {
    }

    public Book(int id, String title, String author, int year, int personId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.personId = personId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
