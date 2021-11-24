package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<ArchivedData> filteredByGenreList = library.getArchive().stream()
                .filter(user -> user.getBook().getGenre().equals(genre))
                .collect(Collectors.toList());
        return filteredByGenreList.stream()
                .filter(user -> countBooksWhichWereReadFor14OrMoreDays(user.getUser(), filteredByGenreList) >= 5)
                .collect(Collectors.toMap(ArchivedData::getUser, user -> user.getBook().getPage()));
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        Stream<ArchivedData> ads1 = library.getArchive().stream()
                .filter(data -> data.getReturned() != null);
        Stream<Map.Entry<Genre, Integer>> ads2 = ads1.collect(Collectors.toMap(data -> data.getBook().getGenre(),
                data -> countBooksInDefinedGenre(ads1, data.getBook().getGenre())))
                .entrySet().stream()
                    .sorted((v1, v2) -> v2.getValue().compareTo(v1.getValue()));
        Stream<Map.Entry<Genre, Integer>> finalStream = ads2
                .takeWhile(pair -> pair.getValue().equals(ads2.findAny().get().getValue()));

        if (hasGenre(finalStream, user.getBook().getGenre())) {
            return user.getBook().getGenre();
        } else {
            return finalStream.findAny().get().getKey();
        }
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 14-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser)).entrySet().stream()
                .filter(user -> isUnreliableUser(user.getValue()))
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
                .collect(Collectors.groupingBy(Book::getGenre)).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, pair -> findMostPopularAuthor(pair.getValue())));
    }

    private long countBooksWhichWereReadFor14OrMoreDays(User u, List<ArchivedData> l) {
        return l.stream()
                .filter(record -> record.getUser().equals(u)
                        && readAllBook(record)
                        && (elapsedNDays(record.getTake(), record.getReturned(), 14) >= 0))
                .count();
    }

    private boolean readAllBook(ArchivedData ad) {
        return ad.getUser().getReadedPages() == ad.getBook().getPage();
    }

    // Returns timestamps' comparing like typical 'comparing' operation
    private int elapsedNDays(Timestamp start, Timestamp end, int N) {
        Timestamp _end = end;
        if (_end == null) {
            _end = Timestamp.valueOf(LocalDateTime.now());
        }
        int day1 = Integer.parseInt(start.toString().substring(8, 9));
        int day2 = Integer.parseInt(_end.toString().substring(8, 9));
        int result = Math.abs(day1 - day2);
        return Integer.compare(result, N);
    }

    private int countBooksInDefinedGenre(Stream<ArchivedData> ads, Genre genre) {
        return ads.mapToInt(ad -> (genre.equals(ad.getBook().getGenre())) ? 1 : 0).sum();
    }

    private boolean hasGenre(Stream<Map.Entry<Genre, Integer>> ads, Genre genre) {
        return ads.filter(ad -> ad.getKey().equals(genre)).count() == 1;
    }

    private String findMostPopularAuthor(List<Book> books) {
        return books.stream()
                .collect(Collectors.toMap(Book::getAuthor, book -> Collections.frequency(books, book.getAuthor())))
                .entrySet().stream()
                .sorted(this::compareAuthors)
                .findAny().get().getKey();
    }

    private int compareAuthors(Map.Entry<String, Integer> author1, Map.Entry<String, Integer> author2) {
        int comparing1 = author1.getValue().compareTo(author2.getValue());
        if (comparing1 != 0) {
            return comparing1;
        }
        return author1.getKey().compareTo(author2.getKey());
    }

    private boolean isUnreliableUser(List<ArchivedData> data) {
        long nOverdues = data.stream()
                .filter(d -> elapsedNDays(d.getTake(), d.getReturned(), 14) > 0)
                .count();
        return (double)nOverdues/data.size() > 0.5;
    }

}
