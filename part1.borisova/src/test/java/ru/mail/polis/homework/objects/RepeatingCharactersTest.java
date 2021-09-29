package ru.mail.polis.homework.objects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RepeatingCharactersTest {

    @Test
    public void getMaxRepeatingCharacters_stringIsNull() {
        assertNull(RepeatingCharacters.getMaxRepeatingCharacters(null));
        assertNull(RepeatingCharacters.getMaxRepeatingCharacters(""));
    }

    @Test
    public void getMaxRepeatingCharacters_oneMax() {
        assertEquals(new RepeatingCharacters.Pair<>('d', 4), RepeatingCharacters.getMaxRepeatingCharacters("aaaddddggeeereee"));
        assertEquals(new RepeatingCharacters.Pair<>('g', 3), RepeatingCharacters.getMaxRepeatingCharacters("ggg"));
        assertEquals(new RepeatingCharacters.Pair<>('c', 4), RepeatingCharacters.getMaxRepeatingCharacters("bbbaabbbccccaa"));
        assertEquals(new RepeatingCharacters.Pair<>('f', 6), RepeatingCharacters.getMaxRepeatingCharacters("abbcccddddeeeeeffffff"));
    }

    @Test
    public void getMaxRepeatingCharacters_manyMax() {
        assertEquals(new RepeatingCharacters.Pair<>('a', 1), RepeatingCharacters.getMaxRepeatingCharacters("a"));
        assertEquals(new RepeatingCharacters.Pair<>('a', 1), RepeatingCharacters.getMaxRepeatingCharacters("abcd"));
        assertEquals(new RepeatingCharacters.Pair<>('g', 2), RepeatingCharacters.getMaxRepeatingCharacters("gagaggaa"));
        assertEquals(new RepeatingCharacters.Pair<>('a', 3), RepeatingCharacters.getMaxRepeatingCharacters("aaabbbccc"));
    }
}