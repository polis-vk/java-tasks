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

    private static final Date TWO_WEEK = new Date(13L * 24 * 60 * 60 * 1000);
    private static final Date MONTH = new Date(29L * 24 * 60 * 60 * 1000);

    public Map<User, Integer> specialistInGenre(Library library, Genre genre) {
        return library.getArchive().stream()
                .filter(archivedData -> archivedData.getBook().getGenre() == genre)
                .filter(this::bookFilter)
                .collect(Collectors.groupingBy(ArchivedData::getUser, Collectors.toList()))
                .entrySet().stream()
                .filter(userSetEntry -> userSetEntry.getValue().size() >= 5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        userListEntry -> {
                            int readPages = userListEntry.getValue().stream()
                                    .mapToInt(value -> value.getBook().getPage()).sum();
                            if (userListEntry.getValue().get(0).getUser().getBook().getGenre().equals(genre)) {
                                return readPages += userListEntry.getValue().get(0).getUser().getReadedPages();
                            }
                            return readPages;
                        }));
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
     * книги, которую юзер сейчас читает и прибавляем к соответствующему рейтингу. Если книги такой нет, то показывается
     * первая в мапе с рейтингами. Дальше выводим результат для сравнения.
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
                    int comparedInt = currentEntry.getValue().compareTo(nextEntry.getValue());
                    Book userBook = user.getBook();
                    if (userBook != null && comparedInt == 0) {
                        if (userBook.getGenre().equals(currentEntry.getKey()) &&
                                !userBook.getGenre().equals(nextEntry.getKey())) {
                            return ++comparedInt;
                        }
                        return --comparedInt;
                    }
                    return comparedInt;
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
        Map<Genre, String> mostPopularAuthor = library.getArchive().stream()
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
                                .min(Comparator.comparingLong(Map.Entry<String, Long>::getValue)
                                        .reversed()
                                        .thenComparing(Map.Entry::getKey))
                                .map(Map.Entry::getKey).orElseThrow(NoSuchElementException::new)));
        for (Genre genre : Genre.values()) {
            if (!mostPopularAuthor.containsKey(genre)) {
                mostPopularAuthor.put(genre, "Author not determined");
            }
        }
        return mostPopularAuthor;
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

        return countUnreliableData > (long) Math.floor(archivedData.size() / 2.);
    }
}
