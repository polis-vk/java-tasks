package ru.mail.polis.homework.streams.lib;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private final static int EXPERT_READING_TIME = 14;
    private final static int EXPERT_READ_BOOKS = 5;
    private final static int DEBTOR_READING_TIME = 30;
    private final static double DEBT_RATIO = 0.5;


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
                .filter(archivedData -> archivedData.getReturned() != null)
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(ArchivedData::getUser,
                        Collectors.groupingBy(ArchivedData::getBook,
                                Collectors.summingInt(LibraryStatistic::calculateDays))))
                .entrySet().stream()
                .filter(userToBooks -> userToBooks.getValue().keySet().size() >= EXPERT_READ_BOOKS)
                .filter(userToBooks -> userToBooks.getValue().entrySet().stream()
                        .allMatch(bookToDays -> bookToDays.getValue() >= EXPERT_READING_TIME))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(userToBooks ->
                        userToBooks.getValue().keySet().stream().mapToInt(Book::getPage).sum())));
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
                .collect(Collectors.groupingBy(data -> data.getBook().getGenre()))
                .entrySet().stream()
                .max((o1, o2) -> compareGenresByBooks(o1.getValue(), o2.getValue()))
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
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(entry -> {
                    int numberOfBooks = entry.getValue().size();
                    int numberOfDelayed = (int) entry.getValue().stream()
                            .filter(data -> calculateDays(data) >= DEBTOR_READING_TIME).count();
                    return (double) numberOfDelayed / numberOfBooks > DEBT_RATIO;
                })
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
        return library.getArchive().stream()
                .map(ArchivedData::getBook)
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
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        authorToBooks -> authorToBooks.getValue().entrySet().stream()
                                .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue)
                                        .thenComparing(Map.Entry.comparingByKey())).get().getKey()));
    }

    private static int calculateDays(ArchivedData data) {
        long from = data.getTake().getTime();
        long to = data.getReturned() == null ? System.currentTimeMillis() : data.getReturned().getTime();
        return (int) (from - to) / (1000 * 60 * 60 * 24);
    }

    private static int compareGenresByBooks(List<ArchivedData> data1, List<ArchivedData> data2) {
        boolean containsUnreturned1 = data1.stream().anyMatch(data -> data.getReturned() == null);
        boolean containsUnreturned2 = data2.stream().anyMatch(data -> data.getReturned() == null);
        int books1 = data1.size();
        int books2 = data2.size();
        int onlyReturned1 = containsUnreturned1 ? books1 - 1 : books1;
        int onlyReturned2 = containsUnreturned2 ? books2 - 1 : books2;
        int onlyReturnedDifference = onlyReturned1 - onlyReturned2;
        return onlyReturnedDifference != 0 ? onlyReturnedDifference : books1 - books2;
    }

}
