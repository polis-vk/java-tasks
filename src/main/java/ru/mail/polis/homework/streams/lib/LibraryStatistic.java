package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private final long DAY_IN_MILLISECONDS = 30 * 86400000;
    private final int MIN_CNT_BOOKS = 5;
    private final int MIN_CNT_DAYS_FOR_READ = 14;

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
        return library.getArchive()
                .stream()
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .filter(archivedData -> {
                    if (archivedData.getReturned() == null) {
                        return false;
                    }
                    return getCntDays(archivedData.getReturned(), archivedData.getTake()) >= MIN_CNT_DAYS_FOR_READ;
                })
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(archive -> archive.getValue().size() >= MIN_CNT_BOOKS)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                archive -> {
                                    return archive.getValue().stream()
                                            .mapToInt(archivedData -> archivedData.getBook().getPage())
                                            .sum() + (archive.getKey().getBook().getGenre().equals(genre) ?
                                            archive.getKey().getReadedPages() : 0);
                                }

                        )
                );

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
                .filter(archivedData -> archivedData.getUser().equals(user) && archivedData.getReturned() != null)
                .collect(Collectors.groupingBy(archive -> archive.getBook().getGenre()))
                .entrySet().stream()
                .max(
                        (first, second) -> {
                            int result = first.getValue().size() - second.getValue().size();
                            if (result == 0) {
                                return (user.getBook().getGenre().equals(first.getKey()) ? 1 : 0) -
                                        (user.getBook().getGenre().equals(second.getKey()) ? 1 : 0);
                            }

                            return result;
                        })
                .orElse(null)
                .getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(
                        archive -> archive.getValue().stream()
                                .filter(book -> {
                                    if (book.getReturned() == null) {
                                        return book.getTake().getTime() > DAY_IN_MILLISECONDS;
                                    }

                                    return getCntDays(book.getReturned(), book.getTake()) > 30;
                                }).count() > archive.getValue().size() / 2
                ).map(Map.Entry::getKey).collect(Collectors.toList());
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
                .filter(archive -> archive.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, String> genres = library.getArchive().stream()
                .collect(Collectors.groupingBy(
                        book -> book.getBook().getGenre(),
                        Collectors.groupingBy(
                                book -> book.getBook().getAuthor(),
                                Collectors.counting()
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        o -> o.getValue()
                                .entrySet().stream()
                                .max((o1, o2) -> {
                                    long result = o1.getValue().compareTo(o2.getValue());

                                    if (result == 0) {
                                        return -o1.getKey().compareTo(o2.getKey());
                                    }

                                    return (int) result;
                                }).get().getKey()
                ));

        library.getBooks().forEach(it -> genres.putIfAbsent(it.getGenre(), "Author not determined"));
        return genres;
    }


    public static int getCntDays(Timestamp returnedTime, Timestamp takeTime) {
        return (int) Math.abs(ChronoUnit.DAYS.between(returnedTime.toInstant(), takeTime.toInstant()));
    }
}
