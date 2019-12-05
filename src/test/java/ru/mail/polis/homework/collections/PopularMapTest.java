package ru.mail.polis.homework.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PopularMapTest {

    private PopularMap<TestObject, TestObject> popularMap1;
    private PopularMap<TestObject, TestObject> popularMap2;

    @Mock
    private Map<TestObject, TestObject> map;

    @Before
    public void setUp() {
        popularMap1 = new PopularMap<>();
        popularMap2 = spy(new PopularMap<>(map));
    }


    @Test
    public void popularKey_OnlyPutOne() {

        for (int i = 0; i < 10; i++) {
            popularMap1.put(new TestObject("key" + i), new TestObject("value" + i));
        }
        TestObject popular = popularMap1.getPopularKey();
        assertTrue(popular.getStr().startsWith("key"));
        assertEquals(1, popularMap1.getKeyPopularity(new TestObject("key2")));
    }

    @Test
    public void popularKey_operationCount() {
        TestObject key = new TestObject("key");
        TestObject value = new TestObject("value");
        popularMap1.get(key);
        popularMap1.remove(key);
        popularMap1.put(key, value);
        popularMap1.put(key, value);
        popularMap1.get(key);
        popularMap1.remove(key);
        popularMap1.remove(key);
        popularMap1.put(key, value);
        popularMap1.remove(key);

        assertEquals(key, popularMap1.getPopularKey());
        assertEquals(9, popularMap1.getKeyPopularity(key));
    }

    @Test
    public void popularKey_manyKeys() {
        TestObject key1 = new TestObject("key1");
        TestObject key2 = new TestObject("key2");
        TestObject key3 = new TestObject("key3");
        TestObject key4 = new TestObject("key4");
        TestObject key5 = new TestObject("key5");
        TestObject value = new TestObject("value");
        popularMap1.put(key1, value);
        popularMap1.remove(key1);
        popularMap1.put(key2, value);
        popularMap1.put(key3, value);
        popularMap1.get(key2);
        popularMap1.remove(key4);
        popularMap1.remove(key3);
        popularMap1.put(key4, value);
        popularMap1.remove(key3);

        assertEquals(key3, popularMap1.getPopularKey());
        assertEquals(3, popularMap1.getKeyPopularity(key3));
        assertEquals(0, popularMap1.getKeyPopularity(key5));
    }



    @Test
    public void popularValue_OnlyPutOne() {

        for (int i = 0; i < 10; i++) {
            popularMap1.put(new TestObject("key" + i), new TestObject("value" + i));
        }
        TestObject popular = popularMap1.getPopularValue();
        assertTrue(popular.getStr().startsWith("value"));
        assertEquals(1, popularMap1.getValuePopularity(new TestObject("value2")));
    }


    @Test
    public void popularValue_operationCount() {
        TestObject key = new TestObject("key");
        TestObject value = new TestObject("value");
        popularMap1.get(key);
        popularMap1.remove(key);
        popularMap1.put(key, value);
        popularMap1.put(key, value);
        popularMap1.get(key);
        popularMap1.remove(key);
        popularMap1.remove(key);
        popularMap1.put(key, value);
        popularMap1.remove(key);

        assertEquals(value, popularMap1.getPopularValue());
        assertEquals(7, popularMap1.getValuePopularity(value));
    }


    @Test
    public void popularKey_manyValues() {
        TestObject key1 = new TestObject("key1");
        TestObject key2 = new TestObject("key2");
        TestObject value1 = new TestObject("value1");
        TestObject value2 = new TestObject("value2");
        TestObject value3 = new TestObject("value3");
        TestObject value4 = new TestObject("value4");
        popularMap1.put(key1, value1);
        popularMap1.get(key1);
        popularMap1.remove(key1);
        popularMap1.put(key2, value1);
        popularMap1.put(key2, value2);
        popularMap1.get(key2);
        popularMap1.remove(key2);
        popularMap1.remove(key1);
        popularMap1.put(key1, value3);
        popularMap1.put(key2, value3);
        popularMap1.get(key2);
        popularMap1.remove(key1);

        assertEquals(value1, popularMap1.getPopularValue());
        assertEquals(5, popularMap1.getValuePopularity(value1));
        assertEquals(3, popularMap1.getValuePopularity(value2));
        assertEquals(4, popularMap1.getValuePopularity(value3));
        assertEquals(0, popularMap1.getValuePopularity(value4));
    }

    @Test
    public void popularIterator() {
        TestObject key1 = new TestObject("key1");
        TestObject key2 = new TestObject("key2");
        TestObject value1 = new TestObject("value1");
        TestObject value2 = new TestObject("value2");
        TestObject value3 = new TestObject("value3");
        TestObject value4 = new TestObject("value4");
        popularMap1.put(key1, value1);
        popularMap1.remove(key1);
        popularMap1.put(key2, value1);
        popularMap1.put(key2, value2);
        popularMap1.get(key2);
        popularMap1.remove(key2);
        popularMap1.remove(key1);
        popularMap1.put(key1, value3);
        popularMap1.put(key2, value3);

        List<TestObject> expected = Arrays.asList(value3);
        int i = 0;
        for (Iterator<TestObject> it = popularMap1.popularIterator(); it.hasNext(); ) {
            assertEquals(expected.get(i++), it.next());
        }
        assertEquals(1, i);
    }

    @Test
    public void isEmpty() {
        popularMap2.isEmpty();
        verify(map, times(1)).isEmpty();

    }

    @Test
    public void containsKey() {

        popularMap2.containsKey(new TestObject("key"));
        verify(map, times(1)).containsKey(new TestObject("key"));
    }

    @Test
    public void containsValue() {

        popularMap2.containsValue(new TestObject("value"));
        verify(map, times(1)).containsValue(new TestObject("value"));
    }

    @Test
    public void get() {
        popularMap2.get(new TestObject("key"));
        verify(map, times(1)).get(new TestObject("key"));
    }

    @Test
    public void put() {

        popularMap2.put(new TestObject("key"), new TestObject("value"));
        verify(map, times(1)).put(new TestObject("key"), new TestObject("value"));
    }

    @Test
    public void remove() {
        popularMap2.remove(new TestObject("key"));
        verify(map, times(1)).remove(new TestObject("key"));
    }

    @Mock
    Map<TestObject, TestObject> putAllMap;

    @Test
    public void putAll() {
        popularMap2.putAll(putAllMap);
        verify(map, times(1)).putAll(putAllMap);
    }

    @Test
    public void clear() {
        popularMap2.clear();
        verify(map, times(1)).clear();
    }

    @Test
    public void keySet() {
        popularMap2.keySet();
        verify(map, times(1)).keySet();
    }

    @Test
    public void values() {
        popularMap2.values();
        verify(map, times(1)).values();
    }

    @Test
    public void entrySet() {
        popularMap2.entrySet();
        verify(map, times(1)).entrySet();
    }
}
