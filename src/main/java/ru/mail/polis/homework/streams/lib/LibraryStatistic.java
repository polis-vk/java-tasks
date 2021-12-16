package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    public static final int COUNT_BOOK_MIN = 5;
    public static final int COUNT_DAY_MIN = 14;
    public static final int COUNT_DAY_LIMIT = 30;

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
        return Collections.unmodifiableMap(library.getArchive().stream()
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(archiveList -> archiveList.getValue().size() >= COUNT_BOOK_MIN
                        && archiveList.getValue().stream().allMatch(data -> controlDay(data, COUNT_DAY_MIN)))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                        .mapToInt(data -> data.getBook().getPage()).sum())));
    }

    private boolean controlDay(ArchivedData data, int limit) {
        return data.getReturned() == null && limit <= Duration.between(data.getTake().toLocalDateTime(),
                LocalDateTime.now()).toDays() || limit <= Duration.between(data.getTake().toLocalDateTime(),
                data.getReturned().toLocalDateTime()).toDays();
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
                .filter(data -> data.getUser().equals(user))
                .collect(Collectors.groupingBy(data -> data.getBook().getGenre(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return Collections.unmodifiableList(library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser)).entrySet().stream()
                .filter(dataList -> dataList.getValue().stream()
                        .filter(archivedData ->
                                controlDay(archivedData, COUNT_DAY_LIMIT)).count() > dataList.getValue().size() / 2)
                .map(Map.Entry::getKey).collect(Collectors.toList()));
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     *
     * @param library   - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return Collections.unmodifiableList(library.getBooks().stream().filter(book -> book.getPage() >= countPage).collect(Collectors.toList()));
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return Collections.unmodifiableMap(library.getBooks().stream()
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    Map.Entry<String, Long> res = entry.getValue().entrySet().stream()
                            .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue)
                                    .thenComparing(Map.Entry.comparingByKey())).orElse(null);
                    return res == null ? "Unknown" : res.getKey();
                })));
    }
}
