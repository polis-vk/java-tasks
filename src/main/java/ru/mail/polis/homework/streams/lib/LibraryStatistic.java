package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалистом жанра считается пользователь который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     *
     * @param library - данные библиотеки
     * @param genre   - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library.getArchive().stream()
                .filter(x -> x.getBook().getGenre() == genre)
                .filter(x -> x.getReturned() == null |
                        (x.getTake().toLocalDateTime().toLocalDate().plusWeeks(2).compareTo(x.getReturned().toLocalDateTime().toLocalDate())) <= 0)
                .collect(groupingBy(ArchivedData::getUser, mapping(ArchivedData::getBook, toList()))).entrySet().stream()
                .filter(x -> x.getValue().size() >= 5)
                .collect(toMap(Map.Entry::getKey, x -> {
                            int value = x.getValue().stream()
                                    .mapToInt(Book::getPage)
                                    .sum();
                            if (x.getKey().getBook().getGenre() == genre) {
                                value += x.getKey().getReadedPages();
                            }
                            return value;
                        }

                ));
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
                .filter(x -> x.getUser() == user)
                .map(ArchivedData::getBook)
                .map(Book::getGenre)
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream().min((o1, o2) -> {
                    if (o1.getValue().compareTo(o2.getValue()) != 0) {
                        return Long.compare(o2.getValue(), o1.getValue());
                    }
                    Genre curBook = user.getBook().getGenre();
                    if (curBook == o1.getKey()) {
                        return -1;
                    }
                    if (curBook == o2.getKey()) {
                        return 1;
                    }
                    return 0;
                })
                .orElseThrow().getKey();

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
                .collect(groupingBy(ArchivedData::getUser,
                        toMap(ArchivedData::getBook, value -> {
                            if (value.getReturned() == null) {
                                return new Timestamp(System.currentTimeMillis() - value.getTake().getTime());
                            }
                            return new Timestamp(value.getReturned().getTime() - value.getTake().getTime());
                        })
                )).entrySet().stream()
                .filter(map -> {
                    final LocalDateTime month = LocalDateTime.of(1970, Month.FEBRUARY, 1, 0, 0, 0);
                    long bookCountKeepingOverMonth = map.getValue().entrySet().stream()
                            .filter(entry -> entry.getValue().toLocalDateTime().isAfter(month))
                            .count();
                    return bookCountKeepingOverMonth > map.getValue().size() / 2;
                })
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
        return Arrays.stream(Genre.values())
                .collect(toMap(Function.identity(),
                        value -> library.getArchive().stream()
                                .map(ArchivedData::getBook)
                                .filter(book -> book.getGenre() == value)
                                .map(Book::getAuthor)
                                .collect(groupingBy(Function.identity(), counting()))
                                .entrySet().stream()
                                .max((o1, o2) -> {
                                    if (o1.getValue().equals(o2.getValue())) {
                                        return o2.getKey().compareTo(o1.getKey());
                                    }
                                    return Long.compare(o2.getValue(), o1.getValue());
                                })
                                .orElseGet(() -> Map.entry("Author not determined", 0L)).getKey(),
                        (value1, value2) -> value1
                ));
    }
}
