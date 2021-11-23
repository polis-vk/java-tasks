package ru.mail.polis.homework.streams.lib;

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
}
