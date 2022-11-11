package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

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
                .filter(archivedData -> countDays(archivedData) >= 14)
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList())).entrySet().stream()
                .filter(userListEntry -> userListEntry.getValue().size() >= 5)
                .collect(Collectors.toMap(Map.Entry:: getKey, userListEntry ->
                        userListEntry.getValue().stream().mapToInt(
                                archivedData -> archivedData.getBook().getPage()).sum() +
                                (userListEntry.getKey().getBook().getGenre().equals(genre) ?
                                userListEntry.getKey().getReadedPages() : 0
                        )));
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
                .collect(Collectors.toMap(
                        archivedData -> archivedData.getBook().getGenre(),
                        archivedData -> archivedData.getReturned() != null ? 1 : 0,
                        Integer::sum)).entrySet().stream()
                .max((o1, o2) ->
                        o1.getValue().equals(o2.getValue()) ?
                                (user.getBook().getGenre() == o1.getKey() ? 1
                                        : user.getBook().getGenre() == o2.getKey() ? -1 : 0)
                                : o1.getValue().compareTo(o2.getValue()))
                .get().getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList())).entrySet().stream()
                .filter(userListEntry -> userListEntry.getValue().stream()
                        .filter(archivedData -> countDays(archivedData) > 30)
                        .count() > (userListEntry.getValue().size() / 2))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     * @param library - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return library.getBooks().stream().filter(book -> book.getPage() >= countPage).collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return library.getBooks().stream()
                .map(Book::getGenre)
                .collect(Collectors.toSet())
                .stream().collect(Collectors.toMap(
                        genre -> genre,
                        genre -> library.getArchive().stream()
                                .map(ArchivedData::getBook)
                                .collect(Collectors.groupingBy(
                                        Book::getGenre,
                                        Collectors.groupingBy(
                                                Book::getAuthor,
                                                Collectors.counting()
                                        )
                                )).entrySet().stream().collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        genreMapEntry -> genreMapEntry.getValue().entrySet().stream()
                                                .max(((o1, o2) ->
                                                        o2.getValue() - o1.getValue() == 0 ?
                                                        o1.getKey().compareTo(o2.getKey())
                                                        : (int) (o2.getValue() - o1.getValue())))
                                                .get().getKey()
                                )).getOrDefault(genre, "Author not determined")
                ));
    }

    private long getTimeInDays(Timestamp data) {
        return TimeUnit.MILLISECONDS.toDays(data.getTime());
    }

    private long countDays(ArchivedData archivedData) {
        return archivedData.getReturned() != null ?
                getTimeInDays(archivedData.getReturned()) - getTimeInDays(archivedData.getTake())
                : getTimeInDays(Timestamp.valueOf(LocalDateTime.now())) - getTimeInDays(archivedData.getTake());
    }
}
