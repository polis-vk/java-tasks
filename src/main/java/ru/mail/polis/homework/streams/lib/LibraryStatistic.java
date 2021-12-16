package ru.mail.polis.homework.streams.lib;

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
                .filter(a -> a.getBook().getGenre().equals(genre)
                        && ((a.getReturned().getTime() - a.getTake().getTime()) / (1000 * 60 * 60 * 24)) > 14)
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream().filter(a -> a.getValue().size() >= 5)
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().size()));
    }

    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     * @param library - данные библиотеки
     * @param user - пользователь
     * @return - жанр
     */
    public Genre loveGenre(Library library, User user) {
        return library.getArchive().stream().filter(a -> a.getUser().equals(user))
                .collect(Collectors.groupingBy(a -> a.getBook().getGenre()))
                .entrySet().stream().max(this::genreComparator).map(Map.Entry::getKey).orElse(null);
    }

    private int genreComparator(Map.Entry<Genre, List<ArchivedData>> arc1, Map.Entry<Genre, List<ArchivedData>> arc2) {
        if (arc1.getValue().size() == arc2.getValue().size()) {
            return arc1.getValue().stream().noneMatch(a -> a.getReturned() != null) ? 1 : 0;
        }
        return arc1.getValue().size() - arc2.getValue().size();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream().filter(a ->
                (a.getReturned() == null && ((System.currentTimeMillis() - a.getTake().getTime()) / (1000 * 60 * 60 * 24)) > 30)
                        || ((a.getReturned().getTime() - a.getTake().getTime()) / (1000 * 60 * 60 * 24)) > 30)
                .map(ArchivedData::getUser)
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
                .filter(a -> a.getPage() >= countPage)
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
                .collect(Collectors.toMap(Map.Entry::getKey, author -> author.getValue().entrySet().stream()
                        .max(Map.Entry.<String, Long>comparingByValue()
                                .thenComparing(Map.Entry.comparingByKey()))
                        .map(Map.Entry::getKey).orElse("")));
    }
}
