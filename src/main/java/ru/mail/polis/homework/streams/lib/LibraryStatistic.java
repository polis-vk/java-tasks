package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private static boolean isHeldEqualsOrMoreThanInDays(ArchivedData archivedData, int days) {
        Timestamp returnedTimestamp = archivedData.getReturned();
        LocalDateTime returned;
        if (returnedTimestamp == null) {
            returned = LocalDateTime.now();
        } else {
            returned = returnedTimestamp.toLocalDateTime();
        }
        LocalDateTime takePlusDays = archivedData.getTake()
                .toLocalDateTime()
                .plusDays(days);
        return returned.isAfter(takePlusDays) ||
                returned.isEqual(takePlusDays);
    }

    private static long expiredInDaysBooksCount(List<ArchivedData> archive, int maxDays) {
        return archive.stream()
                .filter(archiveData -> isHeldEqualsOrMoreThanInDays(archiveData, maxDays))
                .count();
    }

    private static long booksCount(List<ArchivedData> archive) {
        return archive.size();
    }

    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалист жанра считается пользователь который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     * @param library - данные библиотеки
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library.getArchive()
                .stream()
                .filter(archivedData -> Objects.equals(archivedData.getBook().getGenre(), genre) &&
                        isHeldEqualsOrMoreThanInDays(archivedData,14))
                .collect(Collectors.groupingBy(ArchivedData::getUser,
                        Collectors.mapping(archivedData -> archivedData.getBook().getPage(), Collectors.toList())))
                .entrySet()
                .stream()
                .peek(entry -> {
                    User user = entry.getKey();
                    Book userBook = user.getBook();
                    if (userBook != null && Objects.equals(userBook.getGenre(), genre)) {
                        entry.getValue().add(user.getReadedPages());
                    }
                })
                .filter(entry -> entry.getValue().size() >= 5)
                .collect(Collectors.toMap(Map.Entry<User, List<Integer>>::getKey, entry -> entry.getValue()
                        .stream()
                        .mapToInt(page -> page)
                        .sum()));
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        Genre currentUserGenre = user.getBook() != null ? user.getBook().getGenre() : null;
        return library.getArchive()
                .stream()
                .filter(archivedData -> user.equals(archivedData.getUser()) &&
                                        !Objects.equals(archivedData.getBook().getGenre(), currentUserGenre))
                .collect(Collectors.groupingBy(
                        archivedData -> archivedData.getBook().getGenre(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                                               Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getKey))
                .map(entry -> {
                    List<Genre> maxGenres = entry.getValue();
                    if (maxGenres.size() > 1) {
                        return currentUserGenre;
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
        return library.getArchive()
                .stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet()
                .stream()
                .filter(entry -> expiredInDaysBooksCount(entry.getValue(), 30) > booksCount(entry.getValue()) / 2)
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
        return library.getBooks()
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
        return Arrays.stream(Genre.values())
                .collect(Collectors.toMap(
                        genre -> genre,
                        genre -> library.getArchive()
                                .stream()
                                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                                .collect(Collectors.groupingBy(
                                        archivedData -> archivedData.getBook().getAuthor(),
                                        Collectors.counting()
                                ))
                                .entrySet()
                                .stream()
                                .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue)
                                        .thenComparing(Map.Entry::getKey)
                                        .reversed())
                                .map(Map.Entry::getKey)
                                .orElse("Author not determined")
                ));
    }
}
