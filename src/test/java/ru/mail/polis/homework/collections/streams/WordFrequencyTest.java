package ru.mail.polis.homework.collections.streams;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class WordFrequencyTest {

    @Test
    public void simple() {
        Stream<String> innerStream = Arrays.stream(new String[]{"Mother wash wash wash window!"});
        List<String> expected = Arrays.asList("wash", "mother", "window");
        List<String> actual = WordFrequency.wordFrequency(innerStream);
        assertEquals(expected, actual);
    }

    @Test
    public void simpleTwo(){
        Stream<String> innerStream = Arrays.stream(new String[]{"Mother wash wash wash window!",
                "mother! mother! mother!",
                "i can see window"});

        List<String> obtained = WordFrequency.wordFrequency(innerStream);

        List<String> expected = Arrays.asList("mother", "wash", "window", "can", "i", "see");
        Assert.assertEquals(expected, obtained);
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
                "Go i go,but then still go,and finally,reached"});
        List<String> expected = Arrays.asList("go", "and", "but", "finally", "i", "reached", "still", "then");
        List<String> actual = WordFrequency.wordFrequency(innerStream);
        assertEquals(expected, actual);
    }
}