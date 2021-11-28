package ru.mail.polis.homework.streams.lib;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(
                        ArchivedData::getUser,
                        Collectors.mapping(ArchivedData::getBook, toList())
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 4)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .mapToInt(Book::getPage).sum())
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
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getUser().equals(user))
                .collect(Collectors.groupingBy(
                        archivedData -> archivedData.getBook().getGenre(),
                        Collectors.summingDouble(this::countGenres)
                ))
                .entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
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
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(
                        ArchivedData::getUser,
                        Collectors.mapping(archivedData -> archivedData, toList())
                ))
                .entrySet().stream()
                .filter(entry -> isUnreliable(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(toList());
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
                .collect(toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(
                        archivedData -> archivedData.getBook().getGenre(),
                        Collectors.groupingBy(
                                data -> data.getBook().getAuthor(),
                                Collectors.counting()
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().entrySet().stream()
                                .max(Comparator.comparingLong(Map.Entry::getValue)).get().getKey()
                ));
    }

    private boolean isUnreliable(List<ArchivedData> archivedDataList) {
        return archivedDataList.stream().mapToInt(archivedData -> {
            long returned;

            if (archivedData.getReturned() == null) {
                returned = new Date().getTime();
            } else {
                returned = archivedData.getReturned().getTime();
            }

            if (TimeUnit.MILLISECONDS.toDays(returned - archivedData.getTake().getTime()) > 30) {
                return 1;
            }

            return 0;
        }).sum() > archivedDataList.size() / 2;
    }

    private double countGenres(ArchivedData archivedData) {
        if (archivedData.getReturned() == null) {
            return 0.5;
        }

        return 1.0;
    }
}
