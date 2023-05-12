package ru.mail.polis.homework.retake.first.collection;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.Vector;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Реализовать класс библиотеки, которая в себе хранит книги.
 * Книги в библиотеку можно добавлять и удалять из нее.
 *</p>
 *
 * Для тех, кто хочет получить 3
 * Упор делается на время работы программы. Память не важна
 *</p>
 * Для тех, кто хочет получить 4
 * Упор делается на память, время работы не важно
 *
 * Для тех, кто хочет получить 5
 * Надо найти баланс, память важнее, но за временем тоже надо следить
 */
public class Library implements Iterable<Book> {
    private Vector<Book> storage_;
    /**
     * Добавляем книгу в библиотеку
     */
    public boolean addBook(Book book) {
        return storage_.add(book);
    }

    /**
     * Удаляем книгу из библиотеки
     */
    public boolean removeBook(Book book) {
        return storage_.remove(book);
    }

    /**
     * Получаем список книг заданного автора
     */
    public List<Book> getBooksByAuthor(String author) {
        return storage_.stream()
                .filter(book -> Objects.equals(book.getAuthor(), author))
                .collect(Collectors.toList());
    }

    /**
     * Получаем список книг написанных в определенный год
     */
    public List<Book> getBooksByDate(int year) {
        return storage_.stream()
                .filter(book -> Objects.equals(book.getYear(), year))
                .collect(Collectors.toList());
    }

    /**
     * Получаем книгу, которую последней добавили в библиотеку
     */
    public Book getLastBook() {
        return storage_.lastElement();
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 3 или 4
     *
     * Возвращается итератор, который бегает по всем книгам, удовлетворяющим предикату
     */
    public Iterator<Book> iterator(Predicate<Book> predicate) {
        Vector<Book> filteredBooks = new Vector<>();
        for (Book book : storage_) {
            if (predicate.test(book)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks.iterator();
    }

    /**
     *
     * Возвращается итератор, который бегает по всем книгам в порядке добавления
     */
    @Override
    public Iterator<Book> iterator() {
        return storage_.iterator();
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 5
     *
     * Возвращается итератор, который бегает по всем парам (индекс, книга), удовлетворяющим предикату
     */
    public Iterator<Book> iterator(BiPredicate<Integer, Book> predicate) {
        return storage_.stream()
                .filter(book -> predicate.test(storage_.indexOf(book), book))
                .iterator();
    }
}
