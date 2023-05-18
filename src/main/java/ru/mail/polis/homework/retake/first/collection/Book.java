package ru.mail.polis.homework.retake.first.collection;

/**
 * Реализовать класс книги
 */
public class Book {

    private static int countBook = 0;

    private final int id;
    private final int year;
    private final String author;

    public Book(int year, String author){
        id = ++countBook;
        this.year = year;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }
}
