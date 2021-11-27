package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5 баллов
 */
public class LibraryStatistic {

    // Количество книг жанра, прочитанных специалистом >= SPECIALIST_MIN_BOOKS_READ (необходимое требование)
    public static final int SPECIALIST_MIN_BOOKS_READ = 5;
    // Продолжительность чтения специалистом каждой книги жанра >= SPECIALIST_MIN_DURATION_DAYS (необходимое требование)
    public static final int SPECIALIST_MIN_DURATION_DAYS = 14;
    // Ненадежный пользователь -- тот, доля передержанных книг у которого >= UNRELIABLE_MIN_SHARE
    public static final double UNRELIABLE_MIN_SHARE = 0.5;
    // Передержанная книга -- та, возврат которой произошел на > UNRELIABLE_MIN_DURATION_DAYS позже взятия
    public static final int UNRELIABLE_MIN_DURATION_DAYS = 30;

    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалист жанра считается пользователь, который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     *
     * @param library - данные библиотеки
     * @param genre   - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        final Collector<ArchivedData, ?, Long> summingDurationDays = Collectors.mapping(
                rent -> durationDays(rent, rent.getTake()),
                Collectors.reducing(0L, Long::sum));
        final Map<User, Map<Book, Long>> userToBookToRentDuration = library.getArchive().stream()
                .filter(rent -> rent.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(
                        ArchivedData::getUser,
                        Collectors.groupingBy(ArchivedData::getBook, summingDurationDays)));
        userToBookToRentDuration.entrySet().removeIf(entry -> {
            Map<Book, Long> bookToRentDurationSum = entry.getValue();
            return bookToRentDurationSum.size() < SPECIALIST_MIN_BOOKS_READ
                    || bookToRentDurationSum.values().stream().anyMatch(days -> days < SPECIALIST_MIN_DURATION_DAYS);
        });
        return userToBookToRentDuration.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().keySet().stream()
                                .mapToInt(Book::getPage)
                                .sum()
                                + readingProgress(entry.getKey(), genre)));
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот, что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     *
     * @param library - данные библиотеки
     * @param user    - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        final Genre currentBookGenre = user.getBook().getGenre();
        final Collector<ArchivedData, ?, Map<Genre, Integer>> countingByGenre = Collectors.groupingBy(
                rent -> rent.getBook().getGenre(),
                countingDistinctBy(ArchivedData::getBook));
        final Comparator<Map.Entry<Genre, Integer>> comparingWeights = Map.Entry.<Genre, Integer>comparingByValue()
                .thenComparing(entry -> entry.getKey().equals(currentBookGenre));
        return library.getArchive().stream()
                .filter(d -> d.getUser().equals(user))
                .collect(countingByGenre)
                .entrySet()
                .stream()
                .max(comparingWeights)
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Вернуть список пользователей, которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги, которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        Timestamp now = Timestamp.from(Instant.now());
        Collector<ArchivedData, Object, Boolean> collectingIsReliable = Collectors.collectingAndThen(
                Collectors.partitioningBy(
                        entry -> durationDays(entry, now) <= UNRELIABLE_MIN_DURATION_DAYS,
                        countingDistinctBy(ArchivedData::getBook)),
                this::isReliable);
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser, collectingIsReliable))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
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
                .filter(b -> b.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково, брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, String> map = library.getArchive()
                .stream()
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.collectingAndThen(
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting()),
                        m -> m.entrySet().stream()
                                .max(Map.Entry.<String, Long>comparingByValue()
                                        .thenComparing(Map.Entry.<String, Long>comparingByKey().reversed()))
                                .map(Map.Entry::getKey)
                                .orElse(null))));
        Set<Genre> genres = library.getBooks().stream()
                .map(Book::getGenre)
                .collect(Collectors.toSet());
        for (Genre genre : genres) {
            map.putIfAbsent(genre, null);
        }
        return map;
    }

    private long durationDays(ArchivedData rent, Timestamp defaultReturned) {
        Timestamp returned = rent.getReturned();
        if (returned == null) {
            returned = defaultReturned;
        }
        return ChronoUnit.DAYS.between(returned.toInstant(), rent.getTake().toInstant());
    }

    private int readingProgress(User user, Genre genre) {
        if (user.getBook() == null) {
            return 0;
        }
        if (!user.getBook().getGenre().equals(genre)) {
            return 0;
        }
        return user.getReadedPages();
    }

    private boolean isReliable(Map<Boolean, Integer> distribution) {
        int inBounds = distribution.getOrDefault(true, 0);
        int outOfBounds = distribution.getOrDefault(false, 0);
        return outOfBounds <= UNRELIABLE_MIN_SHARE * (inBounds + outOfBounds);
    }

    private <T, K> Collector<T, ?, Integer> countingDistinctBy(Function<? super T, ? extends K> classifier) {
        return Collectors.collectingAndThen(
                Collectors.mapping(classifier, Collectors.toSet()),
                Set::size);
    }
}
