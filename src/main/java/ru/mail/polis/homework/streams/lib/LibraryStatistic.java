package ru.mail.polis.homework.streams.lib;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {
    private static final int SPECIALIST_MIN_BOOKS_COUNT = 5;
    private static final int SPECIALIST_READING_DAYS = 14;
    private static final int UNRELIABLE_USER_READING_DAYS = 30;

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
        return library.getArchive().stream()
                .filter(archive -> genre.equals(archive.getBook().getGenre()))
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(userEntry -> userEntry.getValue().stream()
                        .allMatch(e -> countDays(e) >= SPECIALIST_READING_DAYS))
                .filter(userEntry -> userEntry.getValue().size() >= SPECIALIST_MIN_BOOKS_COUNT)
                .collect(Collectors.toMap(Map.Entry::getKey, userEntry -> userEntry.getValue().stream()
                        .map(ArchivedData::getBook)
                        .mapToInt(Book::getPage).sum() + library.getUsers().stream()
                        .filter(user -> user.equals(userEntry.getKey()))
                        .filter(user -> user.getBook() != null)
                        .filter(user -> genre.equals(user.getBook().getGenre()))
                        .filter(user -> library.getArchive().stream()
                                .anyMatch(archive -> !archive.getBook().equals(user.getBook())))
                        .map(User::getReadedPages)
                        .reduce(0, Integer::sum))
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
        return library.getArchive().stream()
                .filter(archive -> user.equals(archive.getUser()))
                .collect(Collectors.groupingBy(archive -> archive.getBook().getGenre()))
                .entrySet().stream()
                .max(((arch1, arch2) -> {
                    if (arch1.getValue().size() == arch2.getValue().size()) {
                        return arch1.getValue().stream().anyMatch(a -> a.getReturned() == null) ? 1 : 0;
                    }
                    return arch1.getValue().size() - arch2.getValue().size();
                }))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library.getArchive().stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(archive -> archive.getValue().size() / 2 <
                        archive.getValue().stream().filter(e -> countDays(e) > UNRELIABLE_USER_READING_DAYS).count())
                .map(Map.Entry::getKey)
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
                .filter(book -> book.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
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
                        .map(Map.Entry::getKey)
                        .orElse("")));
    }

    private static long countDays(ArchivedData archive) {
        if (archive.getReturned() == null) {
            return (System.currentTimeMillis() - archive.getTake().getTime()) / (1000 * 60 * 60 * 24);
        }
        return (archive.getReturned().getTime() - archive.getTake().getTime()) / (1000 * 60 * 60 * 24);
    }
}

