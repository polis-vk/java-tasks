package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SearchInTheShredderListTest {

    @Test
    public void testPositionPartString() {
        SearchInTheShredderList list = generate();
        int[] result = list.positionPartString("кобольд");
        assertArrayEquals(new int[]{28, 1}, result);

        result = list.positionPartString("предпринимательство");
        assertArrayEquals(new int[]{15, 19}, result);

        result = list.positionPartString("жаворонок");
        assertArrayEquals(new int[]{23, 0}, result);

        result = list.positionPartString("мама");
        assertArrayEquals(new int[]{32, 33}, result);
    }

    @Test
    public void testGetByIndex() {
        SearchInTheShredderList list = generate();
        String result = list.get(28) + list.get(1);
        assertEquals("кобольд", result);

        result = list.get(15) + list.get(19);
        assertEquals("предпринимательство", result);

        result = list.get(23) + list.get(0);
        assertEquals("жаворонок", result);

        result = list.get(32) + list.get(33);
        assertEquals("мама", result);

    }

    @Test
    public void testPositionPartStringWithNull() {
        SearchInTheShredderList list = generate();
        assertNull(list.positionPartString(null));
        assertNull(list.positionPartString(""));
        assertNull(list.positionPartString("рикша"));
        assertNull(list.positionPartString("троглодит"));
    }

    private SearchInTheShredderList generate() {
        //"кобольд", "триппер", "предпринимательство",
        //            "клювонос", "счерчивание", "тёска", "мошенница", "косость", "велорикша", "агамия",
        //            "жаворонок", "тоскливость", "трансформировка", "пронзительность", "уставший", "шары", "тото"
        List<String> data = Arrays.asList("ронок", "ольд", "ельность", "уста","рмировка", "ость","ивость","кос","вело",
            "моше","рикша","нница","счерч","ппер","трансфо","предприни","три", "клюв", "ска", "мательство", "ага", "онос",
            "мия", "жаво", "пронзит", "тё", "ивание", "тоскл", "коб", "вший", "ры", "ша", "ма","ма");
        return new SearchInTheShredderList(data);

    }

}