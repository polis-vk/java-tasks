package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
        return library.getArchive().stream()
                .filter(a -> a.getBook().getGenre().equals(genre) && getDays(a.getTake(), a.getReturned(), 0) >= 14)
                .collect(Collectors.toMap(ArchivedData::getUser, v -> 1, (v1, v2) -> ++v1))
                .entrySet().stream().filter(el -> el.getValue() >= 5)
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getKey().getReadedPages()));
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
        return library.getArchive().stream().filter(a -> a.getUser().equals(user))
                .collect(Collectors.toMap(k -> k.getBook().getGenre(), v -> 1, (v1, v2) -> ++v1))
                .entrySet().stream().max((a, b) -> {
                    if (a.getValue().equals(b.getValue())) {
                        return (a.getKey().equals(user.getBook().getGenre())) ? 1 : -1;
                    }
                    return a.getValue().compareTo(b.getValue());
                }).map(Map.Entry::getKey).orElse(null);
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
                .collect(Collectors.toMap(ArchivedData::getUser,
                        ad -> Stream.of(getDays(ad.getTake(), ad.getReturned(), 31)).collect(Collectors.toList()),
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        }
                )).entrySet().stream()
                .filter(el -> el.getValue().stream().filter(it -> it > 30).count() * 2 > el.getValue().size())
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
        return library.getBooks().stream().filter(b -> b.getPage() >= countPage).collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return null;
    }

    private long getDays(Timestamp take, Timestamp returned, long val) {
        if (returned.equals(null)) {
            return val;
        }
        return TimeUnit.DAYS.convert(returned.getTime() - take.getTime(), TimeUnit.DAYS);
    }
}
