package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private static final String NO_AUTHOR = "Author not determined";
    private static final Map.Entry<String, Integer> EMPTY = new AbstractMap.SimpleEntry<>(null, null);

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
                .filter(o -> o.getBook().getGenre() == genre)
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(userListEntry -> {
                    long count = userListEntry.getValue().stream()
                            .filter(archivedData -> {
                                Timestamp returned = archivedData.getReturned();
                                if (returned == null) {
                                    Instant now = new Timestamp(System.currentTimeMillis()).toInstant();
                                    return archivedData.getTake().toInstant().compareTo(now.minus(14, ChronoUnit.DAYS)) <= 0;
                                }
                                return archivedData.getTake().toInstant().compareTo(archivedData.getReturned().toInstant().minus(14, ChronoUnit.DAYS)) <= 0;
                            })
                            .count();
                    return count >= 5;
                })
                .map(entry -> {
                    int sum = entry.getValue().stream()
                            .map(archivedData -> archivedData.getBook().getPage())
                            .reduce(0, Integer::sum);
                    if (entry.getKey().getBook().getGenre() == genre) {
                        sum += entry.getKey().getReadedPages();
                    }
                    return new AbstractMap.SimpleEntry<>(entry.getKey(), sum);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
        Optional<AbstractMap.SimpleEntry<Genre, Integer>> optional = library.getArchive().stream()
                .filter(archivedData -> archivedData.getUser().equals(user))
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre()))
                .entrySet().stream()
                .map(genreListEntry -> new AbstractMap.SimpleEntry<>(genreListEntry.getKey(), genreListEntry.getValue().size()))
                .max((o1, o2) -> {
                    int compareResult = o1.getValue().compareTo(o2.getValue());
                    if (compareResult == 0) {
                        if (user.getBook().getGenre().equals(o1.getKey())) {
                            return 1;
                        } else if (user.getBook().getGenre().equals(o2.getKey())) {
                            return -1;
                        }
                        return 0;
                    }
                    return compareResult;
                });
        return optional.map(Map.Entry::getKey).orElse(null);
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
                .filter(entry -> {
                    long count = entry.getValue().stream()
                            .filter(archivedData -> {
                                Timestamp returned = archivedData.getReturned();
                                if (returned == null) {
                                    Instant now = new Timestamp(System.currentTimeMillis()).toInstant();
                                    return archivedData.getTake().toInstant().isBefore(now.minus(30, ChronoUnit.DAYS));
                                }
                                return archivedData.getTake().toInstant().isBefore(
                                        archivedData.getReturned().toInstant().minus(30, ChronoUnit.DAYS)
                                );
                            })
                            .count();
                    return count * 2 > entry.getValue().size();
                })
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
        return Arrays.stream(Genre.values())
                .map(genre -> {
                    Map<String, Integer> map = new HashMap<>();
                    library.getArchive().stream()
                            .filter(archivedData -> archivedData.getBook().getGenre() == genre)
                            .forEach(archivedData -> map.merge(archivedData.getBook().getAuthor(), 1, Integer::sum));
                    // No books were taken in this genre
                    if (map.isEmpty()) {
                        return new AbstractMap.SimpleEntry<>(genre, NO_AUTHOR);
                    }
                    // Otherwise, we look for the most famous author
                    String mostFamousAuthor = map.entrySet().stream()
                            .max((o1, o2) -> {
                                int compare = o1.getValue().compareTo(o2.getValue());
                                if (compare == 0) {
                                    return o2.getKey().compareTo(o1.getKey());
                                }
                                return compare;
                            })
                            .orElse(EMPTY)
                            .getKey();
                    return new AbstractMap.SimpleEntry<>(genre, mostFamousAuthor);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
