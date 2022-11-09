package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private static final long TIME_PER_DAY = 24 * 60 * 60 * 1000;

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
        final long timeLimit = TIME_PER_DAY * 14;
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre) && bookTimeHolding(archivedData) >= timeLimit)
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.mapping(archivedData -> archivedData.getBook().getPage(), Collectors.toList())))
                .entrySet().stream()
                .filter(pairOfUserAndListWithPages -> pairOfUserAndListWithPages.getValue().size() >= 5)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    int sumOfPages = entry.getValue().stream()
                            .mapToInt(elem -> elem)
                            .sum();
                    sumOfPages += entry.getKey().getBook().getGenre().equals(genre) ? entry.getKey().getReadedPages() : 0;
                    return sumOfPages;
                }));
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
                .filter(archivedData -> archivedData.getUser().equals(user) && archivedData.getReturned() != null)
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre(), Collectors.counting()))
                .entrySet().stream()
                .max((pairWithGenreAndAmount1, pairWithGenreAndAmount2) -> {
                    if (!pairWithGenreAndAmount2.getValue().equals(pairWithGenreAndAmount1.getValue())) {
                        return pairWithGenreAndAmount1.getValue().compareTo(pairWithGenreAndAmount2.getValue());
                    }
                    if (user.getBook().getGenre().equals(pairWithGenreAndAmount1.getKey())) {
                        return 1;
                    } else if (user.getBook().getGenre().equals(pairWithGenreAndAmount2.getKey())) {
                        return -1;
                    }
                    return 0;
                })
                .map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        final long timeLimit = TIME_PER_DAY * 30;
        return library.getArchive().stream()
                .filter(archivedData -> bookTimeHolding(archivedData) > timeLimit)
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.mapping(ArchivedData::getBook, Collectors.toSet())))
                .entrySet().stream()
                .filter(userListEntry -> {
                    int amountBooksOfUserWith30DaysHolding = userListEntry.getValue().size();
                    int amountOfAllBooksForAUser = library.getArchive().stream()
                            .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.mapping(ArchivedData::getBook, Collectors.toSet())))
                            .entrySet().stream()
                            .filter(userListWithBooksEntry -> userListWithBooksEntry.getKey().equals(userListEntry.getKey()))
                            .flatMap(userListWithBooksEntry -> userListWithBooksEntry.getValue().stream())
                            .collect(Collectors.toSet()).size();
                    return amountOfAllBooksForAUser < 2 * amountBooksOfUserWith30DaysHolding;
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
        return library.getBooks().stream()
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        genreAndMapEntry -> genreAndMapEntry.getValue()
                                .entrySet().stream()
                                .max((genreAndMapEntry1, genreAndMapEntry2) -> {
                                    int difference = genreAndMapEntry2.getValue().compareTo(genreAndMapEntry1.getValue());
                                    return difference != 0 ? difference : genreAndMapEntry2.getKey().compareTo(genreAndMapEntry1.getKey());
                                })
                                .map(Map.Entry::getKey).orElse("")
                ));
    }

    private static long bookTimeHolding(ArchivedData data) {
        long currentTime = Timestamp.from(Instant.now()).getTime();
        long startTime = data.getTake().getTime();
        return data.getReturned() != null ? data.getReturned().getTime() - startTime : currentTime - startTime;
    }
}
