package ru.mail.polis.homework.streams.lib;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private static final int BOOKS_MIN = 5;
    private static final int DAYS_MIN = 14;
    private static final int MONTH = 30;


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
        return library.getUsers()
                .stream()
                .filter(user -> library.getArchive().stream().filter(archivedData -> archivedData.getBook().getGenre().equals(genre)
                        && archivedData.getUser().equals(user)
                        && isExpert(archivedData, DAYS_MIN)).count() >= BOOKS_MIN)
                .collect(Collectors.toMap(user -> user, User::getReadedPages));
    }

    private boolean isExpert(ArchivedData data, int days) {
        return TimeUnit.MILLISECONDS.toDays(data.getReturned() == null ?
                new Date().getTime() : data.getReturned().getTime() - data.getTake().getTime()) >= days;
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
        Map<Genre, Long> allGeners = library.getArchive()
                .stream()
                .filter(archivedData -> archivedData.getUser().equals(user))
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre(), Collectors.counting()));
        long userGener = allGeners
                .values()
                .stream()
                .max(Comparator.comparingLong(value -> value))
                .get();
        List<Genre> bestGeners = allGeners.entrySet()
                .stream()
                .filter(genreLongEntry -> genreLongEntry.getValue() == userGener)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return bestGeners.stream().findFirst().orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getUsers()
                .stream()
                .filter(user -> library.getArchive()
                        .stream()
                        .filter(archivedData -> archivedData.getUser().equals(user) && isExpert(archivedData, MONTH) || (archivedData.getReturned() == null))
                        .count() > library.getArchive().stream()
                        .filter(archivedData -> archivedData.getUser().equals(user))
                        .count() / 2)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     *
     * @param library   - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return library.getBooks()
                .stream()
                .filter(book -> book.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return null;
    }
}