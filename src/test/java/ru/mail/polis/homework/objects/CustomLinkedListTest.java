package ru.mail.polis.homework.objects;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

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
    public void testToStringWithEmptyList() {
        CustomLinkedList list = new CustomLinkedList();
        assertEquals("null", list.toString());
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
}