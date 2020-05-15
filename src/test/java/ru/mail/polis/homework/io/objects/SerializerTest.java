package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.junit.Assert.*;

public class SerializerTest {

    private Serializer serializer;

    @Before
    public void setUp() throws IOException {
        serializer = new Serializer();
        Path directory = Paths.get("src", "test", "resources", "serializer");
        Files.createDirectories(directory);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "serializer").toFile());
    }

    @Test
    public void emptyListTest() throws IOException {
        emptyList(serializer::defaultSerialize, serializer::defaultDeserialize);
        emptyList(serializer::customSerialize, serializer::customDeserialize);
    }

    @Test
    public void completedListTest() throws IOException {
        completedList(serializer::defaultSerialize, serializer::defaultDeserialize);
        completedList(serializer::customSerialize, serializer::customDeserialize);
    }

    private void emptyList(BiConsumer<List<Animal>, String> serialize, Function<String, List<Animal>> deserialize) throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal1.bin");
        List<Animal> animals1 = new ArrayList<>();
        serialize.accept(animals1, file.toString());
        List<Animal> animals2 = deserialize.apply(file.toString());
        assertTrue(animals2.isEmpty());
    }

    private void completedList(BiConsumer<List<Animal>, String> serialize, Function<String, List<Animal>> deserialize) throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal2.bin");
        List<Animal> animals1 = new ArrayList<>();
        List<Rewards> rewards = new ArrayList<>();
        rewards.add(new Rewards(01022001, 1, "CC"));
        rewards.add(new Rewards(02022001, 2, "DD"));
        rewards.add(new Rewards(02022001, 2, "BB"));
        rewards.add(new Rewards(02022001, 2, "FF"));

        Animal animal1 = new Animal(10, "animal1", 0202, AnimalTypes.FISH, rewards);
        Animal animal2 = new Animal(11, "animal2", 0202, AnimalTypes.INSECTS, rewards);
        Animal animal3 = new Animal(12, "animal3", 0202, AnimalTypes.MAMMALS, rewards);
        Animal animal4 = new Animal(13, "animal4", 0202, AnimalTypes.INSECTS, rewards);

        serialize.accept(animals1, file.toString());
        List<Animal> animals2 = deserialize.apply(file.toString());
        assertEquals(animals1, animals2);
    }
}