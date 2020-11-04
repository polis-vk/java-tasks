package ru.mail.polis.homework.collections.streams;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import static ru.mail.polis.homework.collections.streams.SimpleStreams.createBadWordsDetectingStream;
import static ru.mail.polis.homework.collections.streams.SimpleStreams.isPrime;

public class SimpleStreamsTest extends TestCase {

    public void testIsPrime() {
        assertFalse(isPrime(4));
        assertTrue(isPrime(2));
        assertTrue(isPrime(305175781));
        assertFalse(isPrime(305175782));
    }

    public void testCreateBadWordsDetectingStream() {
        String text = "I:love?red!color.  !He; loves red , color. She .love red kit. He !dislikes! blue. ";
        List<String> badWords = new ArrayList<String>(){
            {
                add("red");
                add("He");
                add("blue");
                add("color");
                add("love");
                add("hello");
            }};

        Map<String, Integer> filtered = createBadWordsDetectingStream(text, badWords);
        assertEquals(3, filtered.getOrDefault("red", 0).intValue());
        assertEquals(2, filtered.getOrDefault("He", 0).intValue());
        assertEquals(1, filtered.getOrDefault("blue", 0).intValue());
        assertEquals(2, filtered.getOrDefault("color", 0).intValue());
        assertEquals(2, filtered.getOrDefault("love", 0).intValue());
        assertEquals(0, filtered.getOrDefault("hello", 0).intValue());
    }
}