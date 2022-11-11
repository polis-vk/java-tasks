package ru.mail.polis.homework.streams.lib;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        final int MIN_READ = 5;
        final int MIN_DAYS = 14;
        return library.getArchive().stream()
                .filter(archive -> genre.equals(archive.getBook().getGenre()))
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet()
                .stream()
                .filter(archive -> archive.getValue()
                        .stream()
                        .allMatch(data -> data.getReturned() != null
                                ? ChronoUnit.DAYS.between(data.getTake().toInstant(), data.getReturned().toInstant()) >= (long) MIN_DAYS
                                : ChronoUnit.DAYS.between(data.getTake().toInstant(), Instant.now()) >= (long) MIN_DAYS))
                .filter(archive -> archive.getValue().size() >= MIN_READ)
                .collect(Collectors.toMap(Map.Entry::getKey, userListEntry -> userListEntry.getValue()
                        .stream()
                        .mapToInt(archivedData -> archivedData.getBook().getPage())
                        .sum()
                        + (userListEntry.getKey().getBook().getGenre().equals(genre)
                        ? userListEntry.getKey().getReadedPages()
                        : 0)
                ));
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
        return library.getArchive()
                .stream()
                .filter(archive -> archive.getUser().equals(user) && archive.getReturned() != null)
                .collect(Collectors.groupingBy(archive -> archive.getBook().getGenre(), Collectors.counting()))
                .entrySet()
                .stream()
                .max((a1, a2) -> {
                    long cmp = a1.getValue() - a2.getValue();
                    if (cmp != 0) {
                        return (int) cmp;
                    }
                    if (a1.getKey().equals(user.getBook().getGenre())) {
                        return 1;
                    }
                    return -1;
                }).map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        final int DAYS_HOLDING = 30;
        return library.getArchive()
                .stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet()
                .stream()
                .filter(archive -> archive.getValue()
                        .stream()
                        .filter(data -> data.getReturned() != null
                                ? ChronoUnit.DAYS.between(data.getTake().toInstant(), data.getReturned().toInstant()) >= (long) DAYS_HOLDING
                                : ChronoUnit.DAYS.between(data.getTake().toInstant(), Instant.now()) >= (long) DAYS_HOLDING)
                        .count() > archive.getValue().size() / 2)
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
        return library.getBooks()
                .stream()
                .collect(Collectors.groupingBy(Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, genreMapEntry -> genreMapEntry.getValue()
                        .entrySet()
                        .stream()
                        .max(Map.Entry.<String, Long>comparingByValue()
                                .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                        .filter(authorEntry -> library.getArchive()
                                .stream()
                                .anyMatch(archivedData -> archivedData.getBook().getAuthor().equals(authorEntry.getKey())))
                        .map(Map.Entry::getKey)
                        .orElse("Author not determined")));
    }
}
