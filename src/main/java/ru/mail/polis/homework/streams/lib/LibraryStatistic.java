package ru.mail.polis.homework.streams.lib;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    private static final int BOOKS_REQUIREMENT = 5;
    private static final int DAYS_REQUIREMENT = 14;
    private static final int DAYS_UNRELIABLE = 30;
    private static final String AUTHOR_NOT_DETERMINED = "Author not determined";

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
            .filter(data -> data.getBook().getGenre().equals(genre) && data.getReturned() != null)
            .filter(data -> isBorrowedInDays(data, DAYS_REQUIREMENT))
            .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList()))
            .entrySet().stream().filter(user -> user.getValue().size() >= BOOKS_REQUIREMENT)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                userEntry -> (userEntry.getKey().getBook().getGenre().equals(genre) ?
                    userEntry.getKey().getReadedPages() : 0) + (userEntry.getValue().stream()
                    .mapToInt(data -> data.getBook().getPage()).sum())
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
            .filter(data -> data.getUser().equals(user) && data.getReturned() != null)
            .collect(Collectors.groupingBy(data -> data.getBook().getGenre()))
            .entrySet().stream()
            .max((entry1, entry2) -> {
                if (entry1.getValue().size() == entry2.getValue().size()) {
                    return (user.getBook().getGenre().equals(entry1.getKey()) ? 1 : 0) -
                        (user.getBook().getGenre().equals(entry2.getKey()) ? 1 : 0);
                }
                return entry1.getValue().size() - entry2.getValue().size();
            }).orElseThrow(IllegalStateException::new).getKey();
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
                Collectors.toList()
            ))
            .entrySet().stream()
            .filter(entry -> entry.getValue().stream()
                .filter(data -> isBorrowedInDays(data, DAYS_UNRELIABLE + 1))
                .count() > entry.getValue().size() / 2)
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
            .map(Book::getGenre)
            .distinct()
            .collect(Collectors.toMap(
                genre -> genre,
                genre -> library.getArchive().stream()
                    .map(ArchivedData::getBook)
                    .collect(Collectors.groupingBy(
                        Book::getGenre,
                        Collectors.groupingBy(
                            Book::getAuthor,
                            Collectors.counting()
                        )))
                    .entrySet().stream()
                    .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        genreMapEntry -> genreMapEntry.getValue().entrySet().stream()
                            .max((entry1, entry2) -> {
                                if (entry1.getValue().equals(entry2.getValue())) {
                                    return entry2.getKey().compareTo(entry1.getKey());
                                }
                                return (int) (entry1.getValue() - entry2.getValue());
                            }).orElseThrow(IllegalStateException::new).getKey()
                    )).getOrDefault(genre, AUTHOR_NOT_DETERMINED)
            ));
    }

    /**
     * Держат ли книгу не менее указанного количество дней
     *
     * @param archivedData - данные в архиве
     * @param days - количество дней
     * @return - true, если книгу держат не менее указанного количества дней, false - если нет
     */
    private boolean isBorrowedInDays(ArchivedData archivedData, int days) {
        long returned = archivedData.getReturned() != null ?
            archivedData.getReturned().getTime() : System.currentTimeMillis();
        return TimeUnit.DAYS.convert(returned -
            archivedData.getTake().getTime(), TimeUnit.MILLISECONDS) >= days;
    }
}
