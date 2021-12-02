package ru.mail.polis.homework.streams.lib;

import java.util.Objects;

public class Book {
    private final String name;
    private final String author;
    private final Genre genre;
    private final int page;

    public Book(String name, String author, Genre genre, int page) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getPage() {
        return page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return page == book.page && name.equals(book.name) && author.equals(book.author) && genre == book.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, genre, page);
    }
}
