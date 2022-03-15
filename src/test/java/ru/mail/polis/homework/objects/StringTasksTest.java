package ru.mail.polis.homework.objects;

import org.junit.Test;
import ru.mail.polis.homework.objects.StringTasks;

import static org.junit.Assert.assertEquals;

public class StringTasksTest {

    @Test
    public void valueOf() {
        assertEquals(null, StringTasks.valueOf(null));
        assertEquals(null, StringTasks.valueOf(""));
        assertEquals(0, StringTasks.valueOf("0"));
        assertEquals(0, StringTasks.valueOf("a0"));
        assertEquals(-1, StringTasks.valueOf("-a1"));
        assertEquals(null, StringTasks.valueOf("--a1"));
        assertEquals(null, StringTasks.valueOf("1.23-a1"));
        assertEquals(-12, StringTasks.valueOf("-a1bsc2"));
        assertEquals(-132, StringTasks.valueOf("a-132a"));
        assertEquals(-2147483648, StringTasks.valueOf("a-d21s474asd83648sad"));
        assertEquals(2147483649L, StringTasks.valueOf("asddgsdf2fg1474asdfg8364asd9asd"));
        assertEquals(-2147483649L, StringTasks.valueOf("asd-dgsdf2fg1474asdfg8364asd9asd"));
        assertEquals(1e2, StringTasks.valueOf("1e2"));
        assertEquals(-1e-2, StringTasks.valueOf("asd-asd1asddecvxv-dsf2fsdv"));
        assertEquals(1e-3, StringTasks.valueOf("1e-asdsad3"));
        assertEquals(null, StringTasks.valueOf("1-easdsad3"));
        assertEquals(null, StringTasks.valueOf("-1-easdsad3"));
        assertEquals(null, StringTasks.valueOf("1e--asdsad3"));
        assertEquals(null, StringTasks.valueOf("--1easdsad3"));
        assertEquals(null, StringTasks.valueOf("-1e-asdsad3-"));
        assertEquals(null, StringTasks.valueOf("1easdsad3-"));
        assertEquals(1.2e-3, StringTasks.valueOf("ccz1asd.zc2deg-h3j"));
        assertEquals(1.2e3, StringTasks.valueOf("ccz1asd.zc2degh3j"));
        assertEquals(null, StringTasks.valueOf("ccz1asd.zc2de.g-h3j"));
        assertEquals(1.3, StringTasks.valueOf("fff1fdf.asdsad3"));
        assertEquals(123612736712637.123123112313123123123123e122, StringTasks.valueOf("123612736712637.123123112313123123123123e122"));
        assertEquals(null, StringTasks.valueOf("fff1fdf.asdsad3e"));
    }
}
