package ru.mail.polis.homework.streams.lib;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private static final int SPECIALIST_DAYS_TO_READ = 14;
    private static final int SPECIALIST_READ_COUNT = 5;
    private static final int AVAILABLE_DAYS = 30;

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
        List<User> users = library.getUsers();

        List<ArchivedData> archiveForGenre = library.getArchive()
                .stream().filter(archiveCell -> archiveCell.getBook().getGenre().equals(genre)).collect(Collectors.toList());

        return users.stream().filter(user -> archiveForGenre.stream()
                        .filter(archiveCell -> archiveCell.getUser().equals(user))
                        .filter(archiveCell -> isMoreThanNdaysForBook(archiveCell, SPECIALIST_DAYS_TO_READ)).count() >= SPECIALIST_READ_COUNT)
                .collect(Collectors.toMap(user -> user, User::getReadedPages));
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
        List<ArchivedData> archiveForUser = library.getArchive().stream()
                .filter(archiveCell -> archiveCell.getUser().equals(user))
                .filter(archiveCell -> archiveCell.getReturned() != null)
                .collect(Collectors.toList());
        if (archiveForUser.isEmpty()) {
            return null;
        }

        Map<Genre, Long> genreFrequency = archiveForUser.stream()
                .collect(Collectors.groupingBy(archiveCell -> archiveCell.getBook().getGenre(), Collectors.counting()));

        Long max = genreFrequency.values().stream().max(Long::compareTo).get();

        Map<Genre, Long> maximums = genreFrequency.keySet().stream()
                .filter(key -> genreFrequency.get(key).equals(max)).collect(Collectors.toMap(key -> key, genreFrequency::get));

        if (maximums.size() != 1) {
            for (Genre genre : maximums.keySet()) {
                if (genre.equals(user.getBook().getGenre())) {
                    return genre;
                }
            }
        }
        return (Genre) maximums.keySet().toArray()[0];
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        List<User> users = library.getUsers();
        List<ArchivedData> archive = library.getArchive();

        return users.stream().filter(user ->
                        archive.stream()
                                .filter(archiveCell -> archiveCell.getUser().equals(user))
                                .filter(archiveCell -> isMoreThanNdaysForBook(archiveCell, AVAILABLE_DAYS))
                                .count() > archive.stream().filter(archiveCell -> archiveCell.getUser().equals(user)).count() / 2)
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
                .filter(book -> book.getPage() >= countPage).collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        //Жанр | Автор | Сколько раз взяли
        Map<Genre, Map<String, Long>> genreStatistics = library.getArchive().stream().map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.groupingBy(Book::getAuthor, Collectors.counting())));

        return genreStatistics.entrySet().stream().collect(Collectors
                .toMap(Map.Entry::getKey,
                        genreSet -> genreSet.getValue().entrySet().stream()
                                .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue)
                                        .thenComparing(Map.Entry.comparingByKey()))
                                .get().getKey()));
    }

    private boolean isMoreThanNdaysForBook(ArchivedData archiveCell, int daysN) {
        if (archiveCell.getReturned() != null) {
            return TimeUnit.MILLISECONDS.toDays(archiveCell.getReturned().getTime() - archiveCell.getTake().getTime()) >= daysN;
        }
        return TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - archiveCell.getTake().getTime()) >= daysN;
    }
}
