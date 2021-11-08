package ru.mail.polis.homework.collections.structure;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomDictionaryTest {

    private static String TEST_STRING = "Test";
    private static String REVERT_TEST_STRING = "tseT";
    private static String UPPER_TEST_STRING = TEST_STRING.toUpperCase();

    @Test
    public void testSuccessAdd() {
        CustomDictionary dictionary = new CustomDictionary();
        boolean result = dictionary.add(TEST_STRING);
        assertTrue(result);
    }

    @Test
    public void testCloneWordAdd() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);
        boolean result = dictionary.add(TEST_STRING);
        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyStringAdd() {
        CustomDictionary dictionary = new CustomDictionary();
        boolean result = dictionary.add("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAdd() {
        CustomDictionary dictionary = new CustomDictionary();
        boolean result = dictionary.add(null);
    }

    @Test
    public void testRevertWordAdd() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);

        boolean result = dictionary.add(REVERT_TEST_STRING);
        assertTrue(result);
    }

    @Test
    public void testSimilarUpperCaseWordAdd() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);

        boolean result = dictionary.add(UPPER_TEST_STRING);
        assertTrue(result);
    }

    @Test
    public void testOneWordContains() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);

        assertTrue(dictionary.contains(TEST_STRING));
    }

    @Test
    public void testManyWordsContains() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);
        dictionary.add(REVERT_TEST_STRING);
        dictionary.add(UPPER_TEST_STRING);

        assertTrue(dictionary.contains(TEST_STRING));
        assertTrue(dictionary.contains(REVERT_TEST_STRING));
        assertTrue(dictionary.contains(UPPER_TEST_STRING));
    }

    @Test
    public void testNonExistentContains() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add("Any String");
        boolean result = dictionary.contains(TEST_STRING);

        assertFalse(result);
    }

    @Test
    public void testIsEmptyContains() {
        CustomDictionary dictionary = new CustomDictionary();
        boolean result = dictionary.contains(TEST_STRING);

        assertFalse(result);
    }

    @Test
    public void testSimilarContains() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);
        boolean result = dictionary.contains(REVERT_TEST_STRING);

        assertFalse(result);
    }

    @Test
    public void testExistingWordRemove() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);

        boolean result = dictionary.remove(TEST_STRING);
        assertTrue(result);
    }

    @Test
    public void testNotExistingWordRemove() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);

        boolean result = dictionary.remove(UPPER_TEST_STRING);
        assertFalse(result);
    }

    @Test
    public void testRemoveFromEmptyDictionary() {
        CustomDictionary dictionary = new CustomDictionary();

        boolean result = dictionary.remove(TEST_STRING);
        assertFalse(result);
    }

    @Test
    public void getSimilarWordsForOneWord() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);

        List<String> resultList = dictionary.getSimilarWords(TEST_STRING);
        assertEquals(1, resultList.size());
        assertTrue(resultList.contains(TEST_STRING));
    }

    @Test
    public void getSimilarWordsForRevertsWords() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);
        dictionary.add(REVERT_TEST_STRING);

        List<String> resultList = dictionary.getSimilarWords(TEST_STRING);
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(TEST_STRING));
        assertTrue(resultList.contains(REVERT_TEST_STRING));
    }

    @Test
    public void getSimilarWordsForDifferentCaseWords() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);
        dictionary.add(UPPER_TEST_STRING);

        List<String> resultList = dictionary.getSimilarWords(TEST_STRING);
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(TEST_STRING));
        assertTrue(resultList.contains(UPPER_TEST_STRING));
    }

    @Test
    public void getSimilarWordsBySimilarWord() {
        CustomDictionary dictionary = new CustomDictionary();
        dictionary.add(TEST_STRING);
        dictionary.add(UPPER_TEST_STRING);

        List<String> resultList = dictionary.getSimilarWords(REVERT_TEST_STRING);
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(TEST_STRING));
        assertTrue(resultList.contains(UPPER_TEST_STRING));
    }

    @Test
    public void getSimilarWordsForEmptyDictionary() {
        CustomDictionary dictionary = new CustomDictionary();

        List<String> resultList = dictionary.getSimilarWords(TEST_STRING);
        assertEquals(0, resultList.size());
        assertEquals(Collections.emptyList(), resultList);
    }

    @Test
    public void size() {
        CustomDictionary dictionary = new CustomDictionary();
        assertEquals(0, dictionary.size());

        dictionary.add(TEST_STRING);
        assertEquals(1, dictionary.size());

        dictionary.add(REVERT_TEST_STRING);
        dictionary.add(UPPER_TEST_STRING);
        assertEquals(3, dictionary.size());

        dictionary.remove(TEST_STRING);
        assertEquals(2, dictionary.size());
    }

    @Test
    public void testRemoveNonExistentSize() {
        CustomDictionary dictionary = new CustomDictionary();
        assertEquals(0, dictionary.size());

        dictionary.add(TEST_STRING);
        assertEquals(1, dictionary.size());

        dictionary.remove(REVERT_TEST_STRING);

        assertEquals(1, dictionary.size());
    }

    @Test
    public void testSimilarWordsForDifferentLengthWords() {
        CustomDictionary dictionary = new CustomDictionary();

        dictionary.add("bbb");
        dictionary.add("bbB");
        dictionary.add("bBb");
        dictionary.add("Bbb");

        List<String> result = dictionary.getSimilarWords("bbbb");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testStrangeString() {
        CustomDictionary dictionary = new CustomDictionary();
        char[] arrayChar1 = new char[3];
        arrayChar1[0] = 0;
        arrayChar1[1] = 100;
        arrayChar1[2] = 0;

        String str1 = new String(arrayChar1);
        dictionary.add(str1);

        char[] arrayChar2 = new char[3];
        arrayChar2[0] = 100;
        arrayChar2[1] = 0;
        arrayChar2[2] = 0;

        String str2 = new String(arrayChar2);
        dictionary.add(str2);

        char[] arrayChar3 = new char[3];
        arrayChar3[0] = 0;
        arrayChar3[1] = 0;
        arrayChar3[2] = 100;

        String str3 = new String(arrayChar3);

        List<String> result = dictionary.getSimilarWords(str3);
        assertEquals(2, result.size());
        assertTrue(result.contains(str1));
        assertTrue(result.contains(str2));
    }



}