package ru.mail.polis.homework.streams.lib;

import java.util.List;

public class Library {
    private final List<User> users;
    private final List<Book> books;
    private final List<ArchivedData> archive;

    public Library(List<User> users, List<Book> books, List<ArchivedData> archive) {
        this.users = users;
        this.books = books;
        this.archive = archive;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<ArchivedData> getArchive() {
        return archive;
    }
}
