package ru.mail.polis.homework.streams.lib;

import org.graalvm.compiler.lir.LIRInstruction;

import javax.swing.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Objects;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

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
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .filter(archivedData -> {
                    Temporal takeTime = archivedData.getTake().toInstant();
                    Timestamp returnedTime = archivedData.getReturned();

                    if (returnedTime == null) {
                        return countHoldDays(takeTime, Instant.now()) >= 14;
                    } else {
                        return countHoldDays(takeTime, returnedTime.toInstant()) >= 14;
                    }
                })
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() >= 5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        userListEntry -> userListEntry.getValue().stream()
                                .mapToInt(archivedData -> archivedData.getBook().getPage())
                                .sum() + (userListEntry.getKey().getBook().getGenre().equals(genre) ?
                                userListEntry.getKey().getReadedPages() : 0)
                ));
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        Genre currentGenre = user.getBook() == null ? null : user.getBook().getGenre();
        return library.getArchive().stream()
                .filter(archivedData -> !Objects.equals(archivedData.getBook().getGenre(), currentGenre)
                        && archivedData.getUser().equals(user))
                .collect(Collectors.groupingBy(
                        archivedData -> archivedData.getBook().getGenre(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getKey))
                .map(entry -> {
                    List<Genre> maxGenres = entry.getValue();
                    if (maxGenres.size() > 1) {
                        return currentGenre;
                    }
                    return maxGenres.get(0);
                })
                .orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(
                        ArchivedData::getUser,
                        Collectors.toList()
                )).entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .filter(archivedData -> {
                            Temporal takeTime = archivedData.getTake().toInstant();
                            Timestamp returnedTime = archivedData.getReturned();

                            if (returnedTime == null) {
                                return countHoldDays(takeTime, Instant.now()) >= 14;
                            } else {
                                return countHoldDays(takeTime, returnedTime.toInstant()) >= 14;
                            }
                        })
                        .count() > (entry.getValue().size() / 2))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
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
        return library.getBooks().stream()
                .collect(Collectors.groupingBy(
                        Book::getGenre,
                        Collectors.groupingBy(
                                Book::getAuthor,
                                Collectors.counting()
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .entrySet()
                                .stream()
                                .max((o1, o2) -> (int) (o1.getValue() - o2.getValue()))
                                .orElseThrow(NoSuchElementException::new)
                                .getKey()));
    }

    private static int countHoldDays(Temporal takeTime, Temporal returnedTime) {
        return (int) ChronoUnit.DAYS.between(takeTime, returnedTime);
    }

}