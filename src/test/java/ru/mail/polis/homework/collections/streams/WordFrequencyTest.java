package ru.mail.polis.homework.collections.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class WordFrequencyTest {

    @Test
    public void simple() {
        Stream<String> innerStream = Arrays.stream(new String[]{"Мама мыла мыла мыла раму!"});
        List<String> expected = Arrays.asList("мыла", "мама", "раму");
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
                "Шел я шел,а потом еще шел,и наконец,дошел"});
        List<String> expected = Arrays.asList("шел", "а", "дошел", "еще", "и", "наконец", "потом", "я");
        List<String> actual = WordFrequency.wordFrequency(innerStream);
        assertEquals(expected, actual);
    }
}