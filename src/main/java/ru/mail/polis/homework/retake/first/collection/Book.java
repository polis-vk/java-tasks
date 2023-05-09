package ru.mail.polis.homework.retake.first.collection;

/**
 * Реализовать класс книги
 */
public class Book {
    private final String author;
    private final Integer addingYear;

    Book(String author, Integer addingYear) {
        this.author = author;
        this.addingYear = addingYear;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getAddingYear() {
        return addingYear;
    }
}
