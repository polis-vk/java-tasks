package ru.mail.polis.homework.streams.lib;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    private final static int READING_DAYS = 14;
    private final static int SECOND_DAYS = 30;
    private final static int COUNT_OF_BOOKS = 5;
    private final static String AUTHOR_ERROR = "Author not determined";

    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалист жанра считается пользователь который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     * @param library - данные библиотеки
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        Map<User, Integer> resultMap = library.getArchive().stream()
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .filter(archivedData -> archivedData.getReturned() != null ?
                        ChronoUnit.DAYS.between(archivedData.getTake().toInstant(), archivedData.getReturned().toInstant()) >= READING_DAYS
                        : ChronoUnit.DAYS.between(archivedData.getTake().toInstant(), Instant.now()) >= READING_DAYS)
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList()))
                .entrySet().stream().filter(users -> users.getValue().size() >= COUNT_OF_BOOKS)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        userListEntry -> userListEntry.getValue().stream().filter(data -> data.getUser().equals(userListEntry.getKey()))
                                .filter(data -> data.getUser().getBook().getGenre().equals(userListEntry.getKey().getBook().getGenre()))
                                .mapToInt(data -> data.getBook().getPage())
                                .sum()
                ));
        for (Map.Entry<User, Integer> entry : resultMap.entrySet()) {
            if (entry.getKey().getBook() != null && entry.getKey().getBook().getGenre().equals(genre)) {
                resultMap.put(entry.getKey(), entry.getKey().getReadedPages() + entry.getValue());
            }
        }
        return resultMap;
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     *
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        return library.getArchive().stream().filter(archive -> user.equals(archive.getUser()))
                .collect(Collectors.groupingBy(
                        archivedData -> archivedData.getBook().getGenre(),
                        Collectors.counting()
                )).entrySet().stream().max((o1, o2) -> {
                    if (o1.getValue() - o2.getValue() != 0) {
                        return (int) (o1.getValue() - o2.getValue());
                    }
                    Genre genreOfNotReturnedBook = library.getUsers().stream()
                            .filter(x -> x.equals(user))
                            .findFirst().orElseThrow(NoSuchElementException::new).getBook().getGenre();
                    if (genreOfNotReturnedBook.equals(o2.getKey())) {
                        o2.setValue(o2.getValue() + 1);
                    }
                    if (genreOfNotReturnedBook.equals(o1.getKey())) {
                        o1.setValue(o1.getValue() + 1);
                    }
                    return (int) (o1.getValue() - o2.getValue());
                })
                .orElseThrow(NoSuchElementException::new)
                .getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(
                        ArchivedData::getUser,
                        Collectors.toList()
                )).entrySet().stream()
                .filter(users -> users.getValue().stream()
                        .filter(archivedData -> archivedData.getReturned() != null ?
                                ChronoUnit.DAYS.between(archivedData.getTake().toInstant(), archivedData.getReturned().toInstant()) >= SECOND_DAYS
                                : ChronoUnit.DAYS.between(archivedData.getTake().toInstant(), Instant.now()) >= SECOND_DAYS)
                        .count() > (users.getValue().size() / 2))
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
        return library.getBooks().stream().filter(book -> book.getPage() >= countPage).collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Set<Genre> genres = library.getBooks().stream().map(Book::getGenre).collect(Collectors.toSet());
        Map<Genre, String> resultMap = library.getArchive().stream()
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(
                        Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())
                )).entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        genreMapEntry -> Objects.requireNonNull(genreMapEntry.getValue().entrySet().stream()
                                .max((secondValue, firstValue) -> {
                                    long temp = secondValue.getValue() - firstValue.getValue();
                                    if (temp == 0) {
                                        return firstValue.getKey().compareTo(secondValue.getKey());
                                    }
                                    return (int) temp;
                                })).get().getKey()));
        return genres.stream()
                .collect(Collectors.toMap(
                        genre -> genre,
                        genre -> resultMap.getOrDefault(genre, AUTHOR_ERROR)
                ));
    }
}
