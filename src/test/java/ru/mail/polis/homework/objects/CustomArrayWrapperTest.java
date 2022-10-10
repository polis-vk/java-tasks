package ru.mail.polis.homework.objects;

import org.junit.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomArrayWrapperTest {

    private static final int[] DEFAULT_ARRAY = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void testOddIterator() {
        CustomArrayWrapper arrayWrapper = generateCustomArrayWrapper(DEFAULT_ARRAY);
        Iterator<Integer> iterator = arrayWrapper.oddIterator();
        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        assertEquals(5, result.size());
        assertValues(DEFAULT_ARRAY, result, false);

    }

    @Test
    public void testDefaultIterator() {
        CustomArrayWrapper arrayWrapper = generateCustomArrayWrapper(DEFAULT_ARRAY);
        Iterator<Integer> iterator = arrayWrapper.iterator();
        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        for (int i = 0; i < DEFAULT_ARRAY.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], result.get(i).intValue());
        }
    }

    @Test
    public void testEvenIterator() {
        CustomArrayWrapper arrayWrapper = generateCustomArrayWrapper(DEFAULT_ARRAY);
        Iterator<Integer> iterator = arrayWrapper.evenIterator();
        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        assertEquals(4, result.size());
        assertValues(DEFAULT_ARRAY, result, true);
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testConcurrentModificationEvenIterator() {
        CustomArrayWrapper arrayWrapper = new CustomArrayWrapper(3);
        Iterator iterator = arrayWrapper.evenIterator();
        arrayWrapper.add(100);
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testConcurrentModificationOddIterator() {
        CustomArrayWrapper arrayWrapper = new CustomArrayWrapper(3);
        Iterator<Integer> iterator = arrayWrapper.oddIterator();
        arrayWrapper.add(100);
        iterator.next();
    }

    private CustomArrayWrapper generateCustomArrayWrapper(int[] array) {
        CustomArrayWrapper arrayWrapper = new CustomArrayWrapper(array.length);
        for (int value : array) {
            arrayWrapper.add(value);
        }
        return arrayWrapper;
    }

    private void assertValues(int[] array, List<Integer> result, boolean isEven) {
        int position = 0;
        if (isEven) {
            position = 1;
        }

        for (int i = 0; position < array.length; i++, position += 2) {
            assertEquals(array[position], result.get(i).intValue());
        }
    }


}

//Есть явное дублирование кода. Even и Odd итераторы явно можно объединить