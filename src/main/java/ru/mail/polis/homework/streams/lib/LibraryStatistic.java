package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private final static int EXPERT_DAY_NORM = 14;
    private final static int EXPERT_BOOK_NORM = 5;

    private final static int RELIABILITY_DAYS_NORM = 30;
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
                .filter(archivedData -> archivedData.getBook().getGenre() == genre)
                .filter(archivedData -> {
                    LocalDateTime twoWeeksAfterTake = archivedData.getTake().toLocalDateTime().plusDays(EXPERT_DAY_NORM);
                    return archivedData.getReturned().toLocalDateTime().isEqual(twoWeeksAfterTake) ||
                            archivedData.getReturned().toLocalDateTime().isAfter(twoWeeksAfterTake);
                })
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(books -> books.getValue().size() >= EXPERT_BOOK_NORM)
                .collect(Collectors.toMap(Map.Entry::getKey, archivedData ->
                        (archivedData.getKey().getBook().getGenre() == genre ?
                                archivedData.getKey().getReadedPages() : 0)
                                + archivedData.getValue().stream()
                                .map(book -> book.getBook().getPage())
                                .mapToInt(pages -> pages).sum()));
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
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre(), Collectors.counting()))
                .entrySet().stream()
                .max((genre1, genre2) -> {
                    if (genre1.getValue() - genre2.getValue() != 0) {
                        return (int) (genre1.getValue() - genre2.getValue());
                    }

                    Genre notReturnedGenre = library.getUsers().stream()
                            .filter(reader -> reader == user)
                            .findFirst().get().getBook().getGenre();

                    if (notReturnedGenre.equals(genre1.getKey())) {
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
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(userListEntry -> userListEntry.getValue().stream()
                        .filter(archivedData -> archivedData.getReturned() != null
                                && archivedData.getReturned().toLocalDateTime()
                                .isAfter(archivedData.getTake().toLocalDateTime().plusDays(RELIABILITY_DAYS_NORM))
                                || archivedData.getReturned() == null
                                && LocalDateTime.now()
                                .isAfter(archivedData.getTake().toLocalDateTime().plusDays(RELIABILITY_DAYS_NORM)))
                        .count() > userListEntry.getValue().size() / 2)
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
        Map<Genre, String> mapOfPopularity = library.getArchive().stream()
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre(),
                        Collectors.groupingBy(archivedData -> archivedData.getBook().getAuthor(), Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, genreMapEntry -> genreMapEntry.getValue().entrySet().stream()
                        .max(Map.Entry.<String,Long>comparingByValue().reversed().thenComparing(Map.Entry::getKey))
                        .map(Map.Entry::getKey)
                        .get()
                ));

        for (Genre genre : Genre.values()) {
            if (!mapOfPopularity.containsKey(genre)) {
                mapOfPopularity.put(genre, "Author not determined");
            }
        }

        return mapOfPopularity;
    }
}
