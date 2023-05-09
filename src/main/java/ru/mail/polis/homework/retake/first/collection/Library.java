package ru.mail.polis.homework.retake.first.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

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
    ArrayList<Book> lib;
    /**
     * Добавляем книгу в библиотеку
     */
    public boolean addBook(Book book) {
        return lib.add(book);
    }

    /**
     * Удаляем книгу из библиотеки
     */
    public boolean removeBook(Book book) {
        return lib.remove(book);
    }

    /**
     * Получаем список книг заданного автора
     */
    public List<Book> getBooksByAuthor(String author) {
        ArrayList<Book> required = new ArrayList<>();
        for (Book book : lib) {
            if (book.getAuthor() == author) {
                required.add(book);
            }
        }
        return required;
    }

    /**
     * Получаем список книг написанных в определенный год
     */
    public List<Book> getBooksByDate(int year) {
         ArrayList<Book> required = new ArrayList<>();
         for (Book book : lib) {
             if (book.getAddingYear() == year) {
                 required.add(book);
             }
         }
         return required;
    }

    /**
     * Получаем книгу, которую последней добавили в библиотеку
     */
    public Book getLastBook() {
        return lib.get(lib.size() - 1);
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 3 или 4
     *
     * Возвращается итератор, который бегает по всем книгам, удовлетворяющим предикату
     */
    public Iterator<Book> iterator(Predicate<Book> predicate) {
        return lib.stream().filter(predicate).iterator();
    }

    /**
     *
     * Возвращается итератор, который бегает по всем книгам в порядке добавления
     */
    @Override
    public Iterator<Book> iterator() {
        return lib.iterator();
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 5
     *
     * Возвращается итератор, который бегает по всем парам (индекс, книга), удовлетворяющим предикату
     */
    public Iterator<Book> iterator(BiPredicate<Integer, Book> predicate) {
        return null;
    }
}