package ru.mail.polis.homework.retake.first.collection;

/**
 * Реализовать класс книги
 */
public class Book {
    private String title;
    private String author;
    private int year;
    private int pages;

    public Book() {
        this.title = "";
        this.author = "";
        this.pages = 0;
        this.year = 0;
    }
    public Book(String title, String author, int pages, int year) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getYear() {
        return year;
    }
    public int getPages() {
        return pages;
    }
    public boolean isEmpty() {
        return this.title.equals("") && this.author.equals("") && this.pages == 0 && this.year == 0;
    }
    public String printBook() {
        return "Title: " + title + ", Author: " + author + ", Pages: " + pages;
    }

}
