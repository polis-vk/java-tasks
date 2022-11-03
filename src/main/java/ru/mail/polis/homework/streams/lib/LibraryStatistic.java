package ru.mail.polis.homework.streams.lib;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private static final int READING_DAYS_FOR_EXPERT = 14;
    private static final int COUNT_OF_BOOKS_FOR_EXPERT = 5;
    private static final int MONTH = 30;
    private static final int TRANSFER_COEFFICIENT = 60 * 60 * 24 * 100;

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
                .filter(archive -> archive.getReturned() != null)
                .filter(archive -> archive.getBook().getGenre().equals(genre))
                .filter(arch -> ((arch.getReturned().getTime() - arch.getTake().getTime()) / (TRANSFER_COEFFICIENT)) > READING_DAYS_FOR_EXPERT)
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(archiveMap -> archiveMap.getValue().size() >= COUNT_OF_BOOKS_FOR_EXPERT)
                .collect(Collectors.toMap(Map.Entry::getKey, value -> value.getValue().size()));
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
                .filter(archivedData -> archivedData.getUser().equals(user))
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .filter(LibraryStatistic::isMoreThanMonth)
                .map(ArchivedData::getUser)
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
        return library.getArchive().stream()
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, genreMap -> genreMap.getValue().entrySet().stream()
                        .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue)
                                .thenComparing(Map.Entry.comparingByKey())).map(Map.Entry::getKey).orElse("")));
    }

    private static boolean isMoreThanMonth(ArchivedData a) {
        return (a.getReturned() == null
                && ((System.currentTimeMillis() - a.getTake().getTime()) / (TRANSFER_COEFFICIENT)) > MONTH)
                || ((a.getReturned().getTime() - a.getTake().getTime()) / (TRANSFER_COEFFICIENT)) > MONTH;
    }
}
