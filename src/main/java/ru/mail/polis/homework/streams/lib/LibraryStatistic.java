package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Map<User, Map<Book, Duration>> userBookTime = library.getArchive()
                .stream()
                .filter(d -> d.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(ArchivedData::getUser,
                        Collectors.toMap(ArchivedData::getBook,
                                d -> Duration.between(d.getTake().toInstant(), d.getReturned() == null
                                        ? Instant.now() : d.getReturned().toInstant()), Duration::plus)));

        Map<User, Map<Book, Integer>> userBookPages = library.getArchive()
                .stream()
                .filter(d -> d.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(ArchivedData::getUser,
                        Collectors.toMap(ArchivedData::getBook, d -> d.getUser().getReadedPages(), Integer::sum)));

        Map<User, Map<Book, Integer>> checked = new HashMap<>();
        userBookPages.forEach((user, map) -> map.forEach((book, count) -> {
            if (userBookTime.get(user).get(book).compareTo(Duration.ofDays(13)) > 0) {
                checked.putIfAbsent(user, new HashMap<>());
                checked.get(user).put(book, count);
            }
        }));

        return checked.entrySet()
                .stream()
                .filter((entry -> entry.getValue().size() > 4))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().values()
                                .stream()
                                .reduce(Integer::sum)
                                .get()));
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
                .filter(archivedData -> archivedData.getUser().equals(user))
                .filter(archivedData -> !archivedData.getBook().equals(user.getBook()))
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre(), Collectors.counting()))
                .entrySet()
                .stream().min((o1, o2) -> {
                    int result = Long.compare(o2.getValue(), o1.getValue());
                    if (result != 0) {
                        return result;
                    }
                    if (o1.getKey().equals(user.getBook().getGenre())) {
                        return -1;
                    }
                    if (o2.getKey().equals(user.getBook().getGenre())) {
                        return 1;
                    }
                    return result;
                })
                .get().getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        Map<User, Long> userBooksMap = library.getArchive()
                .stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.counting()));

        Stream<ArchivedData> expiredTime = library.getArchive()
                .stream()
                .filter(archivedData -> {
                    Instant after = Instant.now();
                    if (archivedData.getReturned() != null) {
                        after = archivedData.getReturned().toInstant();
                    }
                    return Duration.between(archivedData.getTake().toInstant(),
                            after).compareTo(Duration.ofDays(30)) > 0;
                });
        return expiredTime
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.counting()))
                .entrySet()
                .stream()
                .filter((entry) -> (double) entry.getValue() / userBooksMap.get(entry.getKey()) > 0.5)
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
                .filter((book -> book.getPage() >= countPage))
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return library.getArchive()
                .stream()
                .collect(Collectors.groupingBy((data) -> data.getBook().getGenre(), Collectors.groupingBy(
                        (data) -> data.getBook().getAuthor(), Collectors.counting())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry
                        .getValue()
                        .entrySet()
                        .stream().max(Comparator.comparingLong(Map.Entry::getValue)).get().getKey()));


    }

    public static void main(String[] args) {
        Book book1 = new Book("shortStory", "Hemingway", Genre.NOVEL, 20);
        Book book2 = new Book("book2", "Dovlatov", Genre.HISTORY, 25);
        Book book3 = new Book("book3", "Hemingway", Genre.NOVEL, 25);
        Book book4 = new Book("book4", "Hemingway", Genre.NOVEL, 25);
        Book book5 = new Book("book5", "Dovlatov", Genre.NOVEL, 25);//no
        Book book6 = new Book("book6", "Hemingway", Genre.NOVEL, 25);
        Book book7 = new Book("book7", "Dovlatov", Genre.NOVEL, 25);//no

        User user1 = new User("firstUser", 12, book6, 15);
        User user12 = new User("firstUser", 12, book1, 15);
        User user2 = new User("secondUser", 20, book2, 20);

        ArchivedData data1 = new ArchivedData(user1, book1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(31, ChronoUnit.DAYS)));
        ArchivedData data12 = new ArchivedData(user12, book1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
        ArchivedData data3 = new ArchivedData(user1, book2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(31, ChronoUnit.DAYS)));
        ArchivedData data4 = new ArchivedData(user1, book3, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(31, ChronoUnit.DAYS)));
        ArchivedData data5 = new ArchivedData(user1, book4, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(31, ChronoUnit.DAYS)));
        ArchivedData data6 = new ArchivedData(user1, book5, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(31, ChronoUnit.DAYS)));
        ArchivedData data7 = new ArchivedData(user1, book7, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(14, ChronoUnit.DAYS)));
        ArchivedData data2 = new ArchivedData(user2, book2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
        ArchivedData data8 = new ArchivedData(user2, book2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(31, ChronoUnit.DAYS)));
        ArchivedData data9 = new ArchivedData(user2, book2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(31, ChronoUnit.DAYS)));
        ArchivedData data10 = new ArchivedData(user2, book2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plus(30, ChronoUnit.DAYS)));

        Library library = new Library(List.of(user1, user2), List.of(book1, book2, book3, book4, book5, book7),
                List.of(data1, data5, data7, data2, data3, data4, data6, data8, data9, data10, data12));//,
        LibraryStatistic libraryStatistic = new LibraryStatistic();
        System.out.println(libraryStatistic.specialistInGenre(library, Genre.NOVEL));
    }
}
