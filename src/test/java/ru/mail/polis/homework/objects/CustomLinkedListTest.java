package ru.mail.polis.homework.objects;


import org.junit.Assert;
import org.junit.Test;

public class CustomLinkedListTest {

    private static final int[] DEFAULT_ARRAY = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final int[] ANOTHER_ARRAY = new int[]{1000111, 200021920, 303404328};


    @Test
    public void testAdd() {
        CustomLinkedList listForDefaultArray = new CustomLinkedList();
        for (int i : DEFAULT_ARRAY) {
            listForDefaultArray.add(i);
        }
        Assert.assertEquals("9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> null", listForDefaultArray.toString());

        CustomLinkedList listForAnotherArray = new CustomLinkedList();
        for (int i : ANOTHER_ARRAY) {
            listForAnotherArray.add(i);
        }
        Assert.assertEquals("303404328 -> 200021920 -> 1000111 -> null", listForAnotherArray.toString());
    }

    @Test
    public void testRemoveElement() {
        CustomLinkedList listForDefaultArray = new CustomLinkedList();
        for (int i : DEFAULT_ARRAY) {
            listForDefaultArray.add(i);
        }
        listForDefaultArray.removeElement(0);
        listForDefaultArray.removeElement(4);
        Assert.assertEquals("8 -> 7 -> 6 -> 5 -> 3 -> 2 -> 1 -> null", listForDefaultArray.toString());

        CustomLinkedList listForAnotherArray = new CustomLinkedList();
        for (int i : ANOTHER_ARRAY) {
            listForAnotherArray.add(i);
        }
        listForAnotherArray.removeElement(0);
        listForAnotherArray.removeElement(1);
        Assert.assertEquals("200021920 -> null", listForAnotherArray.toString());

        // В данном случае исключения сглатываются и тесты проходят, как для пустого списка
        // Код ошибки выводится в stacktrace
        CustomLinkedList listForEmptyArray = new CustomLinkedList();
        listForEmptyArray.removeElement(0);
        listForEmptyArray.removeElement(3);
        Assert.assertEquals("null", listForEmptyArray.toString());
    }

    @Test
    public void testRevertList() {
        CustomLinkedList listForDefaultArray = new CustomLinkedList();
        for (int i : DEFAULT_ARRAY) {
            listForDefaultArray.add(i);
        }
        listForDefaultArray.revertList();
        Assert.assertEquals("1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> null", listForDefaultArray.toString());

        CustomLinkedList listForAnotherArray = new CustomLinkedList();
        for (int i : ANOTHER_ARRAY) {
            listForAnotherArray.add(i);
        }
        listForAnotherArray.revertList();
        Assert.assertEquals("1000111 -> 200021920 -> 303404328 -> null", listForAnotherArray.toString());

        CustomLinkedList listForEmptyArray = new CustomLinkedList();
        listForEmptyArray.revertList();
        Assert.assertEquals("null", listForEmptyArray.toString());
    }


    @Test
    public void testTestToString() {
        CustomLinkedList listForDefaultArray = new CustomLinkedList();
        for (int i : DEFAULT_ARRAY) {
            listForDefaultArray.add(i);
        }
        Assert.assertEquals("9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> null", listForDefaultArray.toString());

        CustomLinkedList listForAnotherArray = new CustomLinkedList();
        for (int i : ANOTHER_ARRAY) {
            listForAnotherArray.add(i);
        }
        Assert.assertEquals("303404328 -> 200021920 -> 1000111 -> null", listForAnotherArray.toString());

        CustomLinkedList listForEmptyArray = new CustomLinkedList();
        Assert.assertEquals("null", listForEmptyArray.toString());
    }
}