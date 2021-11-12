package ru.mail.polis.homework.reflection;

import org.junit.Test;

import ru.mail.polis.homework.reflection.objects.hard.Gender;
import ru.mail.polis.homework.reflection.objects.hard.Hard;
import ru.mail.polis.homework.reflection.objects.medium.Medium;

public class HardTest extends TestBase {

    @Test
    public void testNullFields() {
        Hard batman = new Hard(12, null, null, 75.0, null, null, 100);
        testToString(batman, "{bestFriend: null, gender: null, name: null, nicknames: null, age: 12}");
    }

    @Test
    public void testEmptyNicknames() {
        Hard batman = new Hard(12, "Ivan Ivanov", new String[] {}, 75.0, null, Gender.MALE, 100);
        testToString(batman, "{bestFriend: null, gender: MALE, name: Ivan Ivanov, nicknames: [], age: 12}");
    }

    @Test
    public void testBatman() {
        Medium robin = new Medium(24, "Dick Grayson", 65.0);
        Hard batman = new Hard(33, "Bruce Wayne", new String[] { "Batman" }, 75.0, robin, Gender.MALE, 100);
        testToString(batman, "{bestFriend: {age: 24, name: Dick Grayson}, gender: MALE, name: Bruce Wayne, nicknames: [Batman], age: 33}");
    }

    @Test
    public void testAbracadabra() {
        Medium abaca = new Medium(21, "Abaca Dacaba", 57.0);
        Hard abra = new Hard(24, "Abra Cadabra", new String[] { "abracadabra", null, "", "a" }, 60.0, abaca, Gender.FEMALE, 100);
        testToString(abra, "{bestFriend: {age: 21, name: Abaca Dacaba}, gender: FEMALE, name: Abra Cadabra, nicknames: [abracadabra, null, , a], age: 24}");
    }
}
