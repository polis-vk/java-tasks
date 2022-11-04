package ru.mail.polis.homework.streams.lib;

import java.util.*;
import java.util.concurrent.TimeUnit;
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
        return library.getArchive().stream().filter(archive ->
                        archive.getBook().getGenre() == genre &&
                                archive.getReturned() != null &&
                                getDays(archive.getReturned().getTime() - archive.getTake().getTime()) >= 14
                ).collect(
                        Collectors.groupingBy(
                                ArchivedData::getUser,
                                Collectors.mapping(
                                        archive -> archive.getBook().getPage(),
                                        Collectors.toList()
                                )
                        )
                ).entrySet().stream().filter(entry -> entry.getValue().size() >= 5)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().stream().mapToInt(elem -> elem).sum()
                        )
                );
    }

    private int getDays(long timestamp) {
        return (int) TimeUnit.MILLISECONDS.toDays(timestamp);
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
        return Objects.requireNonNull(
                library.getArchive().stream().filter(archivedData -> archivedData.getUser() == user)
                        .collect(
                                Collectors.groupingBy(
                                        archive -> archive.getUser().getBook().getGenre(),
                                        Collectors.counting()
                                )
                        ).entrySet().stream().max((o1, o2) -> {
                                    if (o1.getValue() > o2.getValue()) {
                                        return 1;
                                    } else if (o1.getValue() < o2.getValue()) {
                                        return -1;
                                    } else {
                                        Genre userGenre = user.getBook().getGenre();
                                        if (o1.getKey() == userGenre && o2.getKey() == userGenre) {
                                            return 0;
                                        } else if (o1.getKey() == userGenre) {
                                            return 1;
                                        } else {
                                            return -1;
                                        }
                                    }
                                }
                        ).orElse(null)).getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream().filter(archive ->
                archive.getReturned() == null ||
                        getDays(archive.getReturned().getTime() - archive.getTake().getTime()) > 30
        ).map(ArchivedData::getUser).collect(Collectors.toList());
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     *
     * @param library   - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return library.getBooks().stream().filter(book -> book.getPage() >= countPage).collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return library.getArchive().stream().map(ArchivedData::getBook)
                .collect(
                        Collectors.groupingBy(
                                Book::getGenre,
                                Collectors.groupingBy(
                                        Book::getAuthor,
                                        Collectors.counting()
                                )
                        )
                ).entrySet().stream().collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> Objects.requireNonNull(entry.getValue().entrySet().stream()
                                        .max((o1, o2) -> {
                                                    if (o1.getValue() > o2.getValue()) {
                                                        return 1;
                                                    } else if (o1.getValue() < o2.getValue()) {
                                                        return -1;
                                                    } else {
                                                        return o1.getKey().compareTo(o2.getKey());
                                                    }
                                                }
                                        ).orElse(null)).getKey()
                        )
                );
    }
}
