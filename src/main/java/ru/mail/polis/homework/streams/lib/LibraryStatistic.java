package ru.mail.polis.homework.streams.lib;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
        Map<User, Integer> specialistInGenre = new HashMap<>();
        Stream<ArchivedData> archivedData = library.getArchive().stream();
        archivedData.filter(x -> x.getBook().getGenre().equals(genre))
                .filter(x -> x.getBook().getPage() == x.getUser().getReadedPages())
                .filter(x -> checkDaysOfReadingBook(x, 14) == true)
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList()))
                .entrySet().stream().filter(s -> s.getValue().size() >=5)
                .forEach(s -> specialistInGenre.merge(s.getKey(), s.getKey().getReadedPages(), Integer::sum));
        return specialistInGenre;
    }
    public boolean checkDaysOfReadingBook(ArchivedData archivedData, int days){
        if (archivedData.getReturned() == null){
            return ChronoUnit.DAYS.between(archivedData.getTake().toInstant(), Instant.now()) >= days;
        }
        else return ChronoUnit.DAYS.between(archivedData.getTake().toInstant(),archivedData.getReturned().toInstant()) >= days;
    }
    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        Map<Genre, Integer> specialistInGenre = new HashMap<>();
        Stream<ArchivedData> archivedData = library.getArchive().stream();
        archivedData.filter(x -> x.getUser().equals(user))
                .filter(x -> x.getReturned() != null)
                .filter(x -> x.getBook().getPage() == x.getUser().getReadedPages())
                .forEach(s -> specialistInGenre.merge(s.getBook().getGenre(), 1, Integer::sum));
        int maxValueInMap = Collections.max(specialistInGenre.values());
        List<Genre> maxValueKeys = specialistInGenre.entrySet().stream()
                .filter(entry -> entry.getValue() == maxValueInMap)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (maxValueKeys.size() > 1) {
            for (Genre favoriteGenre : maxValueKeys) {
                if (favoriteGenre.equals(user.getBook().getGenre())) {
                    return favoriteGenre;
                }
            }
        }
        return maxValueKeys.get(0);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        List<User> unreliableUsers = library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList()))
                .entrySet().stream().filter(userListEntry -> userListEntry.getValue().stream()
                        .filter(archivedData -> checkDaysOfReadingBook(archivedData, 31) == true)
                        .count() > (userListEntry.getValue().size() / 2))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return unreliableUsers;
    }
    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     * @param library - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        List<Book> largeBooks = new ArrayList<>();
        Stream<Book> stream = library.getBooks().stream();
        stream.filter(x -> x.getPage() > countPage)
                .forEach(s -> largeBooks.add(s));
        return largeBooks;
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, String> popularAuthorInGenre = library.getBooks().stream()
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(
                Map.Entry::getKey,
                genreMapEntry -> genreMapEntry.getValue().entrySet().stream()
                        .max((o1, o2) -> {
                            long cmp = o2.getValue() - o1.getValue();
                            if (cmp == 0) {
                                return o1.getKey().compareTo(o2.getKey());
                            }
                            return (int) cmp;
                        }).get().getKey()
        ));


        return popularAuthorInGenre;
    }
}
