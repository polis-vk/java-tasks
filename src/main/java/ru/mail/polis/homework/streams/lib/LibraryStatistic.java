package ru.mail.polis.homework.streams.lib;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
     * @param genre - жанр
     * @return - map пользователь / кол-во прочитанных страниц
     */
    private static final Timestamp TWO_WEEK = new Timestamp(14L * 24 * 60 * 60 * 1000);
    private static final Timestamp MONTH = new Timestamp(30L * 24 * 60 * 60 * 1000);

    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getBook().getGenre() == genre)
                .filter(this::bookFilter)
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toSet()))
                .entrySet().stream()
                .filter(userSetEntry -> userSetEntry.getValue().size() >= 5)
                .collect(Collectors.toMap(Map.Entry::getKey, userSetEntry -> userSetEntry.getKey().getReadedPages()));
    }


    /**
     * Вернуть любимый жанр пользователя. Тот что чаще всего встречается. Не учитывать тот что пользователь читает в данный момент.
     * Если есть несколько одинаковых по весам жанров - брать в расчет то, что пользователь читает в данный момент.
     *
     * @param library - данные библиотеки
     * @param user    - пользователь
     * @return - жанр
     */
    /*
     * Сначала проходимся по жанрам и создаем мапу с сопоставленими Жанр - Рейтинг. Дальше ищем жанр с наибольшим
     * рейтингом. Если рейтинги разные то ищем мксимальный по рейтингу. Если рейтинг одинаковый, подсчитываем рейтинг
     * книг, которые юзер сейчас читает и прибавляем к соответствующему рейтингу. Дальше выводим результат для сравнения.
     */
    public Genre loveGenre(Library library, User user) {
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getUser().equals(user) &&
                        archivedData.getReturned() != null)
                .collect(Collectors.groupingBy(
                        archivedData -> archivedData.getBook().getGenre(),
                        Collectors.counting()))
                .entrySet().stream()
                .max((currentEntry, nextEntry) -> {
                    int comparedInt = nextEntry.getValue().compareTo(currentEntry.getValue());
                    if (comparedInt != 0) {
                        return comparedInt;
                    }
                    long newCurrentEntryScore = currentEntry.getValue() + library.getArchive().stream()
                            .filter(archivedData -> archivedData.getUser().equals(user) &&
                                    archivedData.getBook().getGenre().equals(currentEntry.getKey()))
                            .count();
                    long newNextEntryScore = nextEntry.getValue() + library.getArchive().stream()
                            .filter(archivedData -> archivedData.getUser().equals(user) &&
                                    archivedData.getBook().getGenre().equals(nextEntry.getKey()))
                            .count();
                    return (int) (newNextEntryScore - newCurrentEntryScore);
                })
                .orElseThrow(NoSuchElementException::new).getKey();
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
                .filter(userListEntry -> unreliableUsersFilter(userListEntry.getValue()))
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
        return library.getArchive().stream()
                .map(ArchivedData::getBook)
                .collect(Collectors.groupingBy(
                        Book::getGenre,
                        Collectors.groupingBy(
                                Book::getAuthor,
                                Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        genreMapEntry -> genreMapEntry.getValue().entrySet().stream()
                                .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue)
                                        .thenComparing((Map.Entry::getKey)))
                                .map(Map.Entry::getKey).orElseThrow(NoSuchElementException::new)));
    }

    private boolean bookFilter(ArchivedData archivedData) {
        if (archivedData.getReturned() == null &&
                new Timestamp(new Date().getTime()).getTime() -
                        archivedData.getTake().getTime() >= TWO_WEEK.getTime()) {
            return true;
        } else return (archivedData.getReturned().getTime() -
                archivedData.getTake().getTime()) >= TWO_WEEK.getTime();
    }

    private boolean unreliableUsersFilter(List<ArchivedData> archivedData) {
        long countUnreliableData = archivedData.stream()
                .filter(archivedData1 -> {
                    if (archivedData1.getReturned() == null &&
                            new Timestamp(new Date().getTime()).getTime() -
                                    archivedData1.getTake().getTime() > MONTH.getTime()) {
                        return true;
                    } else return (archivedData1.getReturned().getTime() -
                            archivedData1.getTake().getTime()) > MONTH.getTime();
                })
                .count();

        return countUnreliableData > ((long) Math.ceil(archivedData.size() / 2.));
    }
}
