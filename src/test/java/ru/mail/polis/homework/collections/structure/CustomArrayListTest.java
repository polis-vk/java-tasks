package ru.mail.polis.homework.collections.structure;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class CustomArrayListTest {
    CustomArrayList<Integer> myList = new CustomArrayList<>();
    List<Integer> realList = new ArrayList<>();

    @Test
    public void testAllAdding() {
        myList.add(1);
        myList.add(2);
        myList.add(2, 3);
        myList.addAll(3, Arrays.asList(4));
    }

    @Test
    public void testSize() {
        fillArray(myList);
        fillArray(realList);
        assertEquals(realList.size(), myList.size());

        myList.remove(0);
        realList.remove(0);
        assertEquals(realList.size(), myList.size());

        myList.add(1);
        realList.add(1);
        assertEquals(realList.size(), myList.size());

        myList.add(0, 3);
        realList.add(0, 3);
        assertEquals(realList.size(), myList.size());

        myList.addAll(Arrays.asList(5, 7));
        realList.addAll(Arrays.asList(5, 7));
        assertEquals(realList.size(), myList.size());

        myList.addAll(myList.size(), Arrays.asList(9, 11));
        realList.addAll(realList.size(), Arrays.asList(9, 11));
        assertEquals(realList.size(), myList.size());

        assertTrue(same(myList, realList));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(myList.isEmpty());
        fillArray(myList);
        assertFalse(myList.isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBounceException() {
        fillArray(myList);
        myList.addAll(myList.size() + 1, Collections.singleton(1));
    }

    @Test
    public void testContains() {
        fillArray(myList);
        assertTrue(myList.contains(2));
        assertFalse(myList.contains(1));
    }

    @Test
    public void testIterator() {
        fillArray(myList);
        Iterator<Integer> iterator = myList.iterator();
        while (iterator.hasNext()) {
            assertFalse(Integer.toString(iterator.next()).isEmpty());
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testConcurrentModificationException() {
        myList.add(1);
        myList.add(2);

        Iterator<Integer> iterator = myList.iterator();
        iterator.next();
        myList.add(3);
        iterator.next();
    }

    @Test
    public void testToArray() {
        fillArray(myList);
        fillArray(realList);
        assertTrue(same(myList, realList));
    }

    @Test
    public void testToArrayWithParameters() {
        fillArray(myList);
        fillArray(realList);
        Object[] objects = myList.toArray(new Object[0]);
        for (Object obj : objects) {
            assertFalse(((Integer) obj).toString().isEmpty());
        }
    }

    @Test(expected = ArrayStoreException.class)
    public void testArrayStoreException() {
        fillArray(myList);
        String[] strings = myList.toArray(new String[0]);
    }

    @Test
    public void testRemoveAndRetain() {
        fillArray(myList);
        fillArray(realList);

        List<Integer> temp = new ArrayList<>();
        temp.add(2);
        temp.add(8);

        assertTrue(myList.retainAll(temp) == realList.retainAll(temp) && same(myList, realList));
        assertTrue(myList.removeAll(temp) == realList.removeAll(temp) && same(myList, realList));

        fillArray(myList);
        fillArray(realList);
        assertTrue(myList.remove(2).equals(realList.remove(2)) && same(myList, realList));

        Integer num = 7;
        myList.add(num);
        realList.add(num);
        assertTrue(myList.remove(num) == (realList.remove(num)) && same(myList, realList));
    }

    @Test
    public void testClear() {
        fillArray(myList);
        fillArray(realList);
        myList.clear();
        realList.clear();
        assertTrue(same(myList, realList));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSubList() {
        fillArray(myList);
        fillArray(realList);
        List<Integer> mySubList = myList.subList(1, myList.size() - 1);
        List<Integer> realSubList = realList.subList(1, realList.size() - 1);
        assertTrue(same(mySubList, realSubList));
    }

    @Test
    public void testGetAndSet() {
        fillArray(myList);
        fillArray(realList);

        assertEquals(realList.get(realList.size() - 1), myList.get(myList.size() - 1));
        assertEquals(realList.set(0, 3), myList.set(0, 3));
        assertTrue(same(myList, realList));
    }

    @Test
    public void testIndexOf() {
        fillArray(myList);
        fillArray(realList);
        Integer num = 0;
        myList.add(num);
        realList.add(num);

        assertEquals(realList.indexOf(num), myList.indexOf(num));
        assertEquals(realList.lastIndexOf(num), myList.lastIndexOf(num));
        assertEquals(realList.indexOf(6), myList.indexOf(6));
        assertEquals(realList.lastIndexOf(6), myList.lastIndexOf(6));
    }


    private void fillArray(List<Integer> list) {
        for (int i = 0; i < 15; i = i + 2) {
            list.add(i);
        }
    }

    public static <T, R extends T> boolean same(Collection<T> c, Collection<R> c1) {
        Object[] objects = c.toArray();
        Object[] objects1 = c1.toArray();
        if (objects1.length != objects.length) {
            return false;
        }
        for (int i = 0; i < objects.length; i++) {
            if (!objects1[i].equals(objects[i])) return false;
        }
        return true;
    }
}