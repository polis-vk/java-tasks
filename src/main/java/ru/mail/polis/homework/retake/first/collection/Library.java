package ru.mail.polis.homework.retake.first.collection;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
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
    private ArrayList<Book> storage_;
    /**
     * Добавляем книгу в библиотеку
     */
    public boolean addBook(Book book) {
        if (!book.isEmpty()) {
            return false;
        }
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
        if (!storage_.isEmpty()) {
            return storage_.get(storage_.size() - 1);
        }
        return new Book();
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 3 или 4
     *
     * Возвращается итератор, который бегает по всем книгам, удовлетворяющим предикату
     */
    public Iterator<Book> iterator(Predicate<Book> predicate) {
        return storage_.stream()
                .filter(predicate)
                .iterator();
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
                .map(book -> new AbstractMap.SimpleEntry<>(storage_.indexOf(book), book))
                .filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
                .map(AbstractMap.SimpleEntry::getValue)
                .iterator();
    }
}
