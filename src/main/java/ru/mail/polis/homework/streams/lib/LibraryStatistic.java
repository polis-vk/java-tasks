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

    private final int SPECIALIST_COUNT_OF_BOOKS = 5;
    private final int SPECIALISTS_DAYS_OF_READING_BOOK = 13;
    private final int DAYS_FOR_UNRELIABLE_USERS = 30;

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
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre)
                && archivedData.getReturned() != null
                && isMoreThanNDays(archivedData, SPECIALISTS_DAYS_OF_READING_BOOK))
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= SPECIALIST_COUNT_OF_BOOKS)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                            int sum = library.getArchive().stream()
                                    .filter(archived -> archived.getUser().equals(entry.getKey()) && archived.getBook().getGenre().equals(genre))
                                    .map(x -> x.getBook().getPage())
                                    .reduce(0, Integer::sum);
                            if (entry.getKey().getBook().getGenre().equals(genre)) {
                                return sum + entry.getKey().getReadedPages();
                            }
                            return sum;
                        }));
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
                .filter(archivedData -> archivedData.getUser().equals(user) && archivedData.getReturned() != null)
                .collect(Collectors.groupingBy(arch -> arch.getBook().getGenre(), Collectors.counting()))
                .entrySet().stream()
                .max((entry1, entry2) -> {
                    // Если один любимый жанр
                    if (entry1.getValue() - entry2.getValue() != 0) {
                        return (int) (entry1.getValue() - entry2.getValue());
                    }
                    // Находим жанр, который сейчас читает пользователь
                    Genre genreOfNotReturnedBook = library.getUsers().stream()
                            .filter(x -> x.equals(user))
                            .findFirst().get().getBook().getGenre();

                    if (genreOfNotReturnedBook.equals(entry2.getKey())) {
                        entry2.setValue(entry2.getValue() + 1);
                    }
                    if (genreOfNotReturnedBook.equals(entry1.getKey())) {
                        entry1.setValue(entry1.getValue() + 1);
                    }
                    return (int) (entry1.getValue() - entry2.getValue());
                }).get().getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getUsers().stream()
                .collect(Collectors.toMap(user -> user, user -> {
                    long countOfNotReturnedInTime = library.getArchive().stream()
                            .filter(x -> x.getUser().equals(user) && isMoreThanNDays(x, DAYS_FOR_UNRELIABLE_USERS))
                            .count();
                    long countOfAll = library.getArchive().stream()
                            .filter(x -> x.getUser().equals(user))
                            .count();
                    return (countOfNotReturnedInTime * 1.0) / countOfAll;
                }))
                .entrySet().stream()
                .filter(user -> user.getValue() > 0.5)
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
        return library.getBooks().stream()
                .collect(Collectors.groupingBy(Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, genreMapEntry -> genreMapEntry.getValue().entrySet().stream()
                            .max(Map.Entry.<String,Long>comparingByValue(Comparator.reverseOrder())
                                    .thenComparing(Map.Entry::getKey))
                            .map(Map.Entry::getKey)
                            .orElse("")
                ));
    }

    private boolean isMoreThanNDays(ArchivedData archivedData, int nDays) {
        final long MILLIS_PER_DAY = 1000 * 60 * 60 * 24;

        if (archivedData.getReturned() == null) {
            return (System.currentTimeMillis() - archivedData.getTake().getTime()) / MILLIS_PER_DAY > nDays;
        }
        return (archivedData.getReturned().getTime() - archivedData.getTake().getTime()) / MILLIS_PER_DAY > nDays;
    }
}
