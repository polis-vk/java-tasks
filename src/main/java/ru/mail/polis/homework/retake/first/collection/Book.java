package ru.mail.polis.homework.retake.first.collection;

/**
 * Реализовать класс книги
 */
public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final int yearOfPublication;
    private final String genre;
    private final String publishingHouse;

    public Book(int id, String title, String author, int yearOfPublication, String genre, String publishingHouse) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.genre = genre;
        this.publishingHouse = publishingHouse;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public String getGenre() {
        return genre;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public String getTitle() {
        return title;
    }
}
