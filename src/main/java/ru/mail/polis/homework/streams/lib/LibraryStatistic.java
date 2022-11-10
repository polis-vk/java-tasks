package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

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
        long limitOfTimeMillis = 14L * 24 * 60 * 60 * 1_000;
        return library.getArchive().stream()
                .filter(archivedData -> genre.equals(archivedData.getBook().getGenre()))
                .filter(archivedData -> timeOfHolding(archivedData) >= limitOfTimeMillis)
                .collect(groupingBy(ArchivedData::getUser, toSet())).entrySet().stream()
                .filter(userSetEntry -> userSetEntry.getValue().size() >= 5)
                .collect(
                        toMap(
                                Map.Entry::getKey,
                                userSetEntry -> userSetEntry.getValue().stream().mapToInt(
                                        i -> i.getBook().getPage()).sum()
                                        + (userSetEntry.getKey().getBook().getGenre() == genre ?
                                        userSetEntry.getKey().getReadedPages() : 0)));
    }


    long timeOfHolding(ArchivedData archivedData) {
        if (archivedData.getReturned() == null) {
            return Timestamp.from(Instant.now()).getTime() - archivedData.getTake().getTime();
        } else {
            return archivedData.getReturned().getTime() - archivedData.getTake().getTime();
        }
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
        List<ArchivedData> usersBook = library.getArchive().stream().filter(i -> i.getUser().equals(user)).collect(toList());
        Map<Genre, Integer> returnedCount = usersBook.stream().collect(
                toMap(
                        archivedData -> archivedData.getBook().getGenre(),
                        archivedData -> archivedData.getReturned() == null ? 0 : 1,
                        Integer::sum
                )
        );

        Map.Entry<Genre, Integer> ans = Collections.max(
                returnedCount.entrySet(),
                (Map.Entry<Genre, Integer> first, Map.Entry<Genre, Integer> second) ->
                        (first.getValue().equals(second.getValue())) ?
                                (user.getBook().getGenre() == first.getKey() ? 1 : user.getBook().getGenre() == second.getKey() ? -1 : 0) :
                                first.getValue().compareTo(second.getValue())
        );

        return ans.getKey();

    }


    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        long limitTimeMillis = 30L * 24 * 60 * 60 * 1_000;
        return library.getArchive().stream().collect(
                        toMap(
                                ArchivedData::getUser,
                                i -> timeOfHolding(i) > limitTimeMillis ? 1 : -1,
                                Integer::sum)).entrySet().stream()
                .filter(i -> i.getValue() > 0)
                .map(Map.Entry::getKey).collect(toList());
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     *
     * @param library   - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return library.getBooks().stream().filter(i -> i.getPage() >= countPage).collect(toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, String> ans = library.getArchive().stream().collect(
                        groupingBy(
                                i -> i.getBook().getGenre(),
                                groupingBy(
                                        i -> i.getBook().getAuthor(),
                                        summingInt(i -> 1)
                                )
                        )
                )
                .entrySet().stream().collect(
                        toMap(
                                Map.Entry::getKey,
                                i -> Collections.max(
                                        i.getValue().entrySet(),
                                        (first, second) ->
                                                first.getValue().equals(second.getValue()) ?
                                                        -first.getKey().compareTo(second.getKey()) :
                                                        first.getValue().compareTo(second.getValue())
                                ).getKey()
                        )
                );
        for (Genre genre : Genre.values()) {
            if (!ans.containsKey(genre)) {
                ans.put(genre, "Author not determined");
            }
        }
        return ans;
    }
}
