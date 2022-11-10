package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializerTest {

    private static final int ANIMALS_COUNT = 1000000;
    private static final int MAX_STRING_LENGTH = 10;
    private static final Path TEST_DIRECTORY_PATH =
            Paths.get("src", "test", "resources", "io", "objects");
    private static final Path TEST_FILE_PATH =
            Paths.get("src", "test", "resources", "io", "objects", "animals.test");

    private static Serializer serializer;

    private List<Animal> createAnimals() {
        Random random = new Random();
        List<Animal> animalList = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            animalList.add(new Animal(
                    random.nextBoolean(),
                    random.nextBoolean(),
                    (short) random.nextInt(),
                    generateString(random),
                    random.nextBoolean() ? AnimalSex.MALE : AnimalSex.FEMALE,
                    new Zoo(
                            random.nextInt(),
                            generateString(random)
                    )

            ));
        }
        return animalList;
    }

    private List<AnimalWithMethods> createAnimalsWithMethods() {
        Random random = new Random();
        List<AnimalWithMethods> animalList = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            animalList.add(new AnimalWithMethods(
                    random.nextBoolean(),
                    random.nextBoolean(),
                    (short) random.nextInt(),
                    generateString(random),
                    random.nextBoolean() ? AnimalSex.MALE : AnimalSex.FEMALE,
                    new Zoo(
                            random.nextInt(),
                            generateString(random)
                    )

            ));
        }
        return animalList;
    }

    private List<AnimalExternalizable> createAnimalsExternalizable() {
        Random random = new Random();
        List<AnimalExternalizable> animalList = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            animalList.add(new AnimalExternalizable(
                    random.nextBoolean(),
                    random.nextBoolean(),
                    (short) random.nextInt(),
                    generateString(random),
                    random.nextBoolean() ? AnimalSex.MALE : AnimalSex.FEMALE,
                    new Zoo(
                            random.nextInt(),
                            generateString(random)
                    )

            ));
        }
        return animalList;
    }

    @Test
    public void runAllTests() throws IOException {
        defaultSerializeAndDeserializeTest();
        withMethodsSerializeAndDeserializeTest();
        externalizableSerializeAndDeserializeTest();
        customSerializeAndDeserializeTest();
    }

    @Test
    public void defaultSerializeAndDeserializeTest() throws IOException {
        List<Animal> animals = createAnimals();
        serializer = new Serializer();

        Files.deleteIfExists(TEST_FILE_PATH);
        Files.createDirectories(TEST_DIRECTORY_PATH);
        Files.createFile(TEST_FILE_PATH);

        long beforeSerialization = System.currentTimeMillis();
        serializer.defaultSerialize(animals, TEST_FILE_PATH.toString());
        long afterSerialization = System.currentTimeMillis();

        List<Animal> animalsDeserialized = serializer.defaultDeserialize(TEST_FILE_PATH.toString());
        long afterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(TEST_FILE_PATH);

        assertEquals(animals, animalsDeserialized);

        printResults("DEFAULT serialization/deserialization",
                beforeSerialization, afterSerialization, afterDeserialization, fileSize);

        Files.deleteIfExists(TEST_FILE_PATH);

    }

    @Test
    public void withMethodsSerializeAndDeserializeTest() throws IOException {
        List<AnimalWithMethods> animals = createAnimalsWithMethods();
        serializer = new Serializer();

        Files.deleteIfExists(TEST_FILE_PATH);
        Files.createDirectories(TEST_DIRECTORY_PATH);
        Files.createFile(TEST_FILE_PATH);

        long beforeSerialization = System.currentTimeMillis();
        serializer.serializeWithMethods(animals, TEST_FILE_PATH.toString());
        long afterSerialization = System.currentTimeMillis();

        List<AnimalWithMethods> animalsDeserialized = serializer.deserializeWithMethods(TEST_FILE_PATH.toString());
        long afterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(TEST_FILE_PATH);

        assertEquals(animals, animalsDeserialized);

        printResults("WITH METHODS serialization/deserialization",
                beforeSerialization, afterSerialization, afterDeserialization, fileSize);

        Files.deleteIfExists(TEST_FILE_PATH);
    }

    @Test
    public void externalizableSerializeAndDeserializeTest() throws IOException {
        List<AnimalExternalizable> animals = createAnimalsExternalizable();
        serializer = new Serializer();

        Files.deleteIfExists(TEST_FILE_PATH);
        Files.createDirectories(TEST_DIRECTORY_PATH);
        Files.createFile(TEST_FILE_PATH);

        long beforeSerialization = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animals, TEST_FILE_PATH.toString());
        long afterSerialization = System.currentTimeMillis();

        List<AnimalExternalizable> animalsDeserialized = serializer.deserializeWithExternalizable(TEST_FILE_PATH.toString());
        long afterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(TEST_FILE_PATH);

        assertEquals(animals, animalsDeserialized);

        printResults("EXTERNALIZABLE serialization/deserialization",
                beforeSerialization, afterSerialization, afterDeserialization, fileSize);

        Files.deleteIfExists(TEST_FILE_PATH);
    }

    @Test
    public void customSerializeAndDeserializeTest() throws IOException {
        List<Animal> animals = createAnimals();
        serializer = new Serializer();

        Files.deleteIfExists(TEST_FILE_PATH);
        Files.createDirectories(TEST_DIRECTORY_PATH);
        Files.createFile(TEST_FILE_PATH);

        long beforeSerialization = System.currentTimeMillis();
        serializer.customSerialize(animals, TEST_FILE_PATH.toString());
        long afterSerialization = System.currentTimeMillis();

        List<Animal> animalsDeserialized = serializer.customDeserialize(TEST_FILE_PATH.toString());
        long afterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(TEST_FILE_PATH);

        assertEquals(animals, animalsDeserialized);

        printResults("CUSTOM serialization/deserialization",
                beforeSerialization, afterSerialization, afterDeserialization, fileSize);

        Files.deleteIfExists(TEST_FILE_PATH);
    }

    private void printResults(String serializationName, long beforeSerialization, long afterSerialization,
                              long afterDeserialization, long fileSize) {
        System.out.println(serializationName);
        System.out.println("\tFile size: " + fileSize + " byte");
        System.out.println("\tWrite time: " + (afterSerialization - beforeSerialization) + " ms");
        System.out.println("\tRead time: " + (afterDeserialization - afterSerialization) + " ms");
        System.out.println();
    }

    private static String generateString(Random random) {
        byte[] array = new byte[random.nextInt(MAX_STRING_LENGTH)];
        random.nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
