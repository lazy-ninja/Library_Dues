package com.niraj.apps.materialdemo;

/**
 * Created by niraj on 10/04/2015.
 */
public class Book {
    private String name;
    private String author;
    private int daysRemaining;
    private String id;
    private String issueDate;
    private String returningDate;


    public  Book() {
    }

    public Book(String name, String id, String issueDate, String returningDate) {
        this.name = name;
        this.id = id;
        this.issueDate = issueDate;
        this.returningDate = returningDate;
        this.daysRemaining = 10;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(int daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturningDate() {
        return returningDate;
    }

    public void setReturningDate(String returningDate) {
        this.returningDate = returningDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

