package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomArrayListTest {

    private static final int DEFAULT_SIZE = 16;

    private List<Integer> myArrayList;
    private List<Integer> originalArrayList;

    @Before
    public void setUp() {
        myArrayList = new CustomArrayList<>();
        originalArrayList = new ArrayList<>();
    }

    @Test
    public void size() {
        assertEquals(myArrayList.size(), 0);
        fillArray(myArrayList);
        assertEquals(myArrayList.size(), DEFAULT_SIZE);
    }

    @Test
    public void isEmpty() {
        assertTrue(myArrayList.isEmpty());
        fillArray(myArrayList);
        assertFalse(myArrayList.isEmpty());
    }

    @Test
    public void contains() {
        fillArray(myArrayList);
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertTrue(myArrayList.contains(i));
        }
        assertFalse(myArrayList.contains(-1));
        assertFalse(myArrayList.contains(16));
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
        fillArray(myArrayList);
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i));
        }
    }

    @Test
    public void removeWithoutIndex() {
        fillArray(myArrayList);
        assertFalse(myArrayList.remove(Integer.valueOf(123)));
        assertTrue(myArrayList.remove(Integer.valueOf(7)));
        for (int i = 0; i < 7; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i));
        }
        for (int i = 7; i < DEFAULT_SIZE - 1; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i + 1));
        }
    }

    @Test
    public void containsAll() {
        fillArray(myArrayList);
        List<Integer> another = new ArrayList<>();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            another.add(i);
        }
        assertTrue(myArrayList.containsAll(another));
        assertFalse(myArrayList.containsAll(List.of(-1, 4, 2)));
    }

    @Test
    public void testAddAllWithoutIndex() {
        fillArray(myArrayList);
        assertEquals(DEFAULT_SIZE, myArrayList.size());

        List<Integer> testList = List.of(-1, -2, -3);
        assertTrue(myArrayList.addAll(testList));

        assertEquals(DEFAULT_SIZE + 3, myArrayList.size());
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i));
        }
        for (int i = DEFAULT_SIZE; i < DEFAULT_SIZE + 3; i++) {
            assertEquals(myArrayList.get(i), testList.get(i - DEFAULT_SIZE));
        }
    }

    @Test
    public void testAddAllWithIndex() {
        fillArray(myArrayList);
        assertEquals(DEFAULT_SIZE, myArrayList.size());

        List<Integer> testList = List.of(-1, -2, -3);
        assertTrue(myArrayList.addAll(3, testList));

        assertEquals(DEFAULT_SIZE + 3, myArrayList.size());
        for (int i = 0; i < 2; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i));
        }
        for (int i = 3; i < 6; i++) {
            assertEquals(myArrayList.get(i), testList.get(i - 3));
        }
        for (int i = 6; i < DEFAULT_SIZE + 3; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i - 3));
        }
    }

    @Test
    public void removeAll() {
        List<Integer> testList = new ArrayList<>(List.of(1,2,3,4,4,3,2,1,1,2,3,4,3,2,1,4,2,3,4,1,2,3,4));
        myArrayList.addAll(testList);
        assertEquals(myArrayList.size(), testList.size());

        assertTrue(myArrayList.removeAll(List.of(1, 2)));
        testList.removeAll(List.of(1, 2));

        assertEquals(myArrayList.size(), testList.size());
        for (int i = 0; i < myArrayList.size(); i++) {
            assertEquals(myArrayList.get(i), testList.get(i));
        }
    }

    @Test
    public void retainAll() {
        fillArray(myArrayList);
        fillArray(originalArrayList);
        assertEquals(myArrayList.size(), originalArrayList.size());

        assertTrue(myArrayList.retainAll(List.of(4, 5, 6)));
        originalArrayList.retainAll(List.of(4, 5, 6));

        assertEquals(myArrayList.size(), originalArrayList.size());
        for (int i = 0; i < myArrayList.size(); i++) {
            assertEquals(myArrayList.get(i), originalArrayList.get(i));
        }
    }

    @Test
    public void clear() {
        // TODO.
    }

    @Test
    public void get() {
        fillArray(myArrayList);
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i));
        }
    }

    @Test
    public void set() {
        fillArray(myArrayList);
        for (int i = 0; i < DEFAULT_SIZE; i += 2) {
            myArrayList.set(i, i * i);
        }
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i % 2 == 0 ? i * i : i));
        }
    }

    @Test
    public void addWithIndex() {
        fillArray(myArrayList);
        myArrayList.add(2, 123);
        assertEquals(myArrayList.get(0), Integer.valueOf(0));
        assertEquals(myArrayList.get(1), Integer.valueOf(1));
        assertEquals(myArrayList.get(2), Integer.valueOf(123));
        for (int i = 3; i < DEFAULT_SIZE + 1; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i - 1));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addWithIndexThrowsWhenOutOfBounds() {
        fillArray(myArrayList);
        myArrayList.add(DEFAULT_SIZE + 1, 123);
    }

    @Test
    public void removeWithIndex() {
        fillArray(myArrayList);
        assertEquals(myArrayList.size(), DEFAULT_SIZE);
        myArrayList.remove(4);
        assertEquals(myArrayList.size(), DEFAULT_SIZE - 1);
        for (int i = 0; i < 4; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i));
        }
        for (int i = 4; i < DEFAULT_SIZE - 1; i++) {
            assertEquals(myArrayList.get(i), Integer.valueOf(i + 1));
        }
    }

    @Test
    public void indexOf() {
        fillArray(myArrayList);
        assertEquals(myArrayList.indexOf(7), 7);
        myArrayList.set(3, 7);
        assertEquals(myArrayList.indexOf(7), 3);
        assertEquals(myArrayList.indexOf(123), -1);
    }

    @Test
    public void lastIndexOf() {
        fillArray(myArrayList);
        assertEquals(myArrayList.lastIndexOf(7), 7);
        myArrayList.set(10, 7);
        assertEquals(myArrayList.lastIndexOf(7), 10);
        assertEquals(myArrayList.lastIndexOf(123), -1);
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

    private void fillArray(List<Integer> array) {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            array.add(i);
        }
    }
}
