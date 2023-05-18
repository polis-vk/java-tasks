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

        int addIndexByAuthor = binarySearchInPairList(booksSortedByAuthor, book.getAuthor());
        if (!checkForEqualityByIndex(booksSortedByAuthor, book.getAuthor(), addIndexByAuthor)) {
            booksSortedByAuthor.add(addIndexByAuthor, new Pair<>(book.getAuthor(), new ArrayList<>()));
        }
        booksSortedByAuthor.get(addIndexByAuthor).second.add(book);

        int addIndexByYear = binarySearchInPairList(booksSortedByYear, book.getYear());
        if (!checkForEqualityByIndex(booksSortedByYear, book.getYear(), addIndexByYear)) {
            booksSortedByYear.add(addIndexByYear, new Pair<>(book.getYear(), new ArrayList<>()));
        }
        booksSortedByYear.get(addIndexByYear).second.add(book);

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

        int indexOfAuthorsBooks = binarySearchInPairList(booksSortedByAuthor, book.getAuthor());
        booksSortedByAuthor.get(indexOfAuthorsBooks).second.remove(book);
        if (booksSortedByAuthor.get(indexOfAuthorsBooks).second.isEmpty()) {
            booksSortedByAuthor.remove(indexOfAuthorsBooks);
        }

        int indexOfYearsBooks = binarySearchInPairList(booksSortedByYear, book.getYear());
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
        int indexOfAuthor = binarySearchInPairList(booksSortedByAuthor, author);
        if (!checkForEqualityByIndex(booksSortedByAuthor, author, indexOfAuthor)) {
            return new ArrayList<>();
        }
        return booksSortedByAuthor.get(indexOfAuthor).second;
    }

    /**
     * Получаем список книг написанных в определенный год
     */
    public List<Book> getBooksByDate(int year) {
        int indexOfYear = binarySearchInPairList(booksSortedByYear, year);
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
     * <p>
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
     * <p>
     * Возвращается итератор, который бегает по всем парам (индекс, книга), удовлетворяющим предикату
     */
    public Iterator<Book> iterator(BiPredicate<Integer, Book> predicate) {
        return IntStream.range(0, books.size())
                .filter(index -> predicate.test(index, books.get(index)))
                .mapToObj(books::get)
                .iterator();
    }

    private <T extends Comparable<T>> int binarySearchInPairList(List<Pair<T, List<Book>>> list, T element) {
        if (list.isEmpty()) {
            return 0;
        }

        int leftIndex = 0;
        int rightIndex = list.size() - 1;
        int middleIndex;

        while (leftIndex <= rightIndex) {
            middleIndex = (leftIndex + rightIndex) / 2;

            if (element.equals(list.get(middleIndex).first)) {
                return middleIndex;
            }
            if (element.compareTo(list.get(middleIndex).first) < 0) {
                rightIndex = middleIndex - 1;
            } else {
                leftIndex = middleIndex + 1;
            }
        }

        return leftIndex;
    }

    private <T> boolean checkForEqualityByIndex(List<Pair<T, List<Book>>> list, T key, int index) {
        if (list.isEmpty()) {
            return false;
        }
        if (index > list.size() - 1) {
            return false;
        }
        return list.get(index).first.equals(key);
    }
}
