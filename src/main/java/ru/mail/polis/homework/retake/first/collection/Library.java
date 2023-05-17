package ru.mail.polis.homework.retake.first.collection;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
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
    private final List<Book> books;
    private final List<Pair<String, List<Book>>> booksSortedByAuthor;
    private final List<Pair<Integer, List<Book>>> booksSortedByYear;

    public Library() {
        books = new ArrayList<>();
        booksSortedByAuthor = new ArrayList<>();
        booksSortedByYear = new ArrayList<>();
    }

    /**
     * Добавляем книгу в библиотеку
     */
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }

        books.add(book);

        int addIndexByAuthor = binSearchAuthor(book.getAuthor());
        if (!checkForEqualityByIndex(booksSortedByAuthor, book.getAuthor(), addIndexByAuthor)) {
            booksSortedByAuthor.add(addIndexByAuthor, new Pair<>(book.getAuthor(), new ArrayList<>()));
            booksSortedByAuthor.get(addIndexByAuthor).second.add(book);
        } else {
            booksSortedByAuthor.get(addIndexByAuthor).second.add(book);
        }

        int addIndexByYear = binSearchYear(book.getYear());
        if (!checkForEqualityByIndex(booksSortedByYear, book.getYear(), addIndexByYear)) {
            booksSortedByYear.add(addIndexByYear, new Pair<>(book.getYear(), new ArrayList<>()));
            booksSortedByYear.get(addIndexByYear).second.add(book);
        } else {
            booksSortedByYear.get(addIndexByYear).second.add(book);
        }
        return true;
    }

    /**
     * Удаляем книгу из библиотеки
     */
    public boolean removeBook(Book book) {
        if (book == null) {
            return false;
        }
        if (!books.remove(book)) {
            return false;
        }

        int indexOfAuthorsBooks = binSearchAuthor(book.getAuthor());
        booksSortedByAuthor.get(indexOfAuthorsBooks).second.remove(book);
        if (booksSortedByAuthor.get(indexOfAuthorsBooks).second.isEmpty()) {
            booksSortedByAuthor.remove(indexOfAuthorsBooks);
        }

        int indexOfYearsBooks = binSearchYear(book.getYear());
        booksSortedByYear.get(indexOfYearsBooks).second.remove(book);
        if (booksSortedByYear.get(indexOfYearsBooks).second.isEmpty()) {
            booksSortedByYear.remove(indexOfYearsBooks);
        }
        return true;
    }

    /**
     * Получаем список книг заданного автора
     */
    public List<Book> getBooksByAuthor(String author) {
        int indexOfAuthor = binSearchAuthor(author);
        if (!checkForEqualityByIndex(booksSortedByAuthor, author, indexOfAuthor)) {
            return new ArrayList<>();
        }
        return booksSortedByAuthor.get(indexOfAuthor).second;
    }

    /**
     * Получаем список книг написанных в определенный год
     */
    public List<Book> getBooksByDate(int year) {
        int indexOfYear = binSearchYear(year);
        if (!checkForEqualityByIndex(booksSortedByYear, year, indexOfYear)) {
            return new ArrayList<>();
        }
        return booksSortedByAuthor.get(indexOfYear).second;
    }

    /**
     * Получаем книгу, которую последней добавили в библиотеку
     */
    public Book getLastBook() {
        if (books.isEmpty()) {
            return null;
        }
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
        return IntStream.range(0, books.size())
                .boxed()
                .filter(index -> predicate.test(index, books.get(index)))
                .map(books::get)
                .iterator();
    }

    private int binSearchYear(int element) {
        if (booksSortedByYear.isEmpty()) {
            return 0;
        }

        int leftIndex = 0;
        int rightIndex = booksSortedByYear.size() - 1;
        int middleIndex = 0;

        while (leftIndex <= rightIndex) {
            middleIndex = (leftIndex + rightIndex) / 2;

            if (element == booksSortedByYear.get(middleIndex).first)
                return middleIndex;
            else if (element < booksSortedByYear.get(middleIndex).first)
                rightIndex = middleIndex - 1;
            else
                leftIndex = middleIndex + 1;
        }

        if (element > booksSortedByYear.get(middleIndex).first) {
            ++middleIndex;
        }
        return middleIndex;
    }

    private int binSearchAuthor(String element) {
        if (booksSortedByAuthor.isEmpty()) {
            return 0;
        }

        int leftIndex = 0;
        int rightIndex = booksSortedByAuthor.size() - 1;
        int middleIndex = 0;

        while (leftIndex <= rightIndex) {
            middleIndex = (leftIndex + rightIndex) / 2;

            if (element.equals(booksSortedByAuthor.get(middleIndex).first))
                return middleIndex;
            else if (element.compareTo(booksSortedByAuthor.get(middleIndex).first) < 0)
                rightIndex = middleIndex - 1;
            else
                leftIndex = middleIndex + 1;
        }

        if (element.compareTo(booksSortedByAuthor.get(middleIndex).first) > 0) {
            ++middleIndex;
        }
        return middleIndex;
    }

    private <K> boolean checkForEqualityByIndex(List<Pair<K, List<Book>>> list, K key, int index) {
        if (list.isEmpty()) {
            return false;
        }
        if (index > list.size() - 1) {
            return false;
        }
        return list.get(index).first.equals(key);
    }
}
