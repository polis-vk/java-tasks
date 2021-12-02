package ru.mail.polis.homework.streams.lib;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    public static final int SPECIALIST_READING_DAYS = 14;
    public static final int SPECIALIST_MIN_BOOKS_COUNT = 5;
    public static final int UNRELIABLE_USERS_READING_DAYS = 30;

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
        if ((library == null) || (genre == null)) {
            throw new IllegalArgumentException();
        }
        List<User> users = library.getUsers().stream().filter((e) -> e.getBook().getGenre().equals(genre))
                .filter((user) -> getDays(user, library) >= SPECIALIST_READING_DAYS)
                .collect(Collectors.toList());
       return users.stream().filter((e) -> getNumberOfEquals(e, users) >= SPECIALIST_MIN_BOOKS_COUNT)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(User::getReadPages)));
    }

    private int getNumberOfEquals(User user, List<User> users) {
        return (int) users.stream().filter((e) -> e.equals(user)).count();
    }

    private int getDays(User user, Library library) {
        return library.getArchive().stream().filter((e) -> (e.getUser().equals(user) && e.getBook().equals(user.getBook()))).mapToInt((e) -> {
            if (e.getReturned() == null) {
                return 0;
            }
            return (int) (e.getReturned().getTime() - e.getTake().getTime()) / (1000 * 60 * 60 * 24);
        }).sum();
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
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return null;
    }

    /**
     * Вернуть список книг у которых страниц равно или больше чем переданное значение
     *
     * @param library   - данные библиотеки
     * @param countPage - кол-во страниц
     * @return - список книг
     */
    public List<Book> booksWithMoreCountPages(Library library, int countPage) {
        return null;
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return null;
    }
}
