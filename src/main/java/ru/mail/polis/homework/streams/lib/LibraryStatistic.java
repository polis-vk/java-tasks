package ru.mail.polis.homework.streams.lib;


import ru.mail.polis.homework.objects.RepeatingCharacters;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        return library.getArchive()
                .stream()
                .filter(e -> e.getBook().getGenre() == genre)
                .filter(e -> {
                    if (e.getReturned() == null) {
                        return false;
                    }
                    return new Timestamp(e.getReturned().getTime() - e.getTake().getTime())
                            .after(new Timestamp(5 * 24 * 60 * 60 * 1000));
                })
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().size()
                        )
                )
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= 5)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
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
                .filter(e -> e.getUser() == user)
                .collect(Collectors.groupingBy(e -> e.getBook().getGenre()))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> {
                                    int booksTakenNow = 0;
                                    for (ArchivedData archivedData : e.getValue()) {
                                        if (archivedData.getReturned() == null) {
                                            booksTakenNow++;
                                        }
                                    }
                                    return new RepeatingCharacters.Pair<>(e.getValue().size(), booksTakenNow);
                                }
                        )
                ).entrySet()
                .stream()
                .max((e1, e2) -> {
                    if (e1.getValue().getFirst() > e2.getValue().getFirst()) {
                        return 1;
                    }
                    if (e2.getValue().getFirst() > e1.getValue().getFirst()) {
                        return -1;
                    }
                    if (e1.getValue().getSecond() > e2.getValue().getSecond()) {
                        return 1;
                    }
                    return -1;
                }).get().getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive()
                .stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> {
                                    int booksNotReturnedInTime = 0;
                                    for (ArchivedData archivedData : e.getValue()) {
                                        Timestamp returnTime = archivedData.getReturned();
                                        if (returnTime == null) {
                                            returnTime = new Timestamp(System.currentTimeMillis());
                                        }
                                        if (archivedData.getReturned() == null) {
                                            if (new Timestamp(returnTime.getTime() -
                                                    archivedData.getTake().getTime())
                                                    .after(new Timestamp(30L * 24 * 60 * 60 * 1000))) {
                                                booksNotReturnedInTime++;
                                            }
                                        }
                                    }

                                    return (float) booksNotReturnedInTime / e.getValue().size();
                                }
                        )
                )
                .entrySet()
                .stream()
                .filter(e -> e.getValue() < 0.5)
                .map(Map.Entry::getKey)
                .collect(
                        Collectors.toList()
                );
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
                .collect(
                        Collectors.groupingBy(Book::getGenre,
                                Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue()
                                        .entrySet()
                                        .stream()
                                        .max(Comparator.comparingLong(Map.Entry::getValue)).get().getKey()
                        )
                );
    }
}
