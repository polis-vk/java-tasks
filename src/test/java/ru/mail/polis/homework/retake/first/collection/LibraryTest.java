package ru.mail.polis.homework.retake.first.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LibraryTest {
    Library library;

    @Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void addTest() {
        Book book = new Book("Война и Мир", "Толстой", 1873);
        library.addBook(book);
        Assert.assertEquals(book, library.getBooksByDate(1873).get(0));
        Assert.assertEquals(book, library.getBooksByAuthor("Толстой").get(0));
        Assert.assertEquals(book, library.getLastBook());
    }

    @Test
    public void nullTest() {
        Book book = null;
        Assert.assertFalse(library.addBook(book));
        Assert.assertFalse(library.removeBook(book));
    }

    @Test
    public void testGetByAuthor() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("Преступление и наказание", "Достоевский", 1893));
        bookList.add(new Book("Подросток", "Достоевский", 1993));
        bookList.add(new Book("Игрок", "Достоевский", 1993));
        for (Book book : bookList) {
            library.addBook(book);
        }
        Book book = new Book("Война и Мир", "Толстой", 1873);
        library.addBook(book);
        Assert.assertEquals(bookList, library.getBooksByAuthor("Достоевский"));
    }

    @Test
    public void testGetByDate() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("Преступление и наказание", "Достоевский", 1993));
        bookList.add(new Book("Подросток", "Достоевский", 1993));
        bookList.add(new Book("Игрок", "Достоевский", 1993));
        for (Book book : bookList) {
            library.addBook(book);
        }
        Book book = new Book("Война и Мир", "Толстой", 1873);
        library.addBook(book);
        Assert.assertEquals(bookList, library.getBooksByDate(1993));
    }
}
