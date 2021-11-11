package ru.mail.polis.homework.reflection;

import org.junit.Assert;
import org.junit.Test;

import ru.mail.polis.homework.reflection.objects.medium.Medium;

public class MediumTest extends TestBase {

    @Test
    public void testNull() {
        Assert.assertEquals("null", ReflectionToStringHelper.reflectiveToString(null));
    }

    @Test
    public void testEmptyObject() {
        Empty object = new Empty();
        Assert.assertEquals("{}", ReflectionToStringHelper.reflectiveToString(object));
    }

    @Test
    public void testDefault() {
        Medium object = new Medium();
        testToString(object, "{age: 25, name: John Doe}");
    }

    @Test
    public void testSimple() {
        Medium object = new Medium(10, "Ivan Ivanov", 40.0);
        testToString(object, "{age: 10, name: Ivan Ivanov}");
    }

    @Test
    public void testNullField() {
        Medium object = new Medium(10, null, 40.0);
        testToString(object, "{age: 10, name: null}");
    }

    @Test
    public void testEmptyName() {
        Medium object = new Medium(10, "", 40.0);
        testToString(object, "{age: 10, name: }");
    }

    private static class Empty {
    }
}
