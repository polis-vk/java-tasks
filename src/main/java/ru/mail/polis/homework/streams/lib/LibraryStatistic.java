package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        return library.getArchive().stream()
                .filter(e -> (e.getBook().getGenre() == genre))
                .filter(e -> (getDays(e.getTake(), e.getReturned()) >= 14))
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.counting()))
                .entrySet().stream()
                .filter(s -> (s.getValue() >= 5))
                .map(Map.Entry::getKey).collect(Collectors.toMap(s -> s, User::getReadedPages));
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
        Map<Genre, Long> mapGenreCount = library.getArchive().stream()
                .filter(e -> e.getUser().equals(user))
                .collect(Collectors.groupingBy(e -> (e.getBook().getGenre()), Collectors.counting()));
        long max = mapGenreCount.values().stream()
                .max(Comparator.comparingLong(s -> s)).get();
        Set<Genre> favoriteGenres = mapGenreCount.entrySet().stream()
                .filter(k -> (k.getValue().equals(max)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        if (favoriteGenres.size() == 1 || (!favoriteGenres.contains(user.getBook().getGenre()))) {
            return favoriteGenres.stream().findAny().get();
        }
        return user.getBook().getGenre();
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
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList()))
                .entrySet().stream()
                .filter(entrySet -> (entrySet.getValue().stream()
                        .filter(archivedData ->
                                (getDays(archivedData.getTake(), archivedData.getReturned()) > 30))
                        .count() > entrySet.getValue().size() / 2))
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
        return library.getBooks().stream()
                .filter(s -> (s.getPage() >= countPage))
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(s -> (s.getBook().getGenre()),
                        Collectors.groupingBy(s -> s.getBook().getAuthor(), Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, genreMapEntrySet -> genreMapEntrySet.getValue().entrySet().stream()
                                .max((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                                .get()
                                .getKey()));
    }

    private long getDays(Timestamp timestamp1, Timestamp timestamp2) {
        if (timestamp2 == null) {
            timestamp2 = new Timestamp(System.currentTimeMillis());
        }
        return ChronoUnit.DAYS.between(timestamp1.toInstant(),
                timestamp2.toInstant());
    }
}
