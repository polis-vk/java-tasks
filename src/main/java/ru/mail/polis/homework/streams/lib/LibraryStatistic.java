package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
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
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre)
                        && getReadingDays(archivedData) >= 14)
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.summingInt(x -> 1)))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= 5)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getKey().getReadedPages()));
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getUser().equals(user) && archivedData.getReturned() != null)
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre(),
                        Collectors.counting()))
                .entrySet().stream()
                .max((a, b) -> {
                    int delta = (int) (a.getValue() - b.getValue());

                    if (delta != 0) {
                        return delta;
                    }

                    return countAllUserBookForGenre(library, user, a.getKey())
                            - countAllUserBookForGenre(library, user, b.getKey());
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
                .filter(entry -> entry.getValue().stream()
                        .filter(archivedData -> getReadingDays(archivedData) > 30).count()
                        > entry.getValue().size() / 2)
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
                .collect(Collectors.groupingBy(Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().entrySet().stream()
                        .max((a, b) -> {
                            int delta = (int) (a.getValue() - b.getValue());

                            if (delta != 0) {
                                return delta;
                            }

                            return a.getKey().compareTo(b.getKey());
                        }).get().getKey()));
    }

    private int getReadingDays(ArchivedData aData) {
        long timeStampDelta;

        if (aData.getReturned() == null) {
            timeStampDelta = new Timestamp(System.currentTimeMillis()).getTime() - aData.getTake().getTime();
        } else {
            timeStampDelta = aData.getReturned().getTime() - aData.getTake().getTime();
        }

        return (int) (timeStampDelta / (1000 * 60 * 60 * 24));
    }

    private int countAllUserBookForGenre(Library library, User user, Genre genre) {
        return (int) library.getArchive().stream()
                .filter(archivedData -> archivedData.getUser().equals(user)
                        && archivedData.getBook().getGenre().equals(genre)).count();
    }
}
