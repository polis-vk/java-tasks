package ru.mail.polis.homework.collections.structure;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CustomArrayListTest {

    private static final String[] DEFAULT_STRING_ARRAY = new String[]{"String0", "String1", "String2", "String3", "String4",
            "String5", "String6", "String7", "String8", "String9", "String10", "String11", "String12", "String13",
            "String14", "String15"};
    private static final int[] DEFAULT_ARRAY = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

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
        assertValues(DEFAULT_STRING_ARRAY, result);
    }

    @Test
    public void testToArray() {
        CustomArrayList<String> list = generateList();
        Object[] array = list.toArray();
        assertArrayEquals(array, DEFAULT_STRING_ARRAY);
    }

    @Test
    public void testToArrayGenerics() {
        CustomArrayList<String> list = generateList();
        String[] array = list.toArray(new String[16]);
        assertArrayEquals(array, DEFAULT_STRING_ARRAY);
    }

    @Test
    public void testAdd() {
        CustomArrayList<String> list = generateEmptyList();
        assertEquals(0, list.size());

        list.add("Test");
        assertEquals(1, list.size());
        assertEquals("Test", list.get(0));

        list.add("List");
        assertEquals(2, list.size());
        assertArrayEquals(new String[]{"Test", "List"}, list.toArray());

        list.add(1, "Add");
        assertEquals(3, list.size());
        assertArrayEquals(new String[]{"Test", "Add", "List"}, list.toArray());
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
        assertTrue(list.containsAll(Arrays.asList(DEFAULT_STRING_ARRAY)));
        assertFalse(list.containsAll(Arrays.asList("Test", "Contains")));
    }

    @Test
    public void testAddAll() {
        CustomArrayList<String> list = generateEmptyList();
        list.addAll(Arrays.asList("Test", "AddAll"));
        assertArrayEquals(new String[]{"Test", "AddAll"}, list.toArray());

        list.addAll(1, Arrays.asList("Array", "List"));
        assertArrayEquals(new String[]{"Test", "Array", "List", "AddAll"}, list.toArray());
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
        assertArrayEquals(new String[]{"String5", "String10"}, list.toArray());
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

        list.add(7, "Test");
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

        assertArrayEquals(DEFAULT_STRING_ARRAY, result.toArray());
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

        Object[] array = Arrays.copyOf(DEFAULT_STRING_ARRAY, DEFAULT_STRING_ARRAY.length);
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