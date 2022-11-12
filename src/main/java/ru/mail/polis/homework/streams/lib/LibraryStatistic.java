package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private final int MIN_READ_DAYS = 14;
    private final int MIN_READ_BOOKS = 5;
    private final int MAX_TAKEN_DAYS = 30;

    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалист жанра считается пользователь который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     * @param library - данные библиотеки
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getBook().getGenre().name().equals(genre.name()))
                .filter(archivedData -> archivedData.getReturned() != null &&
                        TimeUnit.MILLISECONDS.toDays(
                                archivedData.getReturned().getTime() - archivedData.getTake().getTime()
                        ) >= MIN_READ_DAYS)
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream().filter(userEntry -> userEntry.getValue().size() >= MIN_READ_BOOKS)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().
                                mapToInt(archivedData -> archivedData.getBook().getPage()).sum() +
                                (entry.getKey().getBook().getGenre().name().equals(genre.name()) ?
                                        entry.getKey().getReadedPages() : 0))
                );
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(entry -> entry.getKey().equals(user))
                .map(entry -> entry.getValue().stream()
                        .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre())))
                .collect(Collectors.toList()).get(0).entrySet().stream()
                .max((entry1, entry2) -> {
                    if (entry1.getValue().size() == entry2.getValue().size()) {
                        if (entry1.getKey().equals(user.getBook().getGenre())) {
                            return 1;
                        }
                        else if (entry2.getKey().equals(user.getBook().getGenre())) {
                            return -1;
                        }
                    }
                    return Integer.compare(entry1.getValue().size(), entry2.getValue().size());
                }).get().getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(entry ->
                        entry.getValue().stream().filter(archivedData -> {
                            if (archivedData.getReturned() == null) {
                                return (TimeUnit.MILLISECONDS.toDays(Timestamp.valueOf(LocalDateTime.now()).getTime() -
                                        TimeUnit.MILLISECONDS.toDays(archivedData.getTake().getTime()))) > MAX_TAKEN_DAYS;
                            }
                            else {
                                return (TimeUnit.MILLISECONDS.toDays(archivedData.getReturned().getTime()) -
                                        TimeUnit.MILLISECONDS.toDays(archivedData.getTake().getTime())) > MAX_TAKEN_DAYS;
                            }
                                }).count() > (entry.getValue().size() / 2))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     * @param library - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return library.getBooks().stream()
                .filter(book -> book.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return null;
    }
}
