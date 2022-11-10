package ru.mail.polis.homework.io;

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
    private static final int CAPACITY = 100000;
    private static final int MIN_RANDOM = 2;
    private static final int MAX_RANDOM = 10;
    private static final int MAX_NAME_LENGTH = 100;
    private static final char START_NAME_LETTER = 'a';
    private static final char END_NAME_LETTER = 'z';
    private static final List<Animal> animals = getRandomAnimalList(SerializerTest::generateAnimal);
    private static final List<AnimalExternalizable> animalsExternalizable = getRandomAnimalList(SerializerTest::generateAnimalExternalizable);
    private static final List<AnimalWithMethods> animalsWithMethods = getRandomAnimalList(SerializerTest::generateAnimalWithMethods);
    private static final Path DIR_PATH = Paths.get(
        "src", "test", "resources", "serializer");
    private static final Path FILE_PATH = Paths.get(
        "src", "test", "resources", "serializer", "animals.bin");
    private final Serializer serializer = new Serializer();

    @Before
    public void setUp() throws IOException {
        Files.createDirectories(DIR_PATH);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    public void defaultSerializeAndDeserializeTest() throws IOException {
        Path file = createFile();
        System.out.println("Default serialization and deserialization ");

        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, file.toString());
        long serializeTime = System.currentTimeMillis() - start;
        long fileSize = Files.size(FILE_PATH);

        start = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(file.toString());
        long deserializeTime = System.currentTimeMillis() - start;

        System.out.println("File size: " + fileSize);
        System.out.println("Serialize time: " + serializeTime + "ms");
        System.out.println("Deserialize time: " + deserializeTime + "ms");

        assertEquals(animals, deserializedAnimals);
    }

    @Test
    public void serializeAndDeserializeWithExternalizableTest() throws IOException {
        Path file = createFile();
        System.out.println("Serialization and deserialization with externalizable");

        long start = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, file.toString());
        long serializeTime = System.currentTimeMillis() - start;
        long fileSize = Files.size(FILE_PATH);

        start = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable(file.toString());
        long deserializeTime = System.currentTimeMillis() - start;

        System.out.println("File size: " + fileSize);
        System.out.println("Serialize time: " + serializeTime + "ms");
        System.out.println("Deserialize time: " + deserializeTime + "ms");

        assertEquals(animalsExternalizable, deserializedAnimals);
    }

    @Test
    public void serializeAndDeserializeWithMethodsTest() throws IOException {
        Path file = createFile();
        System.out.println("Serialization and deserialization with methods");

        long start = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, file.toString());
        long serializeTime = System.currentTimeMillis() - start;
        long fileSize = Files.size(FILE_PATH);

        start = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods(file.toString());
        long deserializeTime = System.currentTimeMillis() - start;

        System.out.println("File size: " + fileSize);
        System.out.println("Serialize time: " + serializeTime + "ms");
        System.out.println("Deserialize time: " + deserializeTime + "ms");

        assertEquals(animalsWithMethods, deserializedAnimals);
    }

    @Test
    public void customSerializeAndDeserializeTest() throws IOException {
        Path file = createFile();
        System.out.println("Custom serialization and deserialization ");

        long start = System.currentTimeMillis();
        serializer.customSerialize(animals, file.toString());
        long serializeTime = System.currentTimeMillis() - start;
        long fileSize = Files.size(FILE_PATH);

        start = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.customDeserialize(file.toString());
        long deserializeTime = System.currentTimeMillis() - start;

        System.out.println("File size: " + fileSize);
        System.out.println("Serialize time: " + serializeTime + "ms");
        System.out.println("Deserialize time: " + deserializeTime + "ms");

        assertEquals(animals, deserializedAnimals);
    }

    private static Path createFile() {
        try {
            return Files.createFile(FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> getRandomAnimalList(Supplier<T> generator) {
        List<T> animals = new ArrayList<>();
        for (int i = 0; i < CAPACITY; i++) {
            animals.add(generator.get());
        }
        return animals;
    }

    private static Animal generateAnimal() {
        return new Animal(
            generateString(),
            generateInt(),
            generateDouble(),
            generateBoolean(),
            generateBoolean(),
            generateAnimalType(),
            generateOrganization()
        );
    }

    private static AnimalWithMethods generateAnimalWithMethods() {
        return new AnimalWithMethods(
            generateString(),
            generateInt(),
            generateDouble(),
            generateBoolean(),
            generateBoolean(),
            generateAnimalType(),
            generateOrganization()
        );
    }

    private static AnimalExternalizable generateAnimalExternalizable() {
        return new AnimalExternalizable(
            generateString(),
            generateInt(),
            generateDouble(),
            generateBoolean(),
            generateBoolean(),
            generateAnimalType(),
            generateOrganization()
        );
    }

    private static String generateString() {
        if (random.nextBoolean()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < random.nextInt(MAX_NAME_LENGTH); i++) {
            stringBuilder.append((char) ThreadLocalRandom.current().nextInt(START_NAME_LETTER, END_NAME_LETTER));
        }
        return stringBuilder.toString();
    }

    private static int generateInt() {
        return ThreadLocalRandom.current().nextInt((MAX_RANDOM - MIN_RANDOM) + 1) + MIN_RANDOM;
    }

    private static double generateDouble() {
        return ThreadLocalRandom.current().nextDouble((MAX_RANDOM - MIN_RANDOM) + 1) + MIN_RANDOM;
    }

    private static boolean generateBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static AnimalType generateAnimalType() {
        if (random.nextBoolean()) {
            return null;
        }
        return AnimalType.values()[random.nextInt(AnimalType.values().length)];
    }

    private static Organization generateOrganization() {
        if (random.nextBoolean()) {
            return null;
        }
        return new Organization(generateString(), generateBoolean());
    }
}
