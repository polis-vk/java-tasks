package ru.mail.polis.homework.collections.structure;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CustomArrayListTest {

    private static final String[] DEFAULT_ARRAY = new String[]{"String0", "String1", "String2", "String3", "String4",
            "String5", "String6", "String7", "String8", "String9", "String10", "String11", "String12", "String13",
            "String14", "String15"};

    @Test
    public void testSize() {
        CustomArrayList<String> list = generateList();
        assertEquals(16, list.size());

        list.add("Test");
        assertEquals(17, list.size());

        list.removeAll(Arrays.asList("String1", "String2", "String3"));
        assertEquals(14, list.size());

        list.clear();
        assertEquals(0, list.size());

        list.addAll(Arrays.asList("Test", "List"));
        assertEquals(2, list.size());

        list.add(null);
        assertEquals(3, list.size());
    }

    @Test
    public void testIsEmpty() {
        CustomArrayList<String> list = generateEmptyList();
        assertTrue(list.isEmpty());

        list.add("Test");
        assertFalse(list.isEmpty());

        list.addAll(Arrays.asList("List", "isEmpty"));
        assertFalse(list.isEmpty());

        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testContains() {
        CustomArrayList<String> list = generateList();
        assertTrue(list.contains("String1"));
        assertFalse(list.contains("Test"));

        list.clear();
        assertFalse(list.contains("String1"));
        assertFalse(list.contains("String15"));

        list.add("Test");
        assertTrue(list.contains("Test"));

        assertFalse(list.contains(null));
        list.add(null);
        assertTrue(list.contains(null));
    }

    @Test
    public void testIterator() {
        CustomArrayList<String> list = generateList();
        Iterator<String> iterator = list.iterator();
        List<String> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        assertEquals(16, result.size());
        assertValues(DEFAULT_ARRAY, result);
    }

    @Test
    public void testToArray() {
        CustomArrayList<String> list = generateList();
        Object[] array = list.toArray();
        assertArrayEquals(array, DEFAULT_ARRAY);
    }

    @Test
    public void testToArrayGenerics() {
        CustomArrayList<String> list = generateList();
        String[] array = list.toArray(new String[16]);
        assertArrayEquals(array, DEFAULT_ARRAY);
    }

    @Test
    public void testAdd() {
        CustomArrayList<String> list = generateEmptyList();
        assertEquals(0, list.size());

        list.add("Test");
        assertEquals(1,list.size());
        assertEquals("Test", list.get(0));

        list.add("List");
        assertEquals(2,list.size());
        assertArrayEquals(new String[] {"Test", "List"}, list.toArray());

        list.add(1, "Add");
        assertEquals(3,list.size());
        assertArrayEquals(new String[] {"Test", "Add", "List"}, list.toArray());
    }

    @Test
    public void testRemove() {
        CustomArrayList<String> list = generateList();
        assertTrue(list.contains("String0"));
        assertEquals(16, list.size());

        list.remove("String0");
        assertFalse(list.contains("String0"));
        assertEquals(15, list.size());

        list.add(null);
        assertTrue(list.contains(null));
        assertEquals(16, list.size());
        list.remove(null);
        assertFalse(list.contains(null));
        assertEquals(15, list.size());

        assertTrue(list.contains("String8"));
        list.remove(7);
        assertFalse(list.contains("String8"));
        assertEquals(14, list.size());
    }

    @Test
    public void testContainsAll() {
        CustomArrayList<String> list = generateList();
        assertTrue(list.containsAll(Arrays.asList(DEFAULT_ARRAY)));
        assertFalse(list.containsAll(Arrays.asList("Test", "Contains")));
    }

    @Test
    public void testAddAll() {
        CustomArrayList<String> list = generateEmptyList();
        list.addAll(Arrays.asList("Test", "AddAll"));
        assertArrayEquals(new String[] {"Test", "AddAll"}, list.toArray());

        list.addAll(1, Arrays.asList("Array", "List"));
        assertArrayEquals(new String[] {"Test", "Array", "List", "AddAll"}, list.toArray());
    }

    @Test
    public void testRemoveAll() {
        CustomArrayList<String> list = generateList();
        list.add("String0");
        list.add("String15");
        assertEquals(18, list.size());

        list.removeAll(Arrays.asList("String0", "String15"));
        assertEquals(14, list.size());
    }

    @Test
    public void testRetainAll() {
        CustomArrayList<String> list = generateList();
        assertEquals(16, list.size());

        list.retainAll(Arrays.asList("String5", "String10"));
        assertEquals(2, list.size());
        assertArrayEquals(new String[] {"String5", "String10"}, list.toArray());
    }

    @Test
    public void testClear() {
        CustomArrayList<String> list = generateList();
        assertEquals(16, list.size());

        list.add("Test");
        assertEquals(17, list.size());

        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    public void testGet() {
        CustomArrayList<String> list = generateList();
        assertEquals("String7", list.get(7));

        list.add(7,"Test");
        assertEquals("String7", list.get(8));
        assertEquals("Test", list.get(7));
    }

    @Test
    public void testSet() {
        CustomArrayList<String> list = generateList();
        assertEquals("String7", list.get(7));

        list.set(5, "Test");
        assertEquals("Test", list.get(5));
    }

    @Test
    public void testIndexOf() {
        CustomArrayList<String> list = generateList();
        assertEquals(7, list.indexOf("String7"));

        list.remove(7);
        assertEquals(-1, list.indexOf("String7"));

        list.add(5, null);
        assertEquals(5, list.indexOf(null));
    }

    @Test
    public void testLastIndexOf() {
        CustomArrayList<String> list = generateList();
        list.add("String5");
        list.add("String7");
        list.add("String5");
        list.add("String8");

        assertEquals(20, list.size());
        assertEquals(17, list.lastIndexOf("String7"));
        assertEquals(18, list.lastIndexOf("String5"));
        assertEquals(19, list.lastIndexOf("String8"));
    }

    @Test
    public void testListIterator() {
        CustomArrayList<String> list = generateList();
        ListIterator<String> iterator = list.listIterator();
        List<String> result = new ArrayList<>();
        while (iterator.hasNext()) {
            String value = iterator.next();

            if (value.equals("String2")) {
                iterator.set("Test");
            }

            if (value.equals("String5")) {
                iterator.remove();
            }

            if (value.equals("String13")) {
                iterator.add("Iterator");
            }

            result.add(value);
        }

        assertArrayEquals(DEFAULT_ARRAY, result.toArray());
        assertEquals(16, list.size());
        assertEquals(16, iterator.nextIndex());
        assertTrue(list.contains("Test"));
        assertTrue(list.contains("Iterator"));
        assertTrue(list.contains("String13"));
        assertFalse(list.contains("String2"));
        assertFalse(list.contains("String5"));

        list = generateList();
        ListIterator<String> reversedIterator = list.listIterator(list.size());
        List<String> reversedResult = new ArrayList<>();
        while (reversedIterator.hasPrevious()) {
            reversedResult.add(reversedIterator.previous());
        }

        Object[] array = Arrays.copyOf(DEFAULT_ARRAY, DEFAULT_ARRAY.length);
        Collections.reverse(Arrays.asList(array));
        assertEquals(16, reversedResult.size());
        assertEquals(-1, reversedIterator.previousIndex());
        assertArrayEquals(array, reversedResult.toArray());
    }

    @Test
    public void subList() {
        CustomArrayList<String> list = generateList();
        List<String> sublist = list.subList(5, 10);
        assertArrayEquals(new String[]{"String5", "String6", "String7", "String8", "String9"}, sublist.toArray());
    }

    private CustomArrayList<String> generateList() {
        CustomArrayList<String> list = new CustomArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            list.add("String" + i);
        }

        return list;
    }

    private CustomArrayList<String> generateEmptyList() {
        return new CustomArrayList<>();
    }

    private void assertValues(String[] array, List<String> result) {
        for (int i = 0; i < array.length; i++) {
            assertEquals(array[i], result.get(i));
        }
    }
}
