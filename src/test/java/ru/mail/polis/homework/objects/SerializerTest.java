package ru.mail.polis.homework.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SerializerTest {
    List<Animal> animals = Animal.randomListOfAnimals(10);
    final String testFileName = "testSerializer.txt";
    Serializer serializer = new Serializer();

    @Before
    public void setUp() throws IOException {
        if (Files.notExists(Paths.get(testFileName))) {
            Files.createFile(Paths.get(testFileName));
        }
    }

    @Test
    public void testDefaultSerializer() throws IOException {
        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, testFileName);
        List<Animal> deserialized = serializer.defaultDeserialize(testFileName);
        assertEquals(animals, deserialized);
        long size = Files.size(Paths.get(testFileName));
        long time = System.currentTimeMillis() - start;
        System.out.printf("Default: Size: %d bytes, Execution time: %d ms%n", size, time);
    }

    @Test
    public void testCustomSerializer() throws IOException {
        long start = System.currentTimeMillis();
        serializer.customSerialize(animals, testFileName);
        List<Animal> deserialized = serializer.customDeserialize(testFileName);
        assertEquals(animals, deserialized);
        long size = Files.size(Paths.get(testFileName));
        long time = System.currentTimeMillis() - start;
        System.out.printf("Custom: Size: %d bytes, Execution time: %d ms%n", size, time);
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testFileName));
    }
}