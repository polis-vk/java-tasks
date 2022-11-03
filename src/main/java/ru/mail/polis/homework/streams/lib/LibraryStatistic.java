package ru.mail.polis.homework.streams.lib;

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
                        .allMatch(data -> daysHold(data) >= MIN_DAYS))
                .filter(archive -> archive.getValue().size() >= MIN_READ)
                .collect(Collectors.toMap(Map.Entry::getKey, lib -> lib.getValue()
                        .stream()
                        .map(ArchivedData::getBook)
                        .mapToInt(Book::getPage).sum()));
    }

    private static int daysHold(ArchivedData archive) {
        return (int) ((archive.getReturned() == null ? System.currentTimeMillis() : archive.getReturned().getTime())
                - archive.getTake().getTime() / (1000 * 60 * 60 * 24));
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
        var genre = library.getArchive().stream()
                .filter(archive -> archive.getUser().equals(user)
                        && archive.getReturned() != null)
                .collect(Collectors.groupingBy(archive -> archive.getBook().getGenre(),
                        Collectors.counting()))
                .entrySet()
                .stream()
                .max((a1, a2) -> {
                    long cmp = a2.getValue() - a1.getValue();
                    if (cmp != 0) {
                        return (int) cmp;
                    }
                    var lib = library.getArchive()
                            .stream()
                            .filter(archive -> archive.getUser().equals(user) && archive.getReturned() == null);
                    int first = (int) lib.filter(archive -> archive.getBook().getGenre().equals(a1.getKey())).count();
                    int second = (int) lib.filter(archive -> archive.getBook().getGenre().equals(a2.getKey())).count();
                    return second - first;
                });
        return genre.map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        final int daysHolding = 30;
        return library.getArchive()
                .stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet()
                .stream()
                .filter(archive -> archive.getValue().stream().filter(e -> daysHold(e) >= daysHolding).count()
                        > archive.getValue().size() / 2)
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
                        .max(Map.Entry.<String, Long>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                        .map(Map.Entry::getKey)
                        .orElse("")));
    }
}
