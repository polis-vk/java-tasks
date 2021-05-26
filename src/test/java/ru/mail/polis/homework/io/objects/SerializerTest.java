package ru.mail.polis.homework.io.objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SerializerTest {

    private static int ANIMALS_SIZE = 100;

    private List<Animal> testAnimals;

    private Serializer serializer = new Serializer();

    private final Path directory = Paths.get("src", "test", "resources", "serializer");
    private Path file;

    @Before
    public void setUP() throws IOException {
        Files.createDirectories(directory);
        testAnimals = Animal.getRandomAnimals(ANIMALS_SIZE);
        file = Paths.get(directory.toString(), "default");
        if (Files.exists(file)) {
            Files.delete(file);
        }
    }

    @Test
    public void defaultSerializerTest() throws IOException {
        long startWriteTime = System.currentTimeMillis();
        serializer.defaultSerialize(testAnimals, file.toString());
        long endWriteTime = System.currentTimeMillis();

        List<Animal> deserialized;
        long startReadTime = System.currentTimeMillis();
        deserialized = serializer.defaultDeserialize(file.toString());
        long endReadTime = System.currentTimeMillis();

        System.out.println("Serialize time: " + (endWriteTime - startWriteTime));
        System.out.println("Deserialize time: " + (endReadTime - startReadTime));
        System.out.println("File size: " + Files.size(file));

        Assert.assertArrayEquals("Not equal after deserialize", testAnimals.toArray(), deserialized.toArray());
    }

    @After
    public void tearDown() throws IOException {
        Files.delete(file);
    }
}
