package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomArrayListTest {

    private static final int DEFAULT_SIZE = 16;

    private CustomArrayList<Integer> array;

    @Before
    public void setUp() {
        array = new CustomArrayList<>();
    }

    @Test
    public void size() {
        assertEquals(array.size(), 0);
        fillArray(array);
        assertEquals(array.size(), DEFAULT_SIZE);
    }

    @Test
    public void isEmpty() {
        assertTrue(array.isEmpty());
        fillArray(array);
        assertFalse(array.isEmpty());
    }

    @Test
    public void contains() {
        fillArray(array);
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertTrue(array.contains(i));
        }
        assertFalse(array.contains(-1));
        assertFalse(array.contains(16));
    }

    @Test
    public void iterator() {
        // TODO.
    }

    @Test
    public void toArray() {
        // TODO.
    }

    @Test
    public void testToArray() {
        // TODO.
    }

    @Test
    public void addWithoutIndex() {
        fillArray(array);
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertEquals(array.get(i), Integer.valueOf(i));
        }
    }

    @Test
    public void removeWithoutIndex() {
        fillArray(array);
        assertFalse(array.remove(Integer.valueOf(123)));
        assertTrue(array.remove(Integer.valueOf(7)));
        for (int i = 0; i < 7; i++) {
            assertEquals(array.get(i), Integer.valueOf(i));
        }
        for (int i = 7; i < DEFAULT_SIZE - 1; i++) {
            assertEquals(array.get(i), Integer.valueOf(i + 1));
        }
    }

    @Test
    public void containsAll() {
        fillArray(array);
        List<Integer> another = new ArrayList<>();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            another.add(i);
        }
        assertTrue(array.containsAll(another));
        assertFalse(array.containsAll(List.of(-1, 4, 2)));
    }

    @Test
    public void testAddAllWithoutIndex() {
        fillArray(array);
        assertEquals(DEFAULT_SIZE, array.size());

        List<Integer> testList = List.of(-1, -2, -3);
        assertTrue(array.addAll(testList));

        assertEquals(DEFAULT_SIZE + 3, array.size());
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertEquals(array.get(i), Integer.valueOf(i));
        }
        for (int i = DEFAULT_SIZE; i < DEFAULT_SIZE + 3; i++) {
            assertEquals(array.get(i), testList.get(i - DEFAULT_SIZE));
        }
    }

    @Test
    public void testAddAllWithIndex() {
        fillArray(array);
        assertEquals(DEFAULT_SIZE, array.size());

        List<Integer> testList = List.of(-1, -2, -3);
        assertTrue(array.addAll(3, testList));

        assertEquals(DEFAULT_SIZE + 3, array.size());
        for (int i = 0; i < 2; i++) {
            assertEquals(array.get(i), Integer.valueOf(i));
        }
        for (int i = 3; i < 6; i++) {
            assertEquals(array.get(i), testList.get(i - 3));
        }
        for (int i = 6; i < DEFAULT_SIZE + 3; i++) {
            assertEquals(array.get(i), Integer.valueOf(i - 3));
        }
    }

    @Test
    public void removeAll() {
        // TODO.
    }

    @Test
    public void retainAll() {
        // TODO.
    }

    @Test
    public void clear() {
        // TODO.
    }

    @Test
    public void get() {
        fillArray(array);
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertEquals(array.get(i), Integer.valueOf(i));
        }
    }

    @Test
    public void set() {
        fillArray(array);
        for (int i = 0; i < DEFAULT_SIZE; i += 2) {
            array.set(i, i * i);
        }
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertEquals(array.get(i), Integer.valueOf(i % 2 == 0 ? i * i : i));
        }
    }

    @Test
    public void addWithIndex() {
        fillArray(array);
        array.add(2, 123);
        assertEquals(array.get(0), Integer.valueOf(0));
        assertEquals(array.get(1), Integer.valueOf(1));
        assertEquals(array.get(2), Integer.valueOf(123));
        for (int i = 3; i < DEFAULT_SIZE + 1; i++) {
            assertEquals(array.get(i), Integer.valueOf(i - 1));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addWithIndexThrowsWhenOutOfBounds() {
        fillArray(array);
        array.add(DEFAULT_SIZE + 1, 123);
    }

    @Test
    public void removeWithIndex() {
        fillArray(array);
        assertEquals(array.size(), DEFAULT_SIZE);
        array.remove(4);
        assertEquals(array.size(), DEFAULT_SIZE - 1);
        for (int i = 0; i < 4; i++) {
            assertEquals(array.get(i), Integer.valueOf(i));
        }
        for (int i = 4; i < DEFAULT_SIZE - 1; i++) {
            assertEquals(array.get(i), Integer.valueOf(i + 1));
        }
    }

    @Test
    public void indexOf() {
        fillArray(array);
        assertEquals(array.indexOf(7), 7);
        array.set(3, 7);
        assertEquals(array.indexOf(7), 3);
        assertEquals(array.indexOf(123), -1);
    }

    @Test
    public void lastIndexOf() {
        fillArray(array);
        assertEquals(array.lastIndexOf(7), 7);
        array.set(10, 7);
        assertEquals(array.lastIndexOf(7), 10);
        assertEquals(array.lastIndexOf(123), -1);
    }

    @Test
    public void listIterator() {
        // TODO.
    }

    @Test
    public void testListIterator() {
        // TODO.
    }

    @Test
    public void subList() {
        // TODO.
    }

    private void fillArray(CustomArrayList<Integer> array) {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            array.add(i);
        }
    }
}
