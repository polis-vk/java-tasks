package ru.mail.polis.homework.retake.first.collection;

/**
 * Реализовать класс книги
 */
public class Book {
    private final String title;
    private final String author;
    private final int year;

    Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
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
}
