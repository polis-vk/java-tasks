package ru.mail.polis.homework.io;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class SerializerTest {

    private static final Random random = new Random();
    private static final int CAPACITY = 300000;
    private static final int COUNT_OF_LEGS = 4;
    private final Serializer serializer = new Serializer();
    private static final List<Animal> animals = getRandomAnimalList(SerializerTest::generateAnimal);
    private static final List<AnimalWithMethods> animalsWithMethods = getRandomAnimalList(SerializerTest::generateAnimalWithMethods);
    private static final List<AnimalExternalizable> animalsExternalizable = getRandomAnimalList(SerializerTest::generateAnimalExternalizable);


    @Before
    public void setUp() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "serializer");
        Files.createDirectories(dir);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "serializer", "animals.bin").toFile());
    }

    @Test
    public void testDefaultSerializeAndDeserialize() {
        Path resultFile = createResultFile();
        System.out.println("---------------Default serialization---------------");
        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, resultFile.toString());
        long end = System.currentTimeMillis();
        System.out.println("Time Serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(resultFile.toString());
        end = System.currentTimeMillis();
        System.out.println("Time Deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);
    }

    @Test
    public void testSerializeAndDeserializeWithMethods() {
        Path resultFile = createResultFile();
        System.out.println("---------------Serialization with methods---------------");
        long start = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, resultFile.toString());
        long end = System.currentTimeMillis();
        System.out.println("Time Serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods(resultFile.toString());
        end = System.currentTimeMillis();
        System.out.println("Time Deserializing: " + (end - start) + "ms");

        assertEquals(animalsWithMethods, deserializedAnimals);
    }

    @Test
    public void testSerializeAndDeserializeExternalizable() {
        Path resultFile = createResultFile();
        System.out.println("---------------Serialization with Externalizable---------------");
        long start = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, resultFile.toString());
        long end = System.currentTimeMillis();
        System.out.println("Time Serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable(resultFile.toString());
        end = System.currentTimeMillis();
        System.out.println("Time Deserializing: " + (end - start) + "ms");

        assertEquals(animalsExternalizable, deserializedAnimals);
    }

    @Test
    public void testCustomSerializeAndDeserialize() {
        Path resultFile = createResultFile();
        System.out.println("---------------Custom serialization---------------");
        long start = System.currentTimeMillis();
        serializer.customSerialize(animals, resultFile.toString());
        long end = System.currentTimeMillis();
        System.out.println("Time Serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.customDeserialize(resultFile.toString());
        end = System.currentTimeMillis();
        System.out.println("Time Deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);
    }

    private static Animal generateAnimal() {
        return new Animal(
                generateString(),
                generateInt(),
                generateAnimalMoveType(),
                generateLocation(),
                generateBoolean(),
                generateBoolean()
        );
    }

    private static AnimalWithMethods generateAnimalWithMethods() {
        return new AnimalWithMethods(
                generateString(),
                generateInt(),
                generateAnimalMoveType(),
                generateLocation(),
                generateBoolean(),
                generateBoolean()
        );
    }

    private static AnimalExternalizable generateAnimalExternalizable() {
        return new AnimalExternalizable(
                generateString(),
                generateInt(),
                generateAnimalMoveType(),
                generateLocation(),
                generateBoolean(),
                generateBoolean()
        );
    }

    private static String generateString() {
        if (random.nextBoolean()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < random.nextInt(100); i++) {
            stringBuilder.append((char) ThreadLocalRandom.current().nextInt('a', 'z'));
        }
        return stringBuilder.toString();
    }

    private static int generateInt() {
        return COUNT_OF_LEGS;
    }

    private static boolean generateBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static AnimalHabitat generateLocation() {
        if (random.nextBoolean()) {
            return null;
        }
        return new AnimalHabitat(generateString());
    }

    private static AnimalMoveType generateAnimalMoveType() {
        if (random.nextBoolean()) {
            return null;
        }
        return AnimalMoveType.values()[random.nextInt(AnimalMoveType.values().length)];
    }

    private static <T> List<T> getRandomAnimalList(Supplier<T> generator) {
        List<T> animals = new ArrayList<>();
        for (int i = 0; i < CAPACITY; i++) {
            animals.add(generator.get());
        }
        return animals;
    }

    private static Path createResultFile() {
        Path resultFile = Paths.get("src", "test", "resources", "serializer", "animals.bin");
        try {
            return Files.createFile(resultFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
