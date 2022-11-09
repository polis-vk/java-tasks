package ru.mail.polis.homework.streams.lib;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    private static final int DAYS_FOR_SPECIALIST = 14;
    private static final int BOOKS_COUNT_FOR_SPECIALIST = 5;
    private static final int DAYS_COUNT_TO_READ = 30;

    /**
     * Вернуть "специалистов" в литературном жанре с кол-вом прочитанных страниц.
     * Специалистом жанра считается пользователь, который прочел как минимум 5 книг в этом жанре,
     * при этом читал каждую из них не менее 14 дней.
     * @param library - данные библиотеки
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .filter(archivedData -> archivedData.getReturned() != null)
                .filter(archivedData -> {
                    long difference = archivedData.getReturned().getTime() - archivedData.getTake().getTime();
                    return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS) >= DAYS_FOR_SPECIALIST;
                })
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(userListEntry -> userListEntry.getValue().size() >= BOOKS_COUNT_FOR_SPECIALIST)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        userListEntry -> {
                            int readedPages = 0;
                            if (userListEntry.getKey().getBook().getGenre().equals(genre)) {
                                readedPages = userListEntry.getKey().getReadedPages();
                            }
                            return userListEntry.getValue().stream()
                                    .mapToInt(value -> value.getBook().getPage()).sum() + readedPages;
                        }
                ));
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот, что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getUser().equals(user))
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre()))
                .entrySet().stream()
                .max((entry1, entry2) -> {
                    int readedBooksCount1 = (int) entry1.getValue().stream()
                            .filter(archivedData -> archivedData.getReturned() != null)
                            .count();
                    int readedBooksCount2 = (int) entry2.getValue().stream()
                            .filter(archivedData -> archivedData.getReturned() != null)
                            .count();
                    int difference = readedBooksCount1 - readedBooksCount2;
                    int differenceWithNotReturned = difference != 0 ? difference :
                            entry1.getValue().size() - entry2.getValue().size();
                    if (differenceWithNotReturned == 0) {
                        if (user.getBook().getGenre().equals(entry1.getKey())) {
                            return 1;
                        } else if (user.getBook().getGenre().equals(entry2.getKey())) {
                            return -1;
                        }
                    }
                    return differenceWithNotReturned;
                }).get().getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(userListEntry -> userListEntry.getValue().stream()
                        .filter(archivedData -> {
                            long difference;
                            if (archivedData.getReturned() == null) {
                                difference = new Date().getTime() - archivedData.getTake().getTime();
                            } else {
                                difference = archivedData.getReturned().getTime() - archivedData.getTake().getTime();
                            }
                            return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS) > DAYS_COUNT_TO_READ;
                        }).count() > userListEntry.getValue().size() / 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть список книг, у которых страниц равно или больше, чем переданное значение
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
        return Arrays.stream(Genre.values())
                .collect(Collectors.toMap(
                        genre -> genre,
                        genre -> library.getArchive().stream()
                                .map(ArchivedData::getBook)
                                .filter(book -> book.getGenre().equals(genre))
                                .collect(Collectors.groupingBy(Book::getAuthor, Collectors.counting()))
                                .entrySet().stream()
                                .max((entry1, entry2) -> {
                                    int difference = (int) (entry1.getValue() - entry2.getValue());
                                    return difference != 0 ? difference : -entry1.getKey().compareTo(entry2.getKey());
                                })
                                .map(Map.Entry::getKey)
                                .orElse("Author not determined")
                ));
    }
}
