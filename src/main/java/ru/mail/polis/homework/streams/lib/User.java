package ru.mail.polis.homework.streams.lib;

import java.util.Objects;

public class User {
    private final String name;
    private final int age;
    private final Book book;
    private final int readPages;

    public User(String name, int age, Book book, int readPages) {
        this.name = name;
        this.age = age;
        this.book = book;
        this.readPages = readPages;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Book getBook() {
        return book;
    }

    public int getReadPages() {
        return readPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, book, readPages);
    }
}
