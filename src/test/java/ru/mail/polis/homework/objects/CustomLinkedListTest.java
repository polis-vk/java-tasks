package ru.mail.polis.homework.objects;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomLinkedListTest {

    private static final int[] DEFAULT_ARRAY = new int[]{5, 3, 7, 4, 5};

    @Test
    public void testAdd() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        assertEquals(generateString(DEFAULT_ARRAY), list.toString());
        list.add(9);
        assertEquals(generateString(new int[]{5, 3, 7, 4, 5, 9}), list.toString());
        assertEquals(6, list.size());
    }

    @Test
    public void testAddByIndex() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        assertEquals(generateString(DEFAULT_ARRAY), list.toString());
        list.add(3, 9);
        assertEquals(generateString(new int[]{5, 3, 7, 9, 4, 5}), list.toString());
        assertEquals(6, list.size());

        list.add(0, 1);
        assertEquals(generateString(new int[]{1, 5, 3, 7, 9, 4, 5}), list.toString());
        assertEquals(7, list.size());


        list.add(7, 1);
        assertEquals(generateString(new int[]{1, 5, 3, 7, 9, 4, 5, 1}), list.toString());
        assertEquals(8, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddByIndex_withException() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        assertEquals(generateString(DEFAULT_ARRAY), list.toString());
        list.add(3, 9);
        assertEquals(generateString(new int[]{5, 3, 7, 9, 4, 5}), list.toString());
        assertEquals(6, list.size());

        list.add(7, 1);
    }

    @Test
    public void testAddByIndex_checkSizeAfterException() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        assertEquals(generateString(DEFAULT_ARRAY), list.toString());
        assertEquals(5, list.size());
        try {
            list.add(7, 1);
        } catch (IndexOutOfBoundsException e) {
            assertEquals(5, list.size());
        }
    }

    @Test
    public void testGet() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        assertEquals(5, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(7, list.get(2));
        assertEquals(4, list.get(3));
        assertEquals(5, list.get(4));

        assertEquals(generateString(DEFAULT_ARRAY), list.toString());
        assertEquals(5, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_exception() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        list.get(5);
    }

    @Test
    public void removeElement() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        list.removeElement(2);
        assertEquals(generateString(new int[]{5, 3, 4, 5}), list.toString());
        assertEquals(4, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeElementWithNegativeIndex() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        list.removeElement(-2);
        assertEquals(generateString(new int[]{5, 3, 4, 5}), list.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeElementFromEmptyList() {
        CustomLinkedList list = new CustomLinkedList();
        assertEquals(0, list.size());
        list.removeElement(0);
    }

    @Test
    public void removeFirstElement() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        list.removeElement(0);
        assertEquals(DEFAULT_ARRAY.length - 1, list.size());
        assertEquals(generateString(new int[]{3, 7, 4, 5}), list.toString());

    }

    @Test
    public void addAfterRemoveLast() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        list.removeElement(list.size() - 1);
        list.add(1);
        assertEquals(generateString(new int[]{5, 3, 7, 4, 1}), list.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexOutOfBounds() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        assertEquals(DEFAULT_ARRAY.length, list.size());
        list.removeElement(DEFAULT_ARRAY.length);
    }

    @Test
    public void revertList() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        list.revertList();

        int[] revertArray = IntStream.rangeClosed(1, DEFAULT_ARRAY.length)
            .map(i -> DEFAULT_ARRAY[DEFAULT_ARRAY.length - i]).toArray();

        assertEquals(generateString(revertArray), list.toString());
        assertEquals(DEFAULT_ARRAY.length, list.size());
    }

    @Test
    public void addToTailAfterRevertList() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        list.revertList();
        list.add(1);
        assertEquals(generateString(new int[]{5, 4, 7, 3, 5, 1}), list.toString());
    }

    @Test
    public void testToStringWithEmptyList() {
        CustomLinkedList list = new CustomLinkedList();
        assertEquals("null", list.toString());
    }

    @Test
    public void testIterator() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        assertEquals(generateString(DEFAULT_ARRAY), list.toString());
        list.add(9);
        iteratorCheck(list.iterator(), new int[]{5, 3, 7, 4, 5, 9});
        iteratorCheck(list.iterator(), new int[]{5, 3, 7, 4, 5, 9});
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorException() {
        CustomLinkedList list = generateCustomLinkedList(DEFAULT_ARRAY);
        assertEquals(generateString(DEFAULT_ARRAY), list.toString());
        list.add(9);
        Iterator<Integer> iterator = list.iterator();
        iteratorCheck(iterator, new int[]{5, 3, 7, 4, 5, 9});
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptyIteratorException() {
        CustomLinkedList list = new CustomLinkedList();
        Iterator<Integer> iterator = list.iterator();
        iteratorCheck(iterator, new int[] {});
        iterator.next();
    }

    private String generateString(int[] data) {
        StringBuilder sb = new StringBuilder();

        if (data.length != 0) {
            for (int value : data) {
                sb.append(value).append(" -> ");
            }
        }

        return sb.append("null").toString();
    }

    private CustomLinkedList generateCustomLinkedList(int[] data) {
        CustomLinkedList list = new CustomLinkedList();
        for (int value : data) {
            list.add(value);
        }
        return list;
    }

    private void iteratorCheck(Iterator<Integer> it, int[] expected) {
        if (expected.length == 0) {
            assertFalse(it.hasNext());
            return;
        }
        for (int j : expected) {
            assertTrue(it.hasNext());
            assertEquals(j, (int) it.next());
        }
        assertFalse(it.hasNext());
    }
}
