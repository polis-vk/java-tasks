package ru.mail.polis.homework.streams.lib;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    private static final int DAYS_TO_SPECIALIST = 14;
    private static final int BOOKS_COUNT_FOR_SPECIALIST = 5;
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
        return library.getArchive()
                .stream()
                .filter(archiveCell -> archiveCell.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(archiveList -> archiveList.getValue().size() >= BOOKS_COUNT_FOR_SPECIALIST)
                .filter(archiveList -> archiveList.getValue().stream()
                        .allMatch(a -> isSpecialist(a)))
                .collect(Collectors.toMap(Map.Entry::getKey, a ->
                        a.getValue().stream()
                                .mapToInt(b -> b.getBook().getPage())
                                .sum()));

    }

    private boolean isSpecialist(ArchivedData archiveCell) {
        if (archiveCell.getReturned() == null) {
            return TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - archiveCell.getTake().getTime()) >= LibraryStatistic.DAYS_TO_SPECIALIST;
        }
        return TimeUnit.MILLISECONDS.toDays(archiveCell.getReturned().getTime() - archiveCell.getTake().getTime()) >= LibraryStatistic.DAYS_TO_SPECIALIST;
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
        return null;
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 14-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        List<ArchivedData> archive = library.getArchive();

    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     * @param library - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return null
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return null;
    }
}
