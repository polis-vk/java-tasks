package ru.mail.polis.homework.streams.lib;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
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
     * @param library - данные библиотеки
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .filter(archivedData -> isHoldNDays(archivedData, 14))
                .collect(Collectors.groupingBy(
                        ArchivedData::getUser,
                        Collectors.toList()
                )).entrySet().stream()
                .filter(userListEntry -> userListEntry.getValue().size() >= 5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        userListEntry -> userListEntry.getValue().stream()
                                .mapToInt(archivedData -> archivedData.getBook().getPage())
                                .sum() + (userListEntry.getKey().getBook().getGenre().equals(genre) ?
                                userListEntry.getKey().getReadedPages() : 0)
                ));
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getUser().equals(user) && archivedData.getReturned() != null)
                .collect(Collectors.groupingBy(
                        archivedData -> archivedData.getBook().getGenre(),
                        Collectors.counting()
                )).entrySet().stream()
                .peek(genreLongEntry -> {
                    System.out.println("-----------------------");
                    System.out.println(genreLongEntry.getKey());
                    System.out.println(genreLongEntry.getValue());
                    System.out.println("-----------------------");
                })
                .max((o1, o2) -> {
                    long cmp = o1.getValue() - o2.getValue();
                    if (cmp == 0) {
                        if (user.getBook().getGenre().equals(o1.getKey())) {
                            return 1;
                        }
                        if (user.getBook().getGenre().equals(o2.getKey())) {
                            return -1;
                        }
                    }
                    return (int) cmp;
                }).get().getKey();
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
                .filter(userListEntry -> userListEntry.getValue().stream()
                        .filter(archivedData -> isHoldNDays(archivedData, 31))
                        .count() > (userListEntry.getValue().size() / 2))
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
        return library.getBooks().stream()
                .collect(Collectors.groupingBy(
                        Book::getGenre,
                        Collectors.groupingBy(
                                Book::getAuthor,
                                Collectors.counting()
                        )
                )).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        genreMapEntry -> genreMapEntry.getValue().entrySet().stream()
                                .max((o1, o2) -> {
                                    long cmp = o2.getValue() - o1.getValue();
                                    if (cmp == 0) {
                                        return o1.getKey().compareTo(o2.getKey());
                                    }
                                    return (int) cmp;
                                }).get().getKey()
                ));
    }

    /**
     * Держат ли данную книгу не менее n дней.
     * @param archivedData запись о книге, которую держат.
     * @param n количество дней.
     * @return Держат ли данную книгу не менее n дней.
     */
    private boolean isHoldNDays(ArchivedData archivedData, long n) {
        Temporal from = archivedData.getTake().toInstant();
        return archivedData.getReturned() != null
                ? ChronoUnit.DAYS.between(from, archivedData.getReturned().toInstant()) >= n
                : ChronoUnit.DAYS.between(from, Instant.now()) >= n;
    }
}
