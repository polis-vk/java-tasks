package ru.mail.polis.homework.streams.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private final static long day = 86_400_400;

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
        Map<User, Integer> specialists = new HashMap<>();
        List<ArchivedData> archiveStorage = library.getArchive();
        List<User> users = library.getUsers();
        long readBooks;
        long twoWeeks = day * 14;
        for (User user : users) {
            readBooks = archiveStorage.stream()
                    .filter(x -> x.getBook().getGenre() == genre)
                    .filter(x -> x.getUser() == user)
                    .filter(x -> (x.getReturned().getTime() - x.getTake().getTime()) >= twoWeeks)
                    .count();
            if (readBooks >= 5) {
                specialists.put(user, user.getReadedPages());
            }
        }
        return specialists;
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
        return library.getArchive().stream()
                .filter(data -> data.getUser() == user)
                .map(ArchivedData::getBook)
                .map(Book::getGenre)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .distinct()
                .sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()))
                .findFirst()
                .get()
                .getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        final long month = day * 30;
        return library.getArchive().stream()
                .filter(x -> (x.getReturned() == null ? x.getTake().getTime() : (x.getReturned().getTime() - x.getTake().getTime())) > month)
                .map(ArchivedData::getUser)
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
        return library.getArchive().stream()
                .map(ArchivedData::getBook)
                .filter(book -> book.getPage() >= countPage)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, String> mostPopularAuthorInGenre = new HashMap<>();
        List<Genre> libraryGenres = library.getArchive()
                .stream()
                .map(ArchivedData::getBook)
                .map(Book::getGenre)
                .distinct()
                .collect(Collectors.toList());

        for (Genre genre : libraryGenres) {
            String authorInGenre = library.getArchive().stream()
                    .map(ArchivedData::getBook)
                    .filter(x -> x.getGenre() == genre)
                    .map(Book::getAuthor)
                    .sorted()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .distinct()
                    .sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()))
                    .findFirst()
                    .get()
                    .getKey();
            mostPopularAuthorInGenre.put(genre, authorInGenre);
        }
        return mostPopularAuthorInGenre;
    }
}
