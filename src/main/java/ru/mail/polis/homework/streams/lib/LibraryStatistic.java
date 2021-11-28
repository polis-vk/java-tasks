package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    private static final int DAYS_TO_BECOME_SPECIALIST = 14;
    private static final int DAYS_TO_BECOME_UNRELIABLE_USER = 30;

    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалист жанра считается пользователь который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     * @param library - данные библиотеки
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        List<ArchivedData> archive = library.getArchive();
        return library.getUsers().stream()
                .filter(user -> archive.stream()
                        .filter(archivedData -> archivedData.getUser().equals(user)
                                && archivedData.getBook().getGenre() == genre
                                && ((archivedData.getReturned() == null && daysBetween(System.currentTimeMillis(), archivedData.getReturned()) > DAYS_TO_BECOME_SPECIALIST)
                                    || (archivedData.getReturned() != null && daysBetween(archivedData.getReturned(), archivedData.getTake()) > DAYS_TO_BECOME_SPECIALIST))
                        )
                        .count() >= 5)
                .collect(Collectors.toMap(Function.identity(), User::getReadedPages));
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        Map<Genre, Long> genreCountingMap = library.getArchive().stream()
                .filter(archivedData -> archivedData.getUser().equals(user))
                .collect(Collectors.groupingBy(
                        archivedData -> archivedData.getBook().getGenre(),
                        Collectors.counting()
                        )
                );
        long maxGenreCounter = genreCountingMap.values().stream().max(Comparator.comparingLong(value -> value)).get();
        List<Genre> maxGenresList = genreCountingMap.entrySet().stream()
                .filter(genreLongEntry -> genreLongEntry.getValue() == maxGenreCounter)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        int loveGenreIndex = maxGenresList.indexOf(user.getBook().getGenre());
        return maxGenresList.get(Math.max(loveGenreIndex, 0));
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 14-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        List<ArchivedData> archive = library.getArchive();
        return library.getUsers().stream()
                .filter(user -> archive.stream()
                        .filter(archivedData -> archivedData.getUser().equals(user)
                                && ((archivedData.getReturned() == null && daysBetween(System.currentTimeMillis(), archivedData.getReturned()) > DAYS_TO_BECOME_UNRELIABLE_USER)
                                    || (archivedData.getReturned() != null && daysBetween(archivedData.getReturned(), archivedData.getTake()) > DAYS_TO_BECOME_UNRELIABLE_USER))
                        )
                        .count() > archive.stream().filter(archivedData -> archivedData.getUser().equals(user)).count() / 2
                )
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
        Map<Genre, Map<String, Long>> authorCountingByGenreMap = library.getBooks().stream()
                .collect(Collectors.groupingBy(
                        Book::getGenre,
                        Collectors.groupingBy(
                                Book::getAuthor,
                                Collectors.counting()
                        )
                ));
        return authorCountingByGenreMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        genreMapEntry -> genreMapEntry.getValue().entrySet().stream()
                                .max(authorWithCountingComparator).get().getKey()
                        )
                );
    }

    private long daysBetween(long millis, Timestamp t2) {
        return ((millis - t2.getTime()) / 3600) / 24;
    }

    private long daysBetween(Timestamp t1, Timestamp t2) {
        return ((t1.getTime() - t2.getTime()) / 3600) / 24;
    }

    private final Comparator<Map.Entry<String, Long>> authorWithCountingComparator = (o1, o2) -> {
        if (o1.getValue() > o2.getValue()) {
            return 1;
        } else if (o1.getValue() < o2.getValue()) {
            return -1;
        } else {
            return -o1.getKey().compareTo(o2.getKey());
        }
    };
}
