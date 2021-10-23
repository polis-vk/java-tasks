package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomDictionaryTest {

    @Test
    public void defaultTest() {
        List<String> data = Arrays.asList("aaa", "aBa", "baa", "aaB");
        CustomDictionary dictionary = new CustomDictionary();
        for (String e : data) {
            dictionary.add(e);
        }
        assertEquals(4, dictionary.size());
        assertEquals(Arrays.asList("aBa", "baa", "aaB"), dictionary.getSimilarWords("AAb"));
        assertEquals(Arrays.asList("aBa", "baa", "aaB"), dictionary.getSimilarWords("BAabbaABABABAbaba"));
        assertEquals(Collections.singletonList("aaa"), dictionary.getSimilarWords("AAAAAAAAAAAAAAAAAAAAAAAaAAAAA"));
        assertEquals(Collections.emptyList(), dictionary.getSimilarWords("b"));
    }

    @Test
    public void containsTest() {
        List<String> data = Arrays.asList("aaa", "aBa", "baa", "aaB");
        CustomDictionary dictionary = new CustomDictionary();
        for (String e : data) {
            dictionary.add(e);
        }
        assertTrue(dictionary.contains("aaa"));
        assertFalse(dictionary.contains("Aaa"));
        assertFalse(dictionary.contains("AAA"));
        assertFalse(dictionary.contains("aaaa"));
        assertFalse(dictionary.remove("Aba"));
        assertFalse(dictionary.remove("VALERA"));
    }


    @Test
    public void removeTest() {
        List<String> data = Arrays.asList("aaa", "aBa", "baa", "aaB");
        CustomDictionary dictionary = new CustomDictionary();
        for (String e : data) {
            dictionary.add(e);
        }
        assertTrue(dictionary.remove("aaa"));
        assertFalse(dictionary.remove("aaa"));
        assertFalse(dictionary.remove("Aba"));
    }

    @Test(expected = NullPointerException.class)
    public void addNull() {
        List<String> data = Arrays.asList("aaa", "aBa", "baa", "aaB");
        CustomDictionary dictionary = new CustomDictionary();
        for (String e : data) {
            dictionary.add(e);
        }
        dictionary.add(null);
    }

    @Test
    public void findNull() {
        List<String> data = Arrays.asList("aaa", "aBa", "baa", "aaB");
        CustomDictionary dictionary = new CustomDictionary();
        for (String e : data) {
            dictionary.add(e);
        }
        assertFalse(dictionary.remove(null));
        assertFalse(dictionary.contains(null));
    }

    @Test
    public void emptyStringTest() {
        List<String> data = Arrays.asList("aaa", "aBa", "baa", "aaB", "");
        CustomDictionary dictionary = new CustomDictionary();
        for (String e : data) {
            dictionary.add(e);
        }
        assertTrue(dictionary.contains(""));
        assertEquals(Collections.singletonList(""), dictionary.getSimilarWords(""));
        assertTrue(dictionary.remove(""));
    }

    @Test
    public void sizeTest() {
        List<String> data = Arrays.asList("aaa", "aBa", "baa", "aaB", "");
        CustomDictionary dictionary = new CustomDictionary();
        int size = 0;
        for (String e : data) {
            assertEquals(dictionary.size(), size);
            dictionary.add(e);
            size++;
        }
        dictionary.remove("");
        assertEquals(dictionary.size(), --size);
        dictionary.remove("");
        assertEquals(dictionary.size(), size);
    }
}