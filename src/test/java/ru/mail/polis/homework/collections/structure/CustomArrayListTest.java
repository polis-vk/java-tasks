package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomArrayListTest {

    public CustomArrayList<Integer> getList() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        return list;
    }

    public CustomArrayList<Integer> getEmptyList() {
        return new CustomArrayList<>();
    }

    @Test
    public void testSize() {
        List<Integer> list = getList();

        assertEquals(10, list.size());

        list.remove(0);
        assertEquals(9, list.size());

        list.add(20);
        assertEquals(10, list.size());

        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    public void testIsEmpty() {
        List<Integer> list = getList();

        assertFalse(list.isEmpty());

        list.clear();
        assertTrue(list.isEmpty());

        list = getEmptyList();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testContains() {
        List<Integer> list = getList();

        assertTrue(list.contains(0));
        assertTrue(list.contains(5));
        assertTrue(list.contains(9));

        assertFalse(list.contains(-1));
        assertFalse(list.contains(10));
    }

    @Test(expected = NoSuchElementException.class)
    public void testIterator() {
        List<Integer> list = getList();
        Iterator<Integer> iterator = list.iterator();

        assertTrue(iterator.hasNext());

        assertEquals(0, (int) iterator.next());
        assertEquals(1, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertEquals(3, (int) iterator.next());

        list = getEmptyList();
        iterator = list.iterator();

        assertFalse(iterator.hasNext());

        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrent() {
        List<Integer> list = getList();
        Iterator<Integer> iterator = list.iterator();

        iterator.next();

        list.add(1);

        iterator.next();
    }

    @Test
    public void testToArray() {
        List<Integer> list = getList();

        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, list.toArray());
    }

    @Test
    public void testToArrayWithArray() {
        List<Integer> list = getList();

        Integer[] a = new Integer[10];

        list.toArray(a);

        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, a);
    }

    @Test
    public void testAdd() {
        List<Integer> list = getEmptyList();

        assertEquals(0, list.size());

        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(3, list.size());
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(3, (int) list.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testBounds() {
        List<Integer> list = getList();

        list.get(11);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemove() {
        List<Integer> list = getList();

        list.remove(0);
        assertEquals(1, (int) list.get(0));

        list.remove((Integer) 5);
        assertEquals(6, (int) list.get(4));

        list.remove(7);
        assertEquals(8, (int) list.get(6));

        list.remove(7);
    }

    @Test
    public void testContainsAll() {
        List<Integer> list = getList();
        Collection<Integer> collection = new ArrayList<>();

        collection.add(1);
        collection.add(2);
        collection.add(3);

        assertTrue(list.containsAll(collection));

        collection.add(0);

        assertTrue(list.containsAll(collection));

        collection.add(20);

        assertFalse(list.containsAll(collection));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAll() {
        List<Integer> list = getList();
        Collection<Integer> collection = new ArrayList<>();

        assertEquals(10, list.size());

        collection.add(1);
        collection.add(2);
        collection.add(3);

        list.addAll(collection);

        assertEquals(13, list.size());
        assertEquals(1, (int) list.get(10));
        assertEquals(2, (int) list.get(11));
        assertEquals(3, (int) list.get(12));

        list.get(13);
    }

    @Test
    public void testAddAllWithIndex() {
        List<Integer> list = getList();
        Collection<Integer> collection = new ArrayList<>();

        assertEquals(10, list.size());

        collection.add(1);
        collection.add(2);
        collection.add(3);

        list.addAll(5, collection);

        assertEquals(13, list.size());
        assertEquals(1, (int) list.get(5));
        assertEquals(2, (int) list.get(6));
        assertEquals(3, (int) list.get(7));
    }

    @Test
    public void testRemoveAll() {
        List<Integer> list = getList();
        Collection<Integer> collection = new ArrayList<>();

        assertEquals(10, list.size());

        collection.add(1);
        collection.add(2);
        collection.add(3);

        list.removeAll(collection);

        assertEquals(7, list.size());
        assertEquals(4, (int) list.get(1));
    }

    @Test
    public void testRetainAll() {
        List<Integer> list = getList();
        Collection<Integer> collection = new ArrayList<>();

        assertEquals(10, list.size());

        collection.add(1);
        collection.add(2);
        collection.add(3);

        list.retainAll(collection);

        assertEquals(3, list.size());
        assertEquals(2, (int) list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testClear() {
        List<Integer> list = getList();

        assertEquals(10, list.size());

        list.clear();

        assertEquals(0, list.size());

        assertTrue(list.isEmpty());

        list.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet() {
        List<Integer> list = getList();

        assertEquals(0, (int) list.get(0));
        assertEquals(1, (int) list.get(1));
        assertEquals(2, (int) list.get(2));
        assertEquals(3, (int) list.get(3));

        list.get(10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSet() {
        List<Integer> list = getList();

        list.set(0, 20);
        list.set(2, 30);

        assertEquals(20, (int) list.get(0));
        assertEquals(1, (int) list.get(1));
        assertEquals(30, (int) list.get(2));

        list.set(10, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddWithIndex() {
        List<Integer> list = getList();

        assertEquals(10, list.size());

        list.add(0, 20);

        assertEquals(11, list.size());
        assertEquals(20, (int) list.get(0));
        assertEquals(0, (int) list.get(1));

        list.add(11, 30);
        assertEquals(9, (int) list.get(10));
        assertEquals(30, (int) list.get(11));

        list.add(13, 20);
    }

    @Test
    public void testRemoveByObject() {
        List<Integer> list = getList();

        assertEquals(10, list.size());

        assertTrue(list.remove((Integer) 1));
        assertTrue(list.remove((Integer) 2));

        assertEquals(8, list.size());

        assertFalse(list.remove((Integer) 10));
    }

    @Test
    public void testIndexOf() {
        List<Integer> list = getList();

        assertEquals(0, list.indexOf(0));
        assertEquals(-1, list.indexOf(10));
    }

    @Test
    public void testLastIndexOf() {
        List<Integer> list = getList();

        list.add(10, 0);

        assertEquals(10, list.lastIndexOf(0));
        assertEquals(-1, list.indexOf(10));
    }

    @Test(expected = IllegalStateException.class)
    public void testListIterator() {
        List<Integer> list = getList();
        ListIterator<Integer> listIterator = list.listIterator();

        assertTrue(listIterator.hasNext());

        assertFalse(listIterator.hasPrevious());

        int a = listIterator.next();
        assertEquals(a, (int) listIterator.previous());

        listIterator.remove();

        assertEquals(1, (int) listIterator.next());

        listIterator.add(5);

        assertEquals(5, (int) listIterator.previous());

        listIterator.set(20);

        assertEquals(20, (int) listIterator.next());

        listIterator.set(10);
        listIterator.set(11);
    }

    @Test(expected = NoSuchElementException.class)
    public void testListIteratorWithIndex() {
        List<Integer> list = getList();
        ListIterator<Integer> listIterator = list.listIterator(10);

        listIterator.add(10);
        listIterator.add(11);

        assertEquals(12, list.size());
        assertEquals(11, (int) listIterator.previous());
        assertEquals(10, (int) listIterator.previous());

        listIterator.previous();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testListIteratorWithIndexConcurrency() {
        List<Integer> list = getList();
        ListIterator<Integer> listIterator = list.listIterator(10);

        listIterator.add(10);
        listIterator.add(11);

        assertEquals(12, list.size());
        assertEquals(11, (int) listIterator.previous());
        assertEquals(10, (int) listIterator.previous());

        list.add(10);

        listIterator.next();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubList() {
        List<Integer> list = getList();

        List<Integer> subList = list.subList(0, 5);

        assertEquals(5, subList.size());

        assertEquals(0, (int) subList.get(0));
        assertEquals(1, (int) subList.get(1));
        assertEquals(2, (int) subList.get(2));
        assertEquals(3, (int) subList.get(3));
        assertEquals(4, (int) subList.get(4));

        subList = list.subList(9, 10);

        assertEquals(1, subList.size());
        assertEquals(9, (int) subList.get(0));

        subList = list.subList(10, 11);
    }
}