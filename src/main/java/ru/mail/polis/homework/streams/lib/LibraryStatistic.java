package ru.mail.polis.homework.streams.lib;

import java.security.KeyStore;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import ru.mail.polis.homework.streams.store.Item;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    private static final int SPECIALIST_READING_DAYS = 14;
    private static final int SPECIALIST_MIN_BOOKS_COUNT = 5;
    private static final int UNRELIABLE_USERS_READING_DAYS = 30;

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
        //O(n^2)
        Map<User, Map<Book, Integer>> userReadingDuration = library.getArchive()     //Составляем таблицу: пользователь,
                //таблица с прочитанными книгами и количеством дней на каждую книгу
                .stream().filter((record) -> record.getBook().getGenre().equals(genre))
                .collect(Collectors.toMap(ArchivedData::getUser, record -> {
                            Map<Book, Integer> newMap = new HashMap<Book, Integer>();
                            newMap.put(record.getBook(), findDuration(record));
                            return newMap;
                        },
                        (existing, replacement) -> {
                            Map<Book, Integer> newMap = new HashMap<Book, Integer>(existing);
                            replacement.forEach(
                                    (book, count) -> newMap.merge(book, count, Integer::sum)
                            );
                            return newMap;
                        }));

        //O(n^2)
        userReadingDuration.forEach((user, map) -> { //Удаляем из таблиц пользователей все книги, которые читали менее 14 дней
            //Сразу это сделать было нельзя, потому что одну и ту же книгу могли брать несколько раз
            map.entrySet().removeIf(entry -> entry.getValue() < SPECIALIST_READING_DAYS);
        });

        //O(n)
        Set<User> specialists = library.getUsers().stream()                     //Формируем множество специалистов
                .filter((record) -> record.getBook().getGenre().equals(genre))
                .filter(user -> {
                    if (!userReadingDuration.containsKey(user)) {
                        return false;
                    }
                    return (userReadingDuration.get(user).size() >= SPECIALIST_MIN_BOOKS_COUNT);
                })
                .collect(Collectors.toSet());

        //O(n)
        return library.getUsers().stream().filter(specialists::contains)
                .collect(Collectors.toMap(Function.identity(), User::getReadPages, Integer::sum));
    }

    private int findDuration(ArchivedData data) {
        if (data.getReturned() == null) {
            return (int) (System.currentTimeMillis() - data.getTake().getTime()) / (1000 * 60 * 60 * 24);
        }
        return (int) (data.getReturned().getTime() - data.getTake().getTime()) / (1000 * 60 * 60 * 24);
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
        //O(n)
        Map<Genre, Long> genreStats = library.getArchive().stream()
                .filter((record) -> (record.getUser().equals(user)) && (record.getReturned() != null))
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.counting()));

        //O(n)
        long maxPopularity = Collections.max(genreStats.values());
        List<Genre> lovedGenres = genreStats.entrySet().stream().filter(entry -> entry.getValue()
                .equals(maxPopularity)).map(Map.Entry::getKey).collect(Collectors.toList());

        if (lovedGenres.size() == 1) {
            return lovedGenres.get(0);
        }

        //O(n)
        Optional<Genre> onHandBookGenre = library.getArchive().stream()
                .filter(record -> record.getReturned() == null).map(record -> record.getBook().getGenre()).findAny();
        if (onHandBookGenre.isPresent() && lovedGenres.contains(onHandBookGenre.get())) {
            return onHandBookGenre.get();
        } else if (lovedGenres.size() != 0) {
            return lovedGenres.get(0);
        }
        return null;
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        //O(n)
        Map<User, UserStats> usersStats = library.getArchive().stream().collect(Collectors.toMap(ArchivedData::getUser,
                record -> {
                    if (findDuration(record) > UNRELIABLE_USERS_READING_DAYS) {
                        return new UserStats(1, 1);
                    }
                    return new UserStats(0, 1);
                }, UserStats::merge));

        return usersStats.entrySet().stream().filter(entry -> entry.getValue().isUnreliable())
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    private static class UserStats {
        private final static double UNRELIABLE_USER_RATE = 0.5;

        private int overdueRecord;
        private int totalRecord;

        public UserStats(int overdueRecord, int totalRecord) {
            this.overdueRecord = overdueRecord;
            this.totalRecord = totalRecord;
        }

        public static UserStats merge(UserStats first, UserStats second) {
            return new UserStats(first.overdueRecord + second.overdueRecord, first.overdueRecord + second.overdueRecord);
        }

        public boolean isUnreliable () {
            return ((double) overdueRecord / totalRecord) < UNRELIABLE_USER_RATE;
        }
        public int getOverdueRecord() {
            return overdueRecord;
        }

        public void setOverdueRecord(int overdueRecord) {
            this.overdueRecord = overdueRecord;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     *
     * @param library   - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return library.getBooks().stream().filter((e) -> e.getPages() >= countPage).collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, Map<String, Long>> authorsStats = library.getArchive().stream().map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.groupingBy(Book::getAuthor, Collectors.counting())));

        return authorsStats.entrySet().stream().map(this::findBestAuthor)
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    private AbstractMap.SimpleEntry<Genre, String> findBestAuthor(Map.Entry<Genre, Map<String, Long>> entry) {
        long maxPopularity = Collections.max(entry.getValue().values());
        String bestAuthor = entry.getValue().entrySet().stream().filter(e -> maxPopularity == e.getValue())
                .map(Map.Entry::getKey).min(String::compareTo).get();
        return new AbstractMap.SimpleEntry<Genre, String>(entry.getKey(), bestAuthor);
    }
}
