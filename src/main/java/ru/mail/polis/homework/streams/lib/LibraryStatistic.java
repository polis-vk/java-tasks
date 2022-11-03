package ru.mail.polis.homework.streams.lib;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private static final int SPECIALIST_MIN_BOOKS = 5;
    private static final int SPECIALIST_MIN_DAYS = 14;
    private static final int READING_DAYS_LIMIT = 30;

    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалист жанра считается пользователь который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     * @param library - данные библиотеки
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library.getUsers().stream()
                .filter(user -> library.getArchive().stream()
                        .filter(data -> data.getUser().equals(user)
                                && data.getBook().getGenre().equals(genre)
                                && getReadingDays(data) >= SPECIALIST_MIN_DAYS)
                        .count() >= SPECIALIST_MIN_BOOKS)
                .collect(Collectors.toMap(Function.identity(), User::getReadedPages));
    }

    public int getReadingDays(ArchivedData data) {
        if (data.getReturned() == null) {
            return (int) TimeUnit.DAYS.convert(System.currentTimeMillis() - data.getTake().getTime(), TimeUnit.MILLISECONDS);
        }
        return (int) TimeUnit.DAYS.convert(data.getReturned().getTime() - data.getTake().getTime(), TimeUnit.MILLISECONDS);
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
                .filter(archive -> archive.getUser().equals(user))
                .collect(Collectors.groupingBy(data -> data.getBook().getGenre(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getUsers().stream()
                .filter(user -> library
                        .getArchive().stream()
                        .filter(data -> data.getUser().equals(user)
                                && getReadingDays(data) > READING_DAYS_LIMIT)
                        .count() > library.getArchive().stream()
                        .filter(data -> data.getUser().equals(user))
                        .count() / 2)
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
                .collect(Collectors.groupingBy(Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        genreMapEntry -> genreMapEntry
                                .getValue()
                                .entrySet()
                                .stream()
                                .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue)
                                        .thenComparing(Map.Entry.comparingByKey()))
                                .get()
                                .getKey()));
    }
}
