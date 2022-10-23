package ru.mail.polis.homework.collections.structure;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomArrayListTest {
    private static final int[] DEFAULT_ARRAY = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void sizeTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);
        assertEquals(DEFAULT_ARRAY.length, list.size());
    }

    @Test
    public void isEmptyTest() {
        List<Integer> list = new CustomArrayList<>();
        assertTrue(list.isEmpty());

        list.add(1);
        assertFalse(list.isEmpty());
    }

    @Test
    public void containsTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);
        assertTrue(list.contains(1));
        assertFalse(list.contains(null));

        list.add(null);
        assertTrue(list.contains(null));
    }

    @Test
    public void iteratorTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);
        Iterator<Integer> iterator = list.iterator();

        for (int e : DEFAULT_ARRAY) {
            assertEquals(e, (int) iterator.next());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorTest2() {
        List<Integer> list = new CustomArrayList<>();
        list.iterator().next();
    }

    @Test
    public void toArrayTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);
        Object[] objects = list.toArray();

        for (int i = 0; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], objects[i]);
        }
    }

    @Test
    public void toArrayTest2() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);
        Integer[] array = new Integer[DEFAULT_ARRAY.length];

        Integer[] result = list.toArray(array);
        assertTrue(array == result);

        for (int i = 0; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) result[i]);
        }
    }

    @Test
    public void toArrayTest3() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        Integer[] result = list.toArray(new Integer[0]);
        for (int i = 0; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) result[i]);
        }
    }

    @Test
    public void removeTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        list.remove(0);
        list.remove(list.size() - 1);
        for (int i = 1; i < DEFAULT_ARRAY.length - 1; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) list.get(i - 1));
        }
    }

    @Test
    public void containsAllTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        assertTrue(list.containsAll(Collections.singletonList(1)));

        List<Integer> list2 = new CustomArrayList<>();
        list2.add(1);
        list2.add(5);
        list2.add(9);
        assertTrue(list.containsAll(list2));
    }

    @Test
    public void addAllTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        List<Integer> list2 = new CustomArrayList<>();
        list2.add(1);
        list2.add(5);
        list2.add(9);
        assertTrue(list.addAll(list2));

        for (int i = 0; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) list.get(i));
        }

        for (int i = 0; i < list2.size(); i++) {
            assertEquals(list2.get(i), list.get(DEFAULT_ARRAY.length + i));
        }
    }

    @Test
    public void addAllTest2() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        List<Integer> list2 = new CustomArrayList<>();
        list2.add(1);
        list2.add(5);
        list2.add(9);
        assertTrue(list.addAll(0, list2));

        for (int i = 0; i < list2.size(); i++) {
            assertEquals(list2.get(i), list.get(i));
        }

        for (int i = 0; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) list.get(list2.size() + i));
        }
    }

    @Test
    public void removeAllTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        List<Integer> list2 = new CustomArrayList<>();
        for (int i = 0; i < DEFAULT_ARRAY.length - 1; i++) {
            list2.add(DEFAULT_ARRAY[i]);
        }
        assertTrue(list.removeAll(list2));

        assertEquals(1, list.size());
        assertEquals(9, (int) list.get(0));
    }

    @Test
    public void retainAllTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        list.retainAll(list);
        assertEquals(DEFAULT_ARRAY.length, list.size());
        for (int i = 0; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) list.get(i));
        }

        list.retainAll(Collections.singletonList(1));
        assertEquals(1, list.size());
        assertEquals(1, (int) list.get(0));
    }

    @Test
    public void clearTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        list.add(10);
        assertEquals(10, (int) list.get(0));
    }

    @Test
    public void setTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        list.set(DEFAULT_ARRAY.length - 1, 2);

        for (int i = 0; i < DEFAULT_ARRAY.length - 1; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) list.get(i));
        }
        assertEquals(2, (int) list.get(DEFAULT_ARRAY.length - 1));

        list.set(0, 0);
        assertEquals(0, (int) list.get(0));
    }

    @Test
    public void addToIndexTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        list.add(0, 0);
        assertEquals(0, (int) list.get(0));

        for (int i = 0; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) list.get(i + 1));
        }
    }

    @Test
    public void removeByIndexTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        list.remove(0);
        assertEquals(DEFAULT_ARRAY.length - 1, list.size());

        for (int i = 1; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) list.get(i - 1));
        }
    }

    @Test
    public void indexOfTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);
        list.add(5);

        assertEquals(4, list.indexOf(5));
    }

    @Test
    public void lastIndexOfTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);
        list.add(5);

        assertEquals(DEFAULT_ARRAY.length, list.lastIndexOf(5));
    }

    @Test
    public void listIteratorTest() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        ListIterator<Integer> iterator = list.listIterator();
        iterator.add(10);
        assertEquals(DEFAULT_ARRAY.length + 1, list.size());

        assertEquals(10, (int) list.get(0));

        for (int e : DEFAULT_ARRAY) {
            assertEquals(e, (int) iterator.next());
        }
    }

    @Test
    public void listIteratorTest2() {
        List<Integer> list = new CustomArrayList<>();
        Arrays.stream(DEFAULT_ARRAY).forEach(list::add);

        ListIterator<Integer> iterator = list.listIterator(4);
        iterator.add(10);
        assertEquals(DEFAULT_ARRAY.length + 1, list.size());

        assertEquals(10, (int) list.get(4));

        assertEquals(5, (int) iterator.next());
        for (int i = 5; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], (int) iterator.next());
        }
    }
}
