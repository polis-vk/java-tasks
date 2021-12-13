package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
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

    private static final int COUNT_BOOKS_FOR_BOOKWORM = 5;
    private static final int COUNT_DAYS_FOR_BOOKWORM = 14;
    private static final int COUNT_DAYS_FOR_UNTRUSTED_USER = 30;

    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        List<ArchivedData> archivedDataList = library.getArchive();
        return library.getUsers().stream()
                .filter(user -> archivedDataList.stream()
                        .filter(archivedData -> archivedData.getUser().equals(user))
                        .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                        .filter(archivedData -> compareDays(archivedData, COUNT_DAYS_FOR_BOOKWORM))
                        .count() >= COUNT_BOOKS_FOR_BOOKWORM)
                .collect(Collectors.toMap(Function.identity(), User::getReadedPages));
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
        Map<Genre, Long> genreFrequency = library.getArchive().stream()
                .filter(archivedData -> archivedData.getUser().equals(user))
                .filter(archivedData -> archivedData.getReturned() != null)
                .collect(Collectors.groupingBy(archiveCell -> archiveCell.getBook().getGenre(), Collectors.counting()));

        Long maxOfGenres = genreFrequency.values().stream()
                .max(Long::compareTo).orElse(null);

        List<Genre> listOfMaxGenres = genreFrequency.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxOfGenres))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return listOfMaxGenres.size() == 1 ? listOfMaxGenres.get(0) :
                listOfMaxGenres.stream()
                        .max(Comparator.comparing(genre ->
                                library.getArchive().stream()
                                        .filter(archivedData -> archivedData.getUser().equals(user))
                                        .filter(archivedData -> archivedData.getReturned() == null)
                                        .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                                        .count())).orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        List<ArchivedData> archiveOfLibrary = library.getArchive();
        return library.getUsers().stream()
                .filter(user -> archiveOfLibrary.stream()
                        .filter(archivedData -> archivedData.getUser().equals(user))
                        .filter(archivedData -> compareDays(archivedData, COUNT_DAYS_FOR_UNTRUSTED_USER))
                        .count() > library.getArchive().stream()
                        .filter(archivedData -> archivedData.getUser().equals(user))
                        .count() / 2
                )
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
        return library.getArchive().stream()
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, genreSet ->
                        Objects.requireNonNull(genreSet.getValue().entrySet().stream()
                                .max(Comparator
                                        .comparingLong(Map.Entry<String, Long>::getValue)
                                        .thenComparing(Map.Entry.comparingByKey()))
                                .orElse(null)).getKey()));
    }

    private boolean compareDays(ArchivedData archivedData, int countDays) {
        Optional<Timestamp> returnedTime = Optional.of(archivedData.getReturned());
        return TimeUnit.MILLISECONDS.toDays(returnedTime.map(Timestamp::getTime).orElseGet(() ->
                System.currentTimeMillis() - archivedData.getTake().getTime()))
                >= countDays;
    }


}
