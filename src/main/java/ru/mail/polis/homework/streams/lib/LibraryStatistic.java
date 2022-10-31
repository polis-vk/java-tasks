package ru.mail.polis.homework.streams.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалист жанра считается пользователь который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     *
     * @param library - данные библиотеки
     * @param genre   - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        Map<User, Integer> resultMap = new HashMap<>();
        List<User> users = library.getUsers();
        List<ArchivedData> archivedData = library.getArchive();
        List<Book> curListOfBooks;
        int curCountPages;
        for (User user : users) {
            curListOfBooks = archivedData.stream()
                    .filter(d -> d.getUser() == user)
                    .filter(d -> d.getBook().getGenre() == genre)
                    .filter(d -> d.getReturned() != null)
                    .filter(d -> (d.getReturned().getTime() - d.getTake().getTime()) > 840000L)
                    .map(ArchivedData::getBook)
                    .collect(Collectors.toList());
            if (curListOfBooks.size() >= 5) {
                curCountPages = curListOfBooks.stream()
                        .map(Book::getPage)
                        .mapToInt(i -> i)
                        .sum();
                resultMap.put(user, curCountPages);
            }
        }
        return resultMap;
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     *
     * @param library - данные библиотеки
     * @param user    - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        return library.getArchive().stream()
                .filter(d -> d.getUser() == user)
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        List<User> resultList = new ArrayList<>();
        int curCountBooks;
        for (User user : library.getUsers()) {
            curCountBooks = (int) library.getArchive().stream()
                    .filter(d -> d.getUser() == user)
                    .count();
            if (library.getArchive().stream()
                    .filter(d -> d.getUser() == user)
                    .filter(d -> (d.getReturned() == null) || (d.getReturned().getTime() - d.getTake().getTime()) > 2592000000L)
                    .count() > curCountBooks / 2) {
                resultList.add(user);
            }
        }
        return resultList;
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     *
     * @param library   - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return library.getBooks().stream()
                .filter(b -> b.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, String> resultMap = new HashMap<>();
        for (Genre genre : Genre.values()) {
            resultMap.put(genre, library.getBooks().stream()
                    .filter(b -> b.getGenre() == genre)
                    .collect(Collectors.groupingBy(Book::getAuthor, Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse(null));
        }
        return resultMap;
    }
}
