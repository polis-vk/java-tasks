package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.time.Instant;
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
     *
     * @param library - данные библиотеки
     * @param genre   - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
//        library.getArchive()
//                .stream()
//                .filter(archivedData -> archivedData.getBook().getGenre().equals(genre))
//                .collect()
        return null;
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
        return library.getBooks()
                .stream()
                .filter((book -> book.getPage() >= countPage))
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, Long> mostPopularAuthorInGenre(Library library) {
//        Map<String, Long> authorWithRepetition =
//                library.getArchive()
//                        .stream()
//                        .collect(Collectors.groupingBy((data) -> data.getBook().getAuthor(), Collectors.counting()));
        return null;


    }

    public static void main(String[] args) {
        Book book1 = new Book("shortStory", "Hemingway", Genre.HISTORY, 20);
        Book book2 = new Book("book2", "Dovlatov", Genre.HISTORY, 25);
        User user1 = new User("firstUser", 12, book1, 15);
        User user2 = new User("secondUser", 20, book2, 20);
        ArchivedData data1 = new ArchivedData(user1, book1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
        ArchivedData data2 = new ArchivedData(user2, book2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
        ArchivedData data3 = new ArchivedData(user1, book2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
        Library library = new Library(List.of(user1, user2), List.of(book1, book2), List.of(data1, data2, data3));
        LibraryStatistic libraryStatistic = new LibraryStatistic();
        System.out.println(libraryStatistic.mostPopularAuthorInGenre(library));
    }
}
