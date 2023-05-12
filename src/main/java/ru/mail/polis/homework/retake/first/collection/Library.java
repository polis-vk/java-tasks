package ru.mail.polis.homework.retake.first.collection;

import java.util.*;
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
    private final Map<String, List<Book>> authorBookMap;
    private final Map<Integer, List<Book>> yearBookMap;
    private final List<Book> bookList;

    Library() {
        authorBookMap = new HashMap<>();
        yearBookMap = new HashMap<>();
        bookList = new ArrayList<>();
    }

    /**
     * Добавляем книгу в библиотеку
     */
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }
        String author = book.getAuthor();
        int year = book.getYear();
        if (!authorBookMap.containsKey(book.getAuthor())) {//o(1)
            authorBookMap.put(author, new ArrayList<>());//o(1)
        }
        if (!yearBookMap.containsKey(year)) {//o(1)
            yearBookMap.put(year, new ArrayList<>());//o(1)
        }
        authorBookMap.get(author).add(book);//o(1)
        yearBookMap.get(year).add(book);//o(1)
        return bookList.add(book);//o(1)
    }

    /**
     * Удаляем книгу из библиотеки
     */
    public boolean removeBook(Book book) {
        if (book == null) {
            return false;
        }
        authorBookMap.get(book.getAuthor()).remove(book);//o(книги автора)
        yearBookMap.get(book.getYear()).remove(book);//o(книги этого же года)
        return bookList.remove(book);//o(все книги) = o(все книги)
    }

    /**
     * Получаем список книг заданного автора
     */
    public List<Book> getBooksByAuthor(String author) {

        return authorBookMap.getOrDefault(author, new ArrayList<>());//o(1)
    }

    /**
     * Получаем список книг написанных в определенный год
     */
    public List<Book> getBooksByDate(int year) {

        return yearBookMap.getOrDefault(year, new ArrayList<>());//o(1)
    }

    /**
     * Получаем книгу, которую последней добавили в библиотеку
     */
    public Book getLastBook() {
        if (bookList.size() > 0) {//o(1)
            return bookList.get(bookList.size() - 1);//o(1)
        }
        return null;//o(1)
    }

    /**
     * ЗАДАНИЕ ТОЛЬКО ДЛЯ ТЕХ, КТО ХОЧЕТ ПОЛУЧИТЬ 3 или 4
     *
     * Возвращается итератор, который бегает по всем книгам, удовлетворяющим предикату
     */
    public Iterator<Book> iterator(Predicate<Book> predicate) {
        return bookList.stream()
                .filter(predicate)
                .iterator();//o(n)
    }

    /**
     *
     * Возвращается итератор, который бегает по всем книгам в порядке добавления
     */
    @Override
    public Iterator<Book> iterator() {
        return bookList.listIterator();//o(1)
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
