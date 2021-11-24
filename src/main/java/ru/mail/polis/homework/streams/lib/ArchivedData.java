package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;

public class ArchivedData {
    private final User user;
    private final Book book;
    private final Timestamp take;
    private final Timestamp returned; //может быть null для книг которые сейчас на руках

    public ArchivedData(User user, Book book, Timestamp take, Timestamp returned) {
        this.user = user;
        this.book = book;
        this.take = take;
        this.returned = returned;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public Timestamp getTake() {
        return take;
    }

    public Timestamp getReturned() {
        return returned;
    }
}
