package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
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
        return library.getUsers().stream()
                .filter(user -> library.getArchive().stream()
                        .filter(achieveNote -> achieveNote.getBook().getGenre().equals(genre)
                                && achieveNote.getUser().equals(user)
                                && compareDays(achieveNote, 14))
                        .count() >= 5)
                .collect(Collectors.toMap(user -> user, User::getReadedPages));
    }

    private boolean compareDays(ArchivedData archiveCell, int days) {
        if (archiveCell.getReturned() == null) {
            return TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - archiveCell.getTake().getTime()) >= days;
        }
        return TimeUnit.MILLISECONDS.toDays(archiveCell.getReturned().getTime() - archiveCell.getTake().getTime()) >= days;

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
        Map<Genre, Long> genreFrequency =
                library.getArchive().stream().filter(
                                achieveNote -> achieveNote.getUser().equals(user) && achieveNote.getReturned() != null
                        )
                        .collect(
                                Collectors.groupingBy(
                                        achieveNote -> achieveNote.getBook().getGenre(), Collectors.counting()
                                )
                        );

        if (genreFrequency.isEmpty()) {
            return null;
        }

        Long maxAmount = genreFrequency.values().stream().max(Long::compareTo).get();

        List<Genre> listOfMaxGenres = genreFrequency.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxAmount))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (listOfMaxGenres.contains(user.getBook().getGenre())) {
            return user.getBook().getGenre();
        }

        return listOfMaxGenres.isEmpty() ? null : listOfMaxGenres.get(0);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getUsers().stream().filter(
                        user -> library.getArchive().stream().filter(
                                        achieveNote -> achieveNote.getUser().equals(user) && compareDays(achieveNote, 30)
                                )
                                .count() * 2L >= library.getArchive().stream().filter(archiveCell -> archiveCell.getUser().equals(user)).count()
                )
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
        return library.getBooks().stream().filter(book -> book.getPage() >= countPage).collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        if (library.getBooks().isEmpty() || library.getArchive().isEmpty()) {
            return null;
        }

        return library.getArchive().stream().collect(
                        Collectors.groupingBy(
                                achieveNote -> achieveNote.getBook().getGenre(),
                                Collectors.groupingBy(
                                        achieveNote -> achieveNote.getBook().getAuthor(),
                                        Collectors.counting()
                                )
                        )
                )
                .entrySet().stream().collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().entrySet().stream()
                                        .max(Comparator.comparingLong(Map.Entry::getValue)).get().getKey()
                        )
                );
    }
}
