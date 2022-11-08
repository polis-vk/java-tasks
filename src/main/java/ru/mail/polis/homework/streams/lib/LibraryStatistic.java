package ru.mail.polis.homework.streams.lib;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
        Map<User, Integer> specialistInGenre = new HashMap<>();
        Stream<ArchivedData> archivedData = library.getArchive().stream();
        archivedData.filter(archivedData1 -> archivedData1.getBook().getGenre().equals(genre))
                .filter(archivedData1 -> checkDaysOfReadingBook(archivedData1, 14))
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList()))
                .entrySet().stream().filter(entry -> entry.getValue().size() >= 5)
                .forEach(entry -> specialistInGenre.merge(entry.getKey(), entry.getValue().stream()
                        .map(archivedData1 -> archivedData1.getBook().getPage())
                        .mapToInt(Integer::intValue).sum(), Integer::sum));
        for (Map.Entry<User, Integer> entry : specialistInGenre.entrySet()) {
            if (entry.getKey().getBook().getGenre().equals(genre)) {
                entry.setValue(entry.getValue() + entry.getKey().getReadedPages());
            }
        }
        return specialistInGenre;
    }

    public boolean checkDaysOfReadingBook(ArchivedData archivedData, int days) {
        if (archivedData.getReturned() == null) {
            return ChronoUnit.DAYS.between(archivedData.getTake().toInstant(), Instant.now()) >= days;
        } else
            return ChronoUnit.DAYS.between(archivedData.getTake().toInstant(), archivedData.getReturned().toInstant()) >= days;
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
        Map<Genre, Integer> specialistInGenre = new HashMap<>();
        Stream<ArchivedData> archivedData = library.getArchive().stream();
        archivedData.filter(archivedData1 -> archivedData1.getUser().equals(user))
                .filter(archivedData1 -> archivedData1.getReturned() != null)
                .forEach(archivedData1 -> specialistInGenre.merge(archivedData1.getBook().getGenre(), 1, Integer::sum));
        int maxValueInMap = Collections.max(specialistInGenre.values());
        List<Genre> maxValueKeys = specialistInGenre.entrySet().stream()
                .filter(entry -> entry.getValue() == maxValueInMap)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (maxValueKeys.size() > 1) {
            for (Genre favoriteGenre : maxValueKeys) {
                if (favoriteGenre.equals(user.getBook().getGenre())) {
                    return favoriteGenre;
                }
            }
        }
        return maxValueKeys.get(0);
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
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList()))
                .entrySet().stream().filter(userListEntry -> userListEntry.getValue().stream()
                        .filter(archivedData -> checkDaysOfReadingBook(archivedData, 31))
                        .count() > (userListEntry.getValue().size() / 2))
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
        List<Book> largeBooks = new ArrayList<>();
        Stream<Book> stream = library.getBooks().stream();
        stream.filter(book -> book.getPage() >= countPage)
                .forEach(largeBooks::add);
        return largeBooks;
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, String> mostPopularAuthorInGenre = library.getArchive().stream()
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre(),
                        Collectors.groupingBy(archivedData -> archivedData.getBook().getAuthor(),
                                Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        genreMapEntry -> genreMapEntry.getValue().entrySet().stream()
                                .max((o1, o2) -> {
                                    long compareValue = o2.getValue() - o1.getValue();
                                    if (compareValue == 0) {
                                        return o1.getKey().compareTo(o2.getKey());
                                    }
                                    return (int) compareValue;
                                }).get().getKey()
                ));
        for (Genre genre : Genre.values()) {
            if (!mostPopularAuthorInGenre.containsKey(genre)) {
                mostPopularAuthorInGenre.put(genre, "Author not determined");
            }
        }
        return mostPopularAuthorInGenre;
    }
}
