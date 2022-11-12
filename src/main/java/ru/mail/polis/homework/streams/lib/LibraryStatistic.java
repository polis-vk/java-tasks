package ru.mail.polis.homework.streams.lib;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
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
     * @param library - данные библиотеки
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library.getArchive()
                .stream().filter(archivedData -> countDays(archivedData) >= 14)
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList())).entrySet()
                .stream().filter(userListEntry -> userListEntry.getValue().size() >= 5)
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
        return library.getArchive()
                .stream().filter(archivedData -> user.equals(archivedData.getUser())
                        && user.getBook().getGenre() != archivedData.getBook().getGenre())
                .collect(Collectors.groupingBy(
                        archivedData -> archivedData.getBook().getGenre(),
                        Collectors.counting()
                )).entrySet()
                .stream()
                .max((e1, e2) -> (int) (e1.getValue() - e2.getValue()))
                .orElseThrow(NoSuchElementException::new).getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getUsers()
                .stream().filter(user -> library.getArchive()
                        .stream().filter(archivedData -> archivedData.getUser().equals(user) &&
                                countDays(archivedData) > 14).count() > library.getArchive()
                        .stream().filter(archivedData -> archivedData.getUser().equals(user)).count() / 2)
                .collect(Collectors.toList());
    }

    public int countDays(ArchivedData darchivedData) {
        if (darchivedData.getReturned() == null) {
            return (int) TimeUnit.DAYS.convert(System.currentTimeMillis() - darchivedData.getTake().getTime(), TimeUnit.MILLISECONDS);
        }
        return (int) TimeUnit.DAYS.convert(darchivedData.getReturned().getTime() - darchivedData.getTake().getTime(), TimeUnit.MILLISECONDS);
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     * @param library - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return library.getBooks()
                .stream()
                .filter(book -> book.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return Arrays.stream(Genre.values()).collect(Collectors.toMap(
                genre -> genre, genre -> library.getArchive().stream()
                        .map(ArchivedData::getBook).filter(book -> book.getGenre().equals(genre))
                        .collect(Collectors.groupingBy(Book::getAuthor, Collectors.counting()))
                        .entrySet().stream().sorted(Map.Entry.comparingByKey())
                        .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Author not determined")
        ));
    }
}
