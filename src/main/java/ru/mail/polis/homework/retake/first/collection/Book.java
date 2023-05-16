package ru.mail.polis.homework.retake.first.collection;

import java.util.Objects;

/**
 * Реализовать класс книги
 */
public class Book {
    private final String author;
    private final int year;
    private final String name;

    public Book(String author, int year, String name) {
        this.author = author;
        this.year = year;
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && author.equals(book.author) && name.equals(book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, year, name);
    }
}
