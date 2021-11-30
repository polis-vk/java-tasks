package ru.mail.polis.homework.streams.lib;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * Класс для работы со статистикой по библиотеке.
 * Оценка 5-ть баллов
 */
public class LibraryStatistic {

    private final static int SPECIALIST_DAYS = 14;
    private final static int UNRELIABLE_DAYS = 30;

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
        List<ArchivedData> archivedData = library.getArchive();
        List<User> users = library.getUsers();
        return users.stream().filter(user -> archivedData.stream()
                        .filter(archivedData1 -> archivedData1.getUser().equals(user))
                        .filter(archivedData1 -> archivedData1.getBook().getGenre().equals(genre))
                        .filter(archivedData1 ->
                                (archivedData1.getReturned() != null
                                        && TimeUnit.MILLISECONDS.toDays(archivedData1.getReturned().getTime() - archivedData1.getTake().getTime()) >= SPECIALIST_DAYS)
                                        || (archivedData1.getReturned() == null
                                        && TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - archivedData1.getTake().getTime()) >= SPECIALIST_DAYS))
                        .count() >= 5)
                .collect(Collectors.toMap(Function.identity(), User::getReadedPages));
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
        List<ArchivedData> archivedData = library.getArchive();

        Stream<ArchivedData> usersArchiveData = archivedData.stream()
                .filter(archivedData1 -> archivedData1.getUser().equals(user));

        Map<Genre, Long> genreFrequencyMap = usersArchiveData
                .filter(archivedData1 -> archivedData1.getReturned() != null)
                .map(archivedData1 -> archivedData1.getBook().getGenre())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map.Entry<Genre, Long> maxGenreMap = genreFrequencyMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        List<Genre> genreMaxes = genreFrequencyMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(maxGenreMap.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (genreMaxes.size() > 1) {
            return genreMaxes.stream()
                    .max(comparing(genre ->
                            usersArchiveData
                                    .filter(archivedData1 -> archivedData1.getReturned() == null)
                                    .filter(archivedData1 -> archivedData1.getBook().getGenre().equals(genre))
                                    .count()))
                    .get();
        }
        return genreMaxes.get(0);
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        List<ArchivedData> archivedData = library.getArchive();
        List<User> users = library.getUsers();
        return users.stream().filter(user -> archivedData.stream()
                        .filter(archivedData1 -> archivedData1.getUser().equals(user))
                        .filter(archivedData1 ->
                                (archivedData1.getReturned() != null
                                        && TimeUnit.MILLISECONDS.toDays(archivedData1.getReturned().getTime() - archivedData1.getTake().getTime()) > UNRELIABLE_DAYS)
                                        || (archivedData1.getReturned() == null
                                        && TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - archivedData1.getTake().getTime()) > UNRELIABLE_DAYS))
                        .count() > archivedData.stream()
                        .filter(archivedData1 -> archivedData1.getUser().equals(user))
                        .count() / 2)
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
        return library.getArchive().stream()
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(Book::getGenre,
                        Collectors.groupingBy(Book::getAuthor, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> {
                            long max = entry.getValue().values().stream()
                                    .max(Long::compareTo)
                                    .orElse(0L);
                            List<String> authorMaxes = entry.getValue().entrySet().stream()
                                    .filter(stringLongEntry -> stringLongEntry.getValue().equals(max))
                                    .map(Map.Entry::getKey)
                                    .collect(Collectors.toList());
                            if (authorMaxes.size() > 1) {
                                return authorMaxes.stream()
                                        .max(comparing(Function.identity()))
                                        .get();
                            }
                            return authorMaxes.get(0);
                        }
                ));
    }
}
