package ru.mail.polis.homework.streams.lib;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    private static final int MIN_SPECIALIST_DAYS = 14;
    private static final int MIN_SPECIALIST_BOOKS = 5;
    private static final int UNRELIABLE_DAYS = 30;
    private static final double RELIABILITY_MULTIPLIER = 0.5;

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
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(e -> e.getValue().stream()
                        .filter(ad -> checkDuration(ad, MIN_SPECIALIST_DAYS))
                        .toArray().length > MIN_SPECIALIST_BOOKS)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .mapToInt(d -> d.getBook().getPage())
                                .sum()));
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
                .filter(d -> d.getUser().equals(user))
                .collect(Collectors.groupingBy(d -> d.getBook().getGenre()))
                .entrySet().stream()
                .max(this::compareGenre).map(Map.Entry::getKey).orElse(null);
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
                .filter(e -> (int) e.getValue().stream()
                        .filter(ad -> checkDuration(ad, UNRELIABLE_DAYS))
                        .count() / (double) e.getValue().size() > RELIABILITY_MULTIPLIER)
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
        return library.getBooks().stream().filter(book -> book.getPage() >= countPage).collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return library.getBooks().stream()
                .collect(Collectors.groupingBy(Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().entrySet().stream()
                                .max(Map.Entry.<String, Long>comparingByValue()
                                        .thenComparing(Map.Entry.comparingByKey()))
                                .map(Map.Entry::getKey).orElse("")));
    }

    private boolean checkDuration(ArchivedData data, int duration) {
        return (data.getReturned() == null
                && Duration.between(data.getTake().toLocalDateTime(),
                LocalDateTime.now()).toDays() >= duration)
                || Duration.between(data.getTake().toLocalDateTime(),
                data.getReturned().toLocalDateTime()).toDays() >= duration;
    }

    private int compareGenre(Map.Entry<Genre, List<ArchivedData>> data1, Map.Entry<Genre, List<ArchivedData>> data2) {
        if (data1.getValue().size() == data2.getValue().size()) {
            return data1.getValue().stream().noneMatch(d -> d.getReturned() == null) ? 0 : 1;
        }
        return data1.getValue().size() - data2.getValue().size();
    }
}
