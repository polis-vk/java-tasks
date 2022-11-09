package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializerTest {

    private static final int MAX_STRING_SIZE = 100;
    private static final int ANIMALS_SIZE = 1000000;
    private static final Path DIR_PATH = Paths.get("src", "test", "resources", "objects");
    private static final Path DEFAULT_FILE_PATH = Paths.get("src", "test", "resources", "objects", "default.bin");
    private static final Path EXTERNALIZE_FILE_PATH = Paths.get("src", "test", "resources", "objects", "externalize.bin");
    private static final Path WITH_METHODS_FILE_PATH = Paths.get("src", "test", "resources", "objects", "methods.bin");
    private static final Path CUSTOM_FILE_PATH = Paths.get("src", "test", "resources", "objects", "custom.bin");
    private static final Serializer serializer = new Serializer();
    private static final Random rnd = new Random();

    @Before
    public void setUp() throws IOException {
        Files.createDirectories(DIR_PATH);
        Files.createFile(DEFAULT_FILE_PATH);
        Files.createFile(EXTERNALIZE_FILE_PATH);
        Files.createFile(WITH_METHODS_FILE_PATH);
        Files.createFile(CUSTOM_FILE_PATH);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(DIR_PATH.toFile());
    }

    @Test
    public void DefaultSerialize() throws IOException {
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < ANIMALS_SIZE; i++) {
            animals.add(generateAnimal());
        }

        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, DEFAULT_FILE_PATH.toString());
        long finish = System.currentTimeMillis();
        long serializationTime = finish - start;

        start = System.currentTimeMillis();
        List<Animal> readedAnimals = serializer.defaultDeserialize(DEFAULT_FILE_PATH.toString());
        finish = System.currentTimeMillis();
        long deserializationTime = finish - start;
        long fileSize = Files.size(DEFAULT_FILE_PATH);

        assertEquals(animals, readedAnimals);
        printResults("Default serialization", serializationTime, deserializationTime, fileSize);
    }

    @Test
    public void ExternalizableSerialize() throws IOException {
        List<AnimalExternalizable> animals = new ArrayList<>();
        for (int i = 0; i < ANIMALS_SIZE; i++) {
            animals.add(generateAnimalExternalizable());
        }

        long start = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animals, EXTERNALIZE_FILE_PATH.toString());
        long finish = System.currentTimeMillis();
        long serializationTime = finish - start;

        start = System.currentTimeMillis();
        List<AnimalExternalizable> readedAnimals = serializer.deserializeWithExternalizable(EXTERNALIZE_FILE_PATH.toString());
        finish = System.currentTimeMillis();
        long deserializationTime = finish - start;
        long fileSize = Files.size(EXTERNALIZE_FILE_PATH);

        assertEquals(animals, readedAnimals);
        printResults("Externalizable serialization", serializationTime, deserializationTime, fileSize);
    }

    @Test
    public void WithMethodsSerialize() throws IOException {
        List<AnimalWithMethods> animals = new ArrayList<>();
        for (int i = 0; i < ANIMALS_SIZE; i++) {
            animals.add(generateAnimalWithMethods());
        }

        long start = System.currentTimeMillis();
        serializer.serializeWithMethods(animals, WITH_METHODS_FILE_PATH.toString());
        long finish = System.currentTimeMillis();
        long serializationTime = finish - start;

        start = System.currentTimeMillis();
        List<AnimalWithMethods> readedAnimals = serializer.deserializeWithMethods(WITH_METHODS_FILE_PATH.toString());
        finish = System.currentTimeMillis();
        long deserializationTime = finish - start;
        long fileSize = Files.size(WITH_METHODS_FILE_PATH);

        assertEquals(animals, readedAnimals);
        printResults("With methods serialization", serializationTime, deserializationTime, fileSize);
    }

    @Test
    public void CustomSerialize() throws IOException {
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < ANIMALS_SIZE; i++) {
            animals.add(generateAnimal());
        }

        long start = System.currentTimeMillis();
        serializer.customSerialize(animals, CUSTOM_FILE_PATH.toString());
        long finish = System.currentTimeMillis();
        long serializationTime = finish - start;

        start = System.currentTimeMillis();
        List<Animal> readedAnimals = serializer.customDeserialize(CUSTOM_FILE_PATH.toString());
        finish = System.currentTimeMillis();
        long deserializationTime = finish - start;
        long fileSize = Files.size(CUSTOM_FILE_PATH);

        assertEquals(animals, readedAnimals);
        printResults("Custom serialization", serializationTime, deserializationTime, fileSize);
    }

    private static Animal generateAnimal() {
        if (!rnd.nextBoolean()) {
            return null;
        }
        return new Animal(generateString(), rnd.nextInt(), rnd.nextDouble(), rnd.nextBoolean(),
                AnimalType.randomAnimalType(), generateWorker());
    }

    private static Worker generateWorker() {
        if (!rnd.nextBoolean()) {
            return null;
        }
        return new Worker(generateString(), rnd.nextLong(), rnd.nextBoolean());
    }

    private static AnimalExternalizable generateAnimalExternalizable() {
        if (!rnd.nextBoolean()) {
            return null;
        }
        return new AnimalExternalizable(generateString(), rnd.nextInt(), rnd.nextDouble(), rnd.nextBoolean(),
                AnimalType.randomAnimalType(), generateWorkerExternalizable());
    }

    private static WorkerExternalizable generateWorkerExternalizable() {
        if (!rnd.nextBoolean()) {
            return null;
        }
        return new WorkerExternalizable(generateString(), rnd.nextLong(), rnd.nextBoolean());
    }

    private static AnimalWithMethods generateAnimalWithMethods() {
        if (!rnd.nextBoolean()) {
            return null;
        }
        return new AnimalWithMethods(generateString(), rnd.nextInt(), rnd.nextDouble(), rnd.nextBoolean(),
                AnimalType.randomAnimalType(), generateWorkerWithMethods());
    }

    private static WorkerWithMethods generateWorkerWithMethods() {
        if (!rnd.nextBoolean()) {
            return null;
        }
        return new WorkerWithMethods(generateString(), rnd.nextLong(), rnd.nextBoolean());
    }

    private static String generateString() {
        if (!rnd.nextBoolean()) {
            return null;
        }
        char[] chars = new char[rnd.nextInt(MAX_STRING_SIZE)];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (rnd.nextInt('z' - '0') + '0');
        }
        return new String(chars);
    }

    private static void printResults(String serialization, long serializationTime, long deserializationTime,
                                     long fileSize) {
        System.out.println(serialization);
        System.out.println("Serialization time: " + serializationTime + " ms");
        System.out.println("Deserialization time: " + deserializationTime + " ms");
        System.out.println("File size: " + fileSize + " bytes");
    }
}
