package ru.mail.polis.homework.streams.lib;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
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
        return library
                .getArchive()
                .stream()
                .filter(e -> Period.between(e.getTake().toLocalDateTime().toLocalDate(), e.getReturned().toLocalDateTime().toLocalDate()).getDays() > 13 && e.getBook().getGenre().equals(genre))
                .collect(Collectors.groupingBy(
                        ArchivedData::getUser
                ))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() >= 5)
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    int result = e.getValue().stream().mapToInt(a -> a.getBook().getPage()).sum();
                    if (e.getKey().getBook().getGenre().equals(genre)) {
                        result += e.getKey().getReadedPages();
                    }
                    return result;
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
    public Genre loveGenre(Library library, User user) {
        return library
                .getArchive()
                .stream()
                .filter(a -> a.getUser().equals(user))
                .collect(Collectors.groupingBy(a -> a.getBook().getGenre(), Collectors.counting()))
                .entrySet()
                .stream()
                .max((p, n) -> {
                    if (p.getValue().compareTo(n.getValue()) < 0) {
                        return -1;
                    } else {
                        if (p.getValue().equals(n.getValue())) {
                            if (p.getKey().equals(user.getBook().getGenre())) {
                                p.setValue(p.getValue() + 1);
                            }
                            if (n.getKey().equals(user.getBook().getGenre())) {
                                n.setValue(n.getValue() + 1);
                            }
                        }
                        return Integer.compare(p.getValue().intValue(), n.getValue().intValue());
                    }
                }).orElseThrow(NoSuchElementException::new).getKey();
    }

    /**
     * Вернуть список пользователей которые больше половины книг держали на руках более 30-ти дней. Брать в расчет и книги которые сейчас
     * пользователи держат у себя (ArchivedData.returned == null)
     *
     * @param library - данные библиотеки
     * @return - список ненадежных пользователей
     */
    public List<User> unreliableUsers(Library library) {
        return library
                .getArchive()
                .stream()
                .collect(Collectors.groupingBy(ArchivedData::getUser))
                .entrySet()
                .stream()
                .filter(e -> {
                            long nubmerBooks = e.getValue().size();
                            long readedMore30Days = e.getValue()
                                    .stream()
                                    .filter(a -> {
                                        if (a.getReturned() == null) {
                                            return ChronoUnit.DAYS.between(a.getTake().toLocalDateTime().toLocalDate(),
                                                    LocalDateTime.now()) > 30;
                                        } else {
                                            return ChronoUnit.DAYS.between(a.getTake().toLocalDateTime().toLocalDate(),
                                                    a.getReturned().toLocalDateTime().toLocalDate()) > 30;
                                        }
                                    })
                                    .count();
                            return (nubmerBooks / ((readedMore30Days != 0) ? readedMore30Days : nubmerBooks + 1) == 1);
                        }
                )
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
        return library
                .getBooks()
                .stream()
                .filter(b -> b.getPage() >= countPage)
                .collect(Collectors.toList());
    }

    /**
     * Вернуть самого популярного автора в каждом жанре. Если кол-во весов у авторов одинаково брать по алфавиту.
     *
     * @param library - данные библиотеки
     * @return - map жанр / самый популярный автор
     */
    public Map<Genre, String> mostPopularAuthorInGenre(Library library) {
        Map<Genre, String> popularAuthorsInGenres = library
                .getArchive()
                .stream()
                .collect(Collectors.groupingBy(a -> a.getBook().getGenre()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().collect(Collectors.groupingBy(a -> a.getBook().getAuthor(), Collectors.counting()))
                                .entrySet()
                                .stream()
                                .max((c1, c2) -> {
                                    if (c1.getValue().compareTo(c2.getValue()) != 0) {
                                        return c1.getValue().compareTo(c2.getValue());
                                    } else {
                                        return c2.getKey().compareTo(c1.getKey());
                                    }
                                })
                                .orElseThrow(NoSuchElementException::new)
                                .getKey()
                ));
        return Arrays.stream(Genre.values()).collect(Collectors.toMap(Function.identity(), e -> popularAuthorsInGenres.getOrDefault(e, "Author not determined")));
    }
}

