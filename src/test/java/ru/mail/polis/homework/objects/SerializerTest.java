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
    private final List<Animal> animals = Animal.randomListOfAnimals(10);
    private final static String TEST_FILE_NAME = "testSerializer.txt";
    private final Serializer serializer = new Serializer();

    @Before
    public void setUp() throws IOException {
        if (Files.notExists(Paths.get(TEST_FILE_NAME))) {
            Files.createFile(Paths.get(TEST_FILE_NAME));
        }
    }

    @Test
    public void testDefaultSerializer() throws IOException {
        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, TEST_FILE_NAME);
        List<Animal> deserialized = serializer.defaultDeserialize(TEST_FILE_NAME);
        assertEquals(animals, deserialized);
        long size = Files.size(Paths.get(TEST_FILE_NAME));
        long time = System.currentTimeMillis() - start;
        System.out.printf("Default: Size: %d bytes, Execution time: %d ms%n", size, time);
    }

    @Test
    public void testCustomSerializer() throws IOException {
        long start = System.currentTimeMillis();
        serializer.customSerialize(animals, TEST_FILE_NAME);
        List<Animal> deserialized = serializer.customDeserialize(TEST_FILE_NAME);
        assertEquals(animals, deserialized);
        long size = Files.size(Paths.get(TEST_FILE_NAME));
        long time = System.currentTimeMillis() - start;
        System.out.printf("Custom: Size: %d bytes, Execution time: %d ms%n", size, time);
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE_NAME));
    }
}