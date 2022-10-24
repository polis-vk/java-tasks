package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     *
     * @param library - данные библиотеки
     * @param genre   - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        Stream<ArchivedData> stream = library.getArchive().stream()
                .filter(o -> o.getBook().getGenre() == genre);
        return stream
                .filter(archivedData -> {
                    User user = archivedData.getUser();
                    long count = stream
                            .filter(data -> data.getUser().equals(user))
                            .filter(data -> {
                                Timestamp timestamp = data.getReturned();
                                if (timestamp == null) {
                                    return data.getTake()
                                            .toInstant()
                                            .plus(14, ChronoUnit.DAYS)
                                            .getNano() <= System.nanoTime();
                                }
                                return data.getTake().toInstant().plus(14, ChronoUnit.DAYS).isBefore(timestamp.toInstant());
                            })
                            .count();
                    return count >= 5;
                })
                .collect(
                        HashMap::new,
                        (map, archivedData) -> {
                            User user = archivedData.getUser();
                            map.put(user, user.getReadedPages());
                        },
                        HashMap::putAll
                );
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
