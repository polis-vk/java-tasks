package ru.mail.polis.homework.collections.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class WordFrequencyTest {

    @Test
    public void simple() {
        Stream<String> innerStream = Arrays.stream(new String[]{"Mama mila mila mila ramu"});
        List<String> expected = Arrays.asList("mila", "mama", "ramu");
        List<String> actual = WordFrequency.wordFrequency(innerStream);
        assertEquals(expected, actual);
    }

    @Test
    public void standard() {
        Stream<String> innerStream = Arrays.stream(new String[]{
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sodales consectetur purus at faucibus.",
                "Donec mi quam, tempor vel ipsum non, faucibus suscipit massa. Morbi lacinia velit blandit tincidunt",
                "efficitur. Vestibulum eget metus imperdiet sapien laoreet faucibus. Nunc eget vehicula mauris, ac",
                "auctor lorem. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer vel odio nec mi",
                "tempor dignissim"});
        List<String> expected = Arrays.asList("consectetur", "faucibus", "ipsum", "lorem", "adipiscing", "amet",
                "dolor", "eget", "elit", "mi");
        List<String> actual = WordFrequency.wordFrequency(innerStream);
        assertEquals(expected, actual);
    }

    @Test
    public void Oops() {
        Stream<String> innerStream = Arrays.stream(new String[]{
                "Shel ya shel, a potom esche shel, i nakonets,doshel"});
        List<String> expected = Arrays.asList("shel", "a", "doshel", "esche", "i", "nakonets", "potom", "ya");
        List<String> actual = WordFrequency.wordFrequency(innerStream);
        assertEquals(expected, actual);
    }
}