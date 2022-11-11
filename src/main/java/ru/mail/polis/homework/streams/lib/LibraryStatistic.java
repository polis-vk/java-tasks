package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.util.Collections;
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
                .filter(data -> data.getBook().getGenre().equals(genre) &&
                        compareDates(data.getTake(), data.getReturned(), 14) >= 0)
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(pair -> pair.getValue().size() >= 5)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        pair -> {
                            int pages = pair.getValue().stream()
                                    .mapToInt(data -> data.getBook().getPage())
                                    .sum();
                            if (pair.getKey().getBook().getGenre().equals(genre)) {
                                pages += pair.getKey().getReadedPages();
                            }
                            return pages;
                        }));
    }

    private int compareDates(Timestamp from, Timestamp to, int days) {
        long actualDays;
        if (to != null) {
            actualDays = (to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000);
        } else {
            actualDays = (System.currentTimeMillis() - from.getTime()) / (24 * 60 * 60 * 1000);
        }
        return actualDays > days ? 1 : actualDays == days ? 0 : -1;
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        Map<Genre, Long> map = library.getArchive().stream()
                .filter(data -> data.getUser().equals(user) && data.getReturned() != null)
                .collect(Collectors.groupingBy(data -> data.getBook().getGenre(), Collectors.counting()));
        long maxCountOfGenre = Collections.max(map.values());
        List<Genre> list = map.entrySet().stream()
                .filter(pair -> pair.getValue() == maxCountOfGenre)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (list.size() == 1) {
            return list.get(0);
        }
        // Исхожу из того, что если есть два одинаковых по наибольшим весам жанра,
        // то есть книга, которую читает пользователь, и которая относится к одному из этих жанров
        return user.getBook().getGenre();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser,
                        Collectors.partitioningBy(
                                data -> compareDates(data.getTake(), data.getReturned(), 30) > 0)))
                .entrySet().stream()
                .filter(map -> (map.getValue().get(true).size() + map.getValue().get(false).size())
                        / map.getValue().get(true).size() < 2)
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
        Map<Genre, String> map = library.getArchive().stream()
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, pair -> pair.getValue().entrySet().stream()
                        .min(Map.Entry.<String, Long>comparingByKey().thenComparing(Map.Entry.comparingByValue()))
                        .get().getKey()));
        for (Genre genre : Genre.values()) {
            if (!map.containsKey(genre)) {
                map.put(genre, "Author not determined");
            }
        }
        return map;
    }
}
