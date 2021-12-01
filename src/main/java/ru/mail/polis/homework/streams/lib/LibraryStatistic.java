package ru.mail.polis.homework.streams.lib;

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
    private static final int SPECIALIST_BECOME_TIME = 14;
    private static final int UNRELIABLE_USER_TIME = 30;

    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалист жанра считается пользователь который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     * @param library - данные библиотеки
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library
                .getUsers()
                .stream()
                .filter(user -> library.getArchive().stream()
                        .filter(archive -> archive.getUser().equals(user)
                                && ((archive.getReturned() == null && countDays(System.currentTimeMillis(), archive.getTake().getTime()) >= SPECIALIST_BECOME_TIME)
                                    || (archive.getReturned() != null && countDays(archive.getReturned().getTime(), archive.getTake().getTime()) >= SPECIALIST_BECOME_TIME)))
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
        Map<Genre, Long> countUserReadingGenres = library
                                                    .getArchive()
                                                    .stream()
                                                    .filter(archive -> archive.getUser().equals(user))
                                                    .collect(Collectors.groupingBy(
                                                                    archive -> archive.getBook().getGenre(),
                                                                    Collectors.counting()
                                                            )
                                                    );
        long userGenreMaxCount = countUserReadingGenres
                                    .values()
                                    .stream()
                                    .max(Comparator.comparingLong(value -> value))
                                    .get();
        List<Genre> maxGenresList = countUserReadingGenres
                                    .entrySet()
                                    .stream()
                                    .filter(genreLongEntry -> genreLongEntry.getValue() == userGenreMaxCount)
                                    .map(Map.Entry::getKey)
                                    .collect(Collectors.toList());
        int readingAtTheMomentIndex = maxGenresList.indexOf(user.getBook().getGenre());
        return maxGenresList.get(Math.max(readingAtTheMomentIndex, 0));
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library
                .getUsers()
                .stream()
                .filter(user -> library
                                .getArchive()
                                .stream()
                                .filter(archive -> archive.getUser().equals(user)
                                        && ((archive.getReturned() == null && countDays(System.currentTimeMillis(), archive.getTake().getTime()) > UNRELIABLE_USER_TIME)
                                        || (archive.getReturned() != null && countDays(archive.getReturned().getTime(), archive.getTake().getTime()) > UNRELIABLE_USER_TIME))
                                )
                                .count() > library.getArchive().stream().filter(archive -> archive.getUser().equals(user)).count() / 2
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
        return library
                .getBooks()
                .stream()
                .filter(book -> book.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, Map<String, Long>> authorGenrePopularity = library
                                                                    .getBooks()
                                                                    .stream()
                                                                    .collect(Collectors.groupingBy(
                                                                            Book::getGenre,
                                                                            Collectors.groupingBy(
                                                                                    Book::getAuthor,
                                                                                    Collectors.counting()
                                                                            )
                                                                    ));
        return authorGenrePopularity
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                genreMapEntry -> genreMapEntry
                                                    .getValue()
                                                    .entrySet()
                                                    .stream()
                                                    .max(authorPopularityComparator)
                                                    .get()
                                                    .getKey()
                                )
                );
    }

    private long countDays(long t1, long t2) {
        return (t1 - t2) / 86400000;
    }

    private final Comparator<Map.Entry<String, Long>> authorPopularityComparator = (author1, author2) -> {
        if (author1.getValue() > author2.getValue()) {
            return 1;
        } else if (author1.getValue() < author2.getValue()) {
            return -1;
        } else {
            return -author1.getKey().compareTo(author2.getKey());
        }
    };
}
