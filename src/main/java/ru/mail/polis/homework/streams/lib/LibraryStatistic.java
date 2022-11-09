package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    static final int COUNT_DAYS_FOR_SPECIALIST = 14;
    static final int COUNT_BOOKS_FOR_SPECIALIST = 5;

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
        return library.getArchive().stream()
                .filter(d -> d.getBook().getGenre() == genre)
                .filter(d -> d.getReturned() != null
                        && TimeUnit.MILLISECONDS.toDays(d.getReturned().getTime() - d.getTake().getTime())
                        >= COUNT_DAYS_FOR_SPECIALIST
                        ||
                        d.getReturned() == null
                                && TimeUnit.MILLISECONDS.toDays(new Timestamp(System.currentTimeMillis()).getTime() - d.getTake().getTime())
                                >= COUNT_DAYS_FOR_SPECIALIST)
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(d -> d.getValue().size() >= COUNT_BOOKS_FOR_SPECIALIST)
                .collect(Collectors.toMap(Map.Entry::getKey, v ->
                        (v.getKey().getBook().getGenre() == genre ?
                                v.getKey().getReadedPages() : 0)
                                + v.getValue().stream()
                                .map(d -> d.getBook().getPage())
                                .mapToInt(i -> i).sum()));
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
        Map<Genre, String> result = new HashMap<>();
        for (Genre genre : Genre.values()) {
            result.put(genre, library.getBooks().stream()
                    .filter(d -> d.getGenre() == genre)
                    .collect(Collectors.groupingBy(Book::getAuthor, Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse("Author not determined"));
        }
        return result;
    }
}
