package ru.mail.polis.homework.streams.lib;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class LibraryStatisticTest {

    @Test
    public void specialistInGenre() {
        List<User> users = generateUsers();
        List<Book> books = generateBooks();

        LocalDateTime mashaTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 1, 0, 0, 0);
        LocalDateTime mashaReturnedDate = LocalDateTime.of(2015, Month.SEPTEMBER, 15, 0, 0, 0);
        List<ArchivedData> dataMasha = new ArrayList<>();
        dataMasha.addAll(generateArchiveData(getUserByName(users, "Маша"), books, Genre.FANTASY, Timestamp.valueOf(mashaTakeDate), Timestamp.valueOf(mashaReturnedDate)));
        dataMasha.addAll(generateArchiveData(getUserByName(users, "Маша"),
                Stream.of(getBookByName(books, "Философия Java"),
                        getBookByName(books, "Чистый код"),
                        getBookByName(books, "Потоковая обработка данных с Apache Flink"),
                        getBookByName(books, "Java Concurrency на практике"),
                        getBookByName(books, "Java. Эффективное программирование")).collect(Collectors.toList()),
                Timestamp.valueOf(mashaTakeDate),
                Timestamp.valueOf(mashaReturnedDate)));
        getUserByName(users, "Маша").setBook(getBookByName(books, "System Design. Подготовка к сложному интервью"));
        getUserByName(users, "Маша").setReadedPages(150);

        LocalDateTime petyaTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 11, 0, 0, 0);
        LocalDateTime petyaReturnedDate = LocalDateTime.of(2015, Month.SEPTEMBER, 26, 0, 0, 0);
        List<ArchivedData> dataPetya = new ArrayList<>();
        dataPetya.addAll(generateArchiveData(getUserByName(users, "Петя"), books, Genre.TECHNICAL, Timestamp.valueOf(petyaTakeDate), Timestamp.valueOf(petyaReturnedDate)));
        dataPetya.addAll(generateArchiveData(getUserByName(users, "Петя"), books, Genre.NOVEL, Timestamp.valueOf(petyaTakeDate), Timestamp.valueOf(petyaReturnedDate)));
        getUserByName(users, "Петя").setBook(getBookByName(books, "Капитанская дочка"));
        getUserByName(users, "Петя").setReadedPages(200);

        LocalDateTime faruhTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 10, 0, 0, 0);
        LocalDateTime faruhReturnedDate = LocalDateTime.of(2015, Month.SEPTEMBER, 17, 0, 0, 0);
        List<ArchivedData> dataFaruh = new ArrayList<>();
        dataFaruh.addAll(generateArchiveData(getUserByName(users, "Фарух"), books, Genre.HISTORY, Timestamp.valueOf(faruhTakeDate), Timestamp.valueOf(faruhReturnedDate)));
        dataFaruh.addAll(generateArchiveData(getUserByName(users, "Фарух"), books, Genre.TECHNICAL, Timestamp.valueOf(faruhTakeDate), Timestamp.valueOf(faruhReturnedDate)));

        List<ArchivedData> allArchivedData = new ArrayList<>();
        allArchivedData.addAll(dataMasha);
        allArchivedData.addAll(dataPetya);
        allArchivedData.addAll(dataFaruh);

        Library library = new Library(users, books, allArchivedData);
        LibraryStatistic statistic = new LibraryStatistic();

        Map<User, Integer> technicalStatResult = statistic.specialistInGenre(library, Genre.TECHNICAL);
        Map<User, Integer> novelStatResult = statistic.specialistInGenre(library, Genre.NOVEL);
        Map<User, Integer> historyStatResult = statistic.specialistInGenre(library, Genre.HISTORY);

        assertEquals(2, technicalStatResult.size());
        assertNotNull(technicalStatResult.get(getUserByName(users, "Петя")));
        assertNotNull(technicalStatResult.get(getUserByName(users, "Маша")));
        assertEquals(Integer.valueOf(2634), technicalStatResult.get(getUserByName(users, "Петя")));
        assertEquals(Integer.valueOf(2480), technicalStatResult.get(getUserByName(users, "Маша")));

        assertEquals(1, novelStatResult.size());
        assertNotNull(novelStatResult.get(getUserByName(users, "Петя")));
        assertEquals(Integer.valueOf(3744), novelStatResult.get(getUserByName(users, "Петя")));

        assertEquals(0, historyStatResult.size());
    }

    @Test
    public void loveGenre() {
        List<User> users = generateUsers();
        List<Book> books = generateBooks();

        LocalDateTime petyaTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 11, 0, 0, 0);
        LocalDateTime petyaReturnedDate = LocalDateTime.of(2015, Month.SEPTEMBER, 26, 0, 0, 0);
        List<ArchivedData> dataPetya = new ArrayList<>();
        dataPetya.addAll(generateArchiveData(getUserByName(users, "Петя"), books, Genre.TECHNICAL, Timestamp.valueOf(petyaTakeDate), Timestamp.valueOf(petyaReturnedDate)));
        dataPetya.addAll(generateArchiveData(getUserByName(users, "Петя"), books, Genre.HISTORY, Timestamp.valueOf(petyaTakeDate), Timestamp.valueOf(petyaReturnedDate)));
        dataPetya.addAll(generateArchiveData(getUserByName(users, "Петя"), books, Genre.NOVEL, Timestamp.valueOf(petyaTakeDate), Timestamp.valueOf(petyaReturnedDate)));

        LocalDateTime mashaTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 5, 0, 0, 0);
        LocalDateTime mashaReturnedDate = LocalDateTime.of(2015, Month.SEPTEMBER, 15, 0, 0, 0);
        List<ArchivedData> dataMasha = new ArrayList<>();
        dataMasha.addAll(generateArchiveData(getUserByName(users, "Маша"),
                Stream.of(getBookByName(books, "Машина времени"),
                        getBookByName(books, "Капитанская дочка"),
                        getBookByName(books, "Властелин колец"),
                        getBookByName(books, "Звездолет «Иосиф Сталин». На взлет!"),
                        getBookByName(books, "Философия Java")).collect(Collectors.toList()),
                Timestamp.valueOf(mashaTakeDate),
                Timestamp.valueOf(mashaReturnedDate)));
        getUserByName(users, "Маша").setBook(getBookByName(books, "Гиперборея. Исторические корни русского народа"));
        getUserByName(users, "Маша").setReadedPages(150);

        List<ArchivedData> allArchivedData = new ArrayList<>();
        allArchivedData.addAll(dataMasha);
        allArchivedData.addAll(dataPetya);

        Library library = new Library(users, books, allArchivedData);
        LibraryStatistic statistic = new LibraryStatistic();

        assertEquals(Genre.NOVEL, statistic.loveGenre(library, getUserByName(users, "Петя")));
        assertEquals(Genre.HISTORY, statistic.loveGenre(library, getUserByName(users, "Маша")));
    }

    @Test
    public void unreliableUsers() {
        List<User> users = generateUsers();
        List<Book> books = generateBooks();

        LocalDateTime mashaUnreliableTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 5, 0, 0, 0);
        LocalDateTime mashaUnreliableReturnedDate = LocalDateTime.of(2015, Month.OCTOBER, 15, 0, 0, 0);
        LocalDateTime mashaGoodTakeDate = LocalDateTime.of(2015, Month.OCTOBER, 15, 0, 0, 0);
        LocalDateTime mashaGoodReturnedDate = LocalDateTime.of(2015, Month.OCTOBER, 27, 0, 0, 0);

        List<ArchivedData> dataMasha = new ArrayList<>();
        dataMasha.addAll(generateArchiveData(getUserByName(users, "Маша"),
                Stream.of(getBookByName(books, "Властелин колец"),
                        getBookByName(books, "Машина времени")).collect(Collectors.toList()),
                Timestamp.valueOf(mashaUnreliableTakeDate),
                Timestamp.valueOf(mashaUnreliableReturnedDate)));
        dataMasha.addAll(generateArchiveData(getUserByName(users, "Маша"), books, Genre.ADVENTURE, Timestamp.valueOf(mashaGoodTakeDate), Timestamp.valueOf(mashaGoodReturnedDate)));

        LocalDateTime petyaUnreliableTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 5, 0, 0, 0);
        LocalDateTime petyaUnreliableReturnedDate = LocalDateTime.of(2015, Month.NOVEMBER, 3, 0, 0, 0);
        LocalDateTime petyaGoodTakeDate = LocalDateTime.of(2015, Month.NOVEMBER, 15, 0, 0, 0);
        LocalDateTime petyaGoodReturnedDate = LocalDateTime.of(2015, Month.NOVEMBER, 23, 0, 0, 0);

        List<ArchivedData> dataPetya = new ArrayList<>();
        dataPetya.addAll(generateArchiveData(getUserByName(users, "Петя"),
                Stream.of(getBookByName(books, "Философия Java"),
                        getBookByName(books, "Чистый код")).collect(Collectors.toList()),
                Timestamp.valueOf(petyaUnreliableTakeDate),
                Timestamp.valueOf(petyaUnreliableReturnedDate)));
        dataPetya.addAll(generateArchiveData(getUserByName(users, "Петя"), books, Genre.ADVENTURE, Timestamp.valueOf(petyaGoodTakeDate), Timestamp.valueOf(petyaGoodReturnedDate)));
        dataPetya.add(new ArchivedData(getUserByName(users, "Петя"), getBookByName(books, "Java. Эффективное программирование"), Timestamp.valueOf(petyaGoodReturnedDate), null)); //просроченна к данному моменту

        LocalDateTime faruhUnreliableTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 1, 0, 0, 0);
        LocalDateTime faruhUnreliableReturnedDate = LocalDateTime.of(2015, Month.NOVEMBER, 1, 0, 0, 0);
        List<ArchivedData> dataFaruh = new ArrayList<>();

        dataFaruh.addAll(generateArchiveData(getUserByName(users, "Фарух"), books, Genre.HISTORY, Timestamp.valueOf(faruhUnreliableTakeDate), Timestamp.valueOf(faruhUnreliableReturnedDate)));

        List<ArchivedData> allArchivedData = new ArrayList<>();
        allArchivedData.addAll(dataMasha);
        allArchivedData.addAll(dataPetya);
        allArchivedData.addAll(dataFaruh);

        Library library = new Library(users, books, allArchivedData);
        LibraryStatistic statistic = new LibraryStatistic();
        List<User> result = statistic.unreliableUsers(library);

        assertEquals(2, result.size());
        assertTrue(result.contains(getUserByName(users, "Петя")));
        assertTrue(result.contains(getUserByName(users, "Фарух")));
    }

    @Test
    public void booksWithMoreCountPages() {
        List<User> users = generateUsers();
        List<Book> books = generateBooks();
        Library library = new Library(users, books, Collections.emptyList());
        LibraryStatistic statistic = new LibraryStatistic();
        List<Book> page1184result = statistic.booksWithMoreCountPages(library, 1184);
        List<Book> page0result = statistic.booksWithMoreCountPages(library, 0);

        assertEquals(2, page1184result.size());
        assertTrue(page1184result.contains(getBookByName(books, "Сандро из Чегема")));
        assertTrue(page1184result.contains(getBookByName(books, "Ведьмак")));

        assertEquals(books.size(), page0result.size());
    }

    @Test
    public void mostPopularAuthorInGenre() {
        List<User> users = generateUsers();
        List<Book> books = generateBooks();

        LocalDateTime mashaTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 5, 0, 0, 0);
        List<ArchivedData> dataMasha = new ArrayList<>();
        dataMasha.addAll(generateArchiveData(getUserByName(users, "Маша"),
                Stream.of(getBookByName(books, "Капитанская дочка"),
                        getBookByName(books, "Властелин колец"),
                        getBookByName(books, "Звездолет «Иосиф Сталин». На взлет!"),
                        getBookByName(books, "Философия Java")).collect(Collectors.toList()),
                Timestamp.valueOf(mashaTakeDate), null));

        LocalDateTime faruhTakeDate = LocalDateTime.of(2015, Month.SEPTEMBER, 17, 0, 0, 0);
        List<ArchivedData> dataFaruh = new ArrayList<>();
        dataFaruh.addAll(generateArchiveData(getUserByName(users, "Фарух"),
                Stream.of(getBookByName(books, "Java Concurrency на практике"),
                        getBookByName(books, "100 великих славян"),
                        getBookByName(books, "Тайна третьей планеты"),
                        getBookByName(books, "Герой нашего времени")).collect(Collectors.toList()),
                Timestamp.valueOf(faruhTakeDate), null));

        List<ArchivedData> allArchivedData = new ArrayList<>();
        allArchivedData.addAll(dataMasha);
        allArchivedData.addAll(dataFaruh);

        Library library = new Library(users, books, allArchivedData);
        LibraryStatistic statistic = new LibraryStatistic();
        Map<Genre, String> result = statistic.mostPopularAuthorInGenre(library);

        assertEquals(Genre.values().length, result.size());
        assertEquals("Author not determined", result.get(Genre.NOVEL));
        assertEquals("Лервмонтов", result.get(Genre.ADVENTURE));
        assertEquals("Булычев", result.get(Genre.FANTASY));
        assertEquals("Бобров", result.get(Genre.HISTORY));
        assertEquals("Блох", result.get(Genre.TECHNICAL));

    }

    private List<ArchivedData> generateArchiveData(User user, List<Book> bookList, Timestamp take, Timestamp returned) {
        List<ArchivedData> list = new ArrayList<>(bookList.size());
        bookList.forEach(book -> list.add(new ArchivedData(user, book, take, returned)));

        return list;
    }

    private List<ArchivedData> generateArchiveData(User user, List<Book> bookList, Genre genre, Timestamp take, Timestamp returned) {
        List<ArchivedData> list = new ArrayList<>();
        bookList.stream().filter(book -> book.getGenre().equals(genre)).forEach(book -> list.add(new ArchivedData(user, book, take, returned)));
        return list;
    }

    private static List<User> generateUsers() {
        List<User> list = new ArrayList<>();
        list.add(new User("Маша", 20, null, 0));
        list.add(new User("Петя", 23, null, 0));
        list.add(new User("Фарух", 37, null, 0));

        return list;
    }

    private User getUserByName(List<User> users, String name) {
        return users.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

    private Book getBookByName(List<Book> books, String bookName) {
        return books.stream().filter(book -> book.getName().equals(bookName)).findFirst().orElse(null);
    }

    private List<Book> generateBooks() {
        List<Book> list = new ArrayList<>();
        list.add(new Book("Машина времени", "Уэллс", Genre.NOVEL, 352));
        list.add(new Book("Превращение", "Кафка", Genre.NOVEL, 288));
        list.add(new Book("Смерть в Венеции", "Манн", Genre.NOVEL, 576));
        list.add(new Book("Сандро из Чегема", "Искандер", Genre.NOVEL, 1184));
        list.add(new Book("Декамерон", "Боккаччо", Genre.NOVEL, 800));
        list.add(new Book("Спеши любить", "Спаркс", Genre.NOVEL, 224));
        list.add(new Book("За рекой, в тени деревьев", "Хемингуэй", Genre.NOVEL, 320));

        list.add(new Book("Капитанская дочка", "Пушкин", Genre.ADVENTURE, 256));
        list.add(new Book("Герой нашего времени", "Лервмонтов", Genre.ADVENTURE, 423));

        list.add(new Book("Властелин колец", "Толкин", Genre.FANTASY, 752));
        list.add(new Book("Ведьмак", "Сапковский", Genre.FANTASY, 1340));
        list.add(new Book("Тайна третьей планеты", "Булычев", Genre.FANTASY, 288));
        list.add(new Book("Салярис", "Лем", Genre.FANTASY, 288));
        list.add(new Book("Дюна", "Герберт", Genre.FANTASY, 736));

        list.add(new Book("Звездолет «Иосиф Сталин». На взлет!", "Перемолотов", Genre.HISTORY, 384));
        list.add(new Book("Позывной: «Колорад». Наш человек Василий Сталин", "Большаков", Genre.HISTORY, 352));
        list.add(new Book("100 великих славян", "Бобров", Genre.HISTORY, 416));
        list.add(new Book("Гиперборея. Исторические корни русского народа", "Демин", Genre.HISTORY, 642));

        list.add(new Book("Философия Java", "Эккель", Genre.TECHNICAL, 640));
        list.add(new Book("Чистый код", "Мартин", Genre.TECHNICAL, 464));
        list.add(new Book("Потоковая обработка данных с Apache Flink", "Фабиан", Genre.TECHNICAL, 298));
        list.add(new Book("Java Concurrency на практике", "Блох", Genre.TECHNICAL, 464));
        list.add(new Book("Java. Эффективное программирование", "Блох", Genre.TECHNICAL, 464));
        list.add(new Book("System Design. Подготовка к сложному интервью", "Сюй", Genre.TECHNICAL, 304));

        return list;
    }

}