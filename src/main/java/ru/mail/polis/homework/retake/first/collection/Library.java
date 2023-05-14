package ru.mail.polis.homework.retake.first.collection;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Реализовать класс библиотеки, которая в себе хранит книги.
 * Книги в библиотеку можно добавлять и удалять из нее.
 * <p>
 * <p>
 * Для тех, кто хочет получить 3
 * Упор делается на время работы программы. Память не важна
 * <p>
 * Для тех, кто хочет получить 4
 * Упор делается на память, время работы не важно
 * <p>
 * Для тех, кто хочет получить 5
 * Надо найти баланс, память важнее, но за временем тоже надо следить
 */
public class Library implements Iterable<Book> {
    private final List<Book> bookList = new ArrayList<>();

    /**
     * Добавляем книгу в библиотеку
     */
    public boolean addBook(Book book) {
        return bookList.add(book);
    }

    /**
     * Удаляем книгу из библиотеки
     */
    public boolean removeBook(Book book) {
        return bookList.remove(book);
    }

    /**
     * Получаем список книг заданного автора
     */
    public List<Book> getBooksByAuthor(String author) {
        return bookList.stream()
                .filter(book -> Objects.equals(book.getAuthor(), author))
                .collect(Collectors.toList());
    }

    /**
     * Получаем список книг написанных в определенный год
     */
    public List<Book> getBooksByDate(int year) {
        return bookList.stream()
                .filter(book -> book.getYearOfPublication() == year)
                .collect(Collectors.toList());
    }

    /**
     * Получаем книгу, которую последней добавили в библиотеку
     */
    public Book getLastBook() {
        if (bookList.size() > 0) {
            return bookList.get(bookList.size() - 1);
        }
        return null;
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 3 или 4
     * <p>
     * Возвращается итератор, который бегает по всем книгам, удовлетворяющим предикату
     */
    public Iterator<Book> iterator(Predicate<Book> predicate) {
        return bookList.stream()
                .filter(predicate)
                .iterator();
    }

    /**
     * Возвращается итератор, который бегает по всем книгам в порядке добавления
     */
    @Override
    public Iterator<Book> iterator() {
        return bookList.stream().iterator();
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 5
     * <p>
     * Возвращается итератор, который бегает по всем парам (индекс, книга), удовлетворяющим предикату
     */
    public Iterator<Book> iterator(BiPredicate<Integer, Book> predicate) {
        return null;
    }
}
