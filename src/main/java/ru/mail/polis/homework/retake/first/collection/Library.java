package ru.mail.polis.homework.retake.first.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Реализовать класс библиотеки, которая в себе хранит книги.
 * Книги в библиотеку можно добавлять и удалять из нее.
 *
 *
 * Для тех, кто хочет получить 3
 * Упор делается на время работы программы. Память не важна
 *
 * Для тех, кто хочет получить 4
 * Упор делается на память, время работы не важно
 *
 * Для тех, кто хочет получить 5
 * Надо найти баланс, память важнее, но за временем тоже надо следить
 */
public class Library implements Iterable<Book> {

    private final LinkedHashMap<String, Book> books;
    private final HashMap<String, List<Book>> booksByAuthor;
    private final HashMap<Integer, List<Book>> booksByYear;

    public Library() {
        books = new LinkedHashMap<>();
        booksByAuthor = new HashMap<>();
        booksByYear = new HashMap<>();
    }

    /**
     * Добавляем книгу в библиотеку
     */
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }
        books.put(book.getName(), book);

        booksByAuthor.computeIfAbsent(book.getAuthor(), k -> new ArrayList<>()).add(book);
        booksByYear.computeIfAbsent(book.getYear(), k -> new ArrayList<>()).add(book);
        return true;
    }

    /**
     * Удаляем книгу из библиотеки
     */
    public boolean removeBook(Book book) {
        if (book == null || !books.containsKey(book.getName())) {
            return false;
        }

        books.remove(book.getName());

        List<Book> authorBooks = booksByAuthor.get(book.getAuthor());
        authorBooks.removeIf(booksByAuthor -> booksByAuthor.equals(book));
        if (authorBooks.isEmpty()) {
            booksByAuthor.remove(book.getAuthor());
        }

        List<Book> yearBooks = booksByYear.get(book.getYear());
        yearBooks.removeIf(bookByYear -> bookByYear.equals(book));
        if (yearBooks.isEmpty()) {
            booksByYear.remove(book.getYear());
        }
        return true;
    }

    /**
     * Получаем список книг заданного автора
     */
    public List<Book> getBooksByAuthor(String author) {
        return booksByAuthor.getOrDefault(author, new ArrayList<>());
    }

    /**
     * Получаем список книг написанных в определенный год
     */
    public List<Book> getBooksByDate(int year) {
        return booksByYear.getOrDefault(year, new ArrayList<>());
    }

    /**
     * Получаем книгу, которую последней добавили в библиотеку
     */
    public Book getLastBook() {
        String lastKey = (String) books.keySet().toArray()[books.size() - 1];
        return books.get(lastKey);
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 3 или 4
     *
     * Возвращается итератор, который бегает по всем книгам, удовлетворяющим предикату
     */
    public Iterator<Book> iterator(Predicate<Book> predicate) {
        return books.values().stream()
                .filter(predicate)
                .collect(Collectors.toList())
                .iterator();
    }

    /**
     *
     * Возвращается итератор, который бегает по всем книгам в порядке добавления
     */
    @Override
    public Iterator<Book> iterator() {
        return books.values().iterator();
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 5
     *
     * Возвращается итератор, который бегает по всем парам (индекс, книга), удовлетворяющим предикату
     */
    public Iterator<Book> iterator(BiPredicate<Integer, Book> predicate) {
        Book[] booksArray = books.values().toArray(new Book[0]);
        List<Book> filteredBooks = IntStream.range(0, books.size())
                .boxed()
                .filter(index -> predicate.test(index, booksArray[index]))
                .map(index -> booksArray[index])
                .collect(Collectors.toList());
        return filteredBooks.iterator();
    }

}
