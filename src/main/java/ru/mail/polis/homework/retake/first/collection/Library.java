package ru.mail.polis.homework.retake.first.collection;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
    private final ArrayList<Book> books;
    private final ArrayList<Pair<String, ArrayList<Book>>> authorsBooks;
    private final ArrayList<Pair<Integer, ArrayList<Book>>> yearsBooks;

    //Для оценки сложности
    //k = authorsBooksIndex.size() либо yearsBooksIndex.size()
    //n = books.size()
    //m = authorsBooksIndex.getSecond().size() либо yearsBooksIndex.getSecond().size()

    public Library() {
        books = new ArrayList<>();
        authorsBooks = new ArrayList<>();
        yearsBooks = new ArrayList<>();
    }

    /**
     * Добавляем книгу в библиотеку
     */
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }

        books.add(book);

        int authorsBooksIndex = binarySearch(authorsBooks, book.getAuthor());
        if (authorsBooksIndex != -1) {
            authorsBooks.add(authorsBooksIndex, new Pair<>(book.getAuthor(), new ArrayList<>()));
        }
        authorsBooks.get(authorsBooksIndex).getSecond().add(book);

        int yearsBooksIndex = binarySearch(yearsBooks, book.getYear());
        if (yearsBooksIndex != -1) {
            yearsBooks.add(yearsBooksIndex, new Pair<>(book.getYear(), new ArrayList<>()));
        }
        yearsBooks.get(yearsBooksIndex).getSecond().add(book);

        return true; //O(2 log k)
    }

    /**
     * Удаляем книгу из библиотеки
     */
    public boolean removeBook(Book book) {
        if (book == null || !books.remove(book)) {
            return false;
        }

        int authorsBooksIndex = binarySearch(authorsBooks, book.getAuthor());
        authorsBooks.get(authorsBooksIndex).getSecond().remove(book);
        if (authorsBooks.get(authorsBooksIndex).getSecond().isEmpty()) {
            authorsBooks.remove(authorsBooksIndex);
        }

        int yearsBooksIndex = binarySearch(yearsBooks, book.getYear());
        yearsBooks.get(yearsBooksIndex).getSecond().remove(book);
        if (yearsBooks.get(yearsBooksIndex).getSecond().isEmpty()) {
            yearsBooks.remove(yearsBooksIndex);
        }
        return true; //O(2 log(k) + 2 * k + 2 * m)
    }

    /**
     * Получаем список книг заданного автора
     */
    public List<Book> getBooksByAuthor(String author) {
        int i = binarySearch(authorsBooks, author);
        return (i != -1) ? authorsBooks.get(i).getSecond() : null; //O(log k)
    }

    /**
     * Получаем список книг написанных в определенный год
     */
    public List<Book> getBooksByDate(int year) {
        int i = binarySearch(yearsBooks, year);
        return (i != -1) ? yearsBooks.get(i).getSecond() : null; //O(log k)
    }

    /**
     * Получаем книгу, которую последней добавили в библиотеку
     */
    public Book getLastBook() {
        if (books.isEmpty()) {
            return null;
        }
        return books.get(books.size() - 1); //O(1)
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
                .filter(i -> predicate.test(i, books.get(i)))
                .mapToObj(books::get)
                .collect(Collectors.toList())
                .iterator(); //O(n)
    }

    private <T extends Comparable<T>> int binarySearch(ArrayList<Pair<T, ArrayList<Book>>> arr, T key) {
        int left = 0;
        int right = arr.size() - 1;
        int mid;
        int cmp;
        while (left <= right) {
            mid = (right + left) / 2;
            cmp = key.compareTo(arr.get(mid).getFirst());
            if (cmp == 0) {
                return mid;
            }
            if (cmp < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
