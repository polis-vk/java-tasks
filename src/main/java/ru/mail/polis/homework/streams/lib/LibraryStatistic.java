package ru.mail.polis.homework.streams.lib;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;
import java.util.Map.Entry;
import java.util.*;

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
                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
                .filter(archivedData -> archivedData.getReturned() != null && TimeUnit.MILLISECONDS.toDays(
                        archivedData.getReturned().getTime() - archivedData.getTake().getTime()) >= 14)
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(user -> user.getValue().size() >= 5)
                .collect(Collectors.toMap(Map.Entry::getKey, archive -> archive.getValue()
                        .stream()
                        .mapToInt(archivedData -> archivedData.getUser().getReadedPages())
                        .sum()));
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
                .filter(archivedData -> archivedData.getUser().equals(user))
                .collect(Collectors.groupingBy(archivedData -> archivedData.getBook().getGenre()))
                .entrySet().stream()
                .max(LibraryStatistic::compare)
                .get().getKey();
    }

    private static int compare (Entry<Genre, List<ArchivedData>> value1, Entry<Genre, List<ArchivedData>> value2){
        long result1 = value1.getValue().stream().filter(archivedData -> archivedData.getReturned() != null).count();
        long result2 = value2.getValue().stream().filter(archivedData -> archivedData.getReturned() != null).count();
        long difference = result1 - result2;
        if (difference == 0) {
            return value1.getValue().size() - value2.getValue().size();
        }
        return (int) difference;
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .filter(archivedData -> {
                    long difference;
                    if (archivedData.getReturned() == null) {
                        difference = new Date().getTime()
                                - archivedData.getTake().getTime();
                    }
                    else {
                        difference = archivedData.getReturned().getTime()
                                - archivedData.getTake().getTime();
                    }
                    return TimeUnit.MILLISECONDS.toDays(difference) > 30;
                })
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
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
        return library.getBooks().stream().filter(book -> book.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        return library.getBooks().stream().
                collect(Collectors.groupingBy(Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, authors -> authors.getValue()
                .entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey()));
    }
}
