package ru.mail.polis.homework.retake.first.collection;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    private final List<Book> books = new ArrayList<>();

    /**
     * Добавляем книгу в библиотеку
     */
    public boolean addBook(Book book) {
        return books.add(book);
    }

    /**
     * Удаляем книгу из библиотеки
     */
    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    /**
     * Получаем список книг заданного автора
     */
    public List<Book> getBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    /**
     * Получаем список книг написанных в определенный год
     */
    public List<Book> getBooksByDate(int year) {
        return books.stream()
                .filter(book -> book.getYear() == year)
                .collect(Collectors.toList());
    }

    /**
     * Получаем книгу, которую последней добавили в библиотеку
     */
    public Book getLastBook() {
        return books.get(books.size() - 1);
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 3 или 4
     *
     * Возвращается итератор, который бегает по всем книгам, удовлетворяющим предикату
     */
    public Iterator<Book> iterator(Predicate<Book> predicate) {
        return null;
    }

    /**
     * Возвращается итератор, который бегает по всем книгам в порядке добавления
     */
    @Override
    public Iterator<Book> iterator() {
        return books.iterator();
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 5
     *
     * Возвращается итератор, который бегает по всем парам (индекс, книга), удовлетворяющим предикату
     */
    public Iterator<Book> iterator(BiPredicate<Integer, Book> predicate) {
        return books.stream()
                .map(book -> new HashMap.SimpleEntry<>(book.getId(), book))
                .filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
                .map(HashMap.SimpleEntry::getValue)
                .iterator();
    }
}
