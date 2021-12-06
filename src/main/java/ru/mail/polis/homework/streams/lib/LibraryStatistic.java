package ru.mail.polis.homework.streams.lib;

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

    private static final int DAYS_TO_SPECIALIST = 14;
    private static final int BOOKS_COUNT_FOR_SPECIALIST = 5;

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
                .filter(arch -> arch.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet().stream()
                .filter(archiveList -> archiveList.getValue().size() >= BOOKS_COUNT_FOR_SPECIALIST)
                .filter(archiveList -> archiveList.getValue().stream()
                        .allMatch(a -> isSpecialist(a, DAYS_TO_SPECIALIST)))
                .collect(Collectors.toMap(Map.Entry::getKey, a ->
                        a.getValue().stream()
                                .mapToInt(b -> b.getBook().getPage())
                                .sum()));

    }

    private boolean isSpecialist(ArchivedData arch, int daysCount) {
        if (arch.getReturned() == null) {
            return TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - arch.getTake().getTime()) >= daysCount;
        }
        return TimeUnit.MILLISECONDS.toDays(arch.getReturned().getTime() - arch.getTake().getTime()) >= daysCount;
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
                .filter(a -> a.getUser().equals(user))
                .collect(Collectors.groupingBy(a -> a.getBook().getGenre()))
                .entrySet().stream()
                .max((o1, o2) -> compareLoveGenre(o1.getValue(), o2.getValue()))
                .get().getKey();
    }

    private int compareLoveGenre(List<ArchivedData> value, List<ArchivedData> value1) {
        if (value.size() == value1.size()) {
            return value.stream().anyMatch(a -> a.getReturned() == null) ? 1 : 0;
        }
        return Integer.compare(value.size(), value1.size());
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
                .collect(Collectors.groupingBy(ArchivedData::getUser)).entrySet().stream()
                .filter(arch -> arch.getValue().stream().filter(a -> isSpecialist(a, 30)).count() > arch.getValue().size() / 2)
                .map(a -> a.getKey())
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
        return library.getArchive().stream()
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, genreSet -> genreSet.getValue().entrySet().stream()
                        .max(Comparator
                                .comparingLong(Map.Entry<String, Long>::getValue)
                                .thenComparing(Map.Entry.comparingByKey()))
                        .get().getKey()));
    }
}
