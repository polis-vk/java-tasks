package ru.mail.polis.homework.io.objects;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static List<Animal> animals;
    private static List<AnimalWithMethods> animalsWithMethods;
    private static List<AnimalExternalizable> animalsExternalizable;
    private static Serializer serializer;

    private static final double NULL_CHANCE = 0.1;
    private static final int MAX_STRING_LENGTH = 20;
    private static final int WARMUP_NUMBER_OF_SERIALIZATIONS = 2;
    private static final int MAX_NUMBER_OF_GENERATIONS = 500_000;
    private static final Path TEST_DIRECTORY_PATH = Paths.get("src", "test", "resources", "io", "objects");
    private static final Path TEST_FILE_PATH = Paths.get("src", "test", "resources", "io", "objects", "animals.bin");

    @BeforeClass
    public static void generateData() {
        animals = new ArrayList<>();
        animalsWithMethods = new ArrayList<>();
        animalsExternalizable = new ArrayList<>();
        serializer = new Serializer();
        Random random = new Random();

        for (int i = 0; i < MAX_NUMBER_OF_GENERATIONS; i++) {
            animals.add(generateAnimal(random));
            animalsWithMethods.add(generateAnimalWithMethods(random));
            animalsExternalizable.add(generateAnimalExternalizable(random));
        }
    }

    @AfterClass
    public static void deleteFile() throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH);
    }

    @Test
    public void testSerialization() throws IOException {
        for (int i = 0; i < WARMUP_NUMBER_OF_SERIALIZATIONS; i++) {
            defaultSerializationTest(true);
            serializationWithMethodsTest(true);
            serializationWithExternalizable(true);
            customSerializationTest(true);
        }

        defaultSerializationTest(false);
        serializationWithMethodsTest(false);
        serializationWithExternalizable(false);
        customSerializationTest(false);
    }

    private void defaultSerializationTest(boolean isWarmup) throws IOException {
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

        if (!isWarmup) {
            printResults("Default serialization", beforeSerialization, afterSerialization, afterDeserialization,
                    fileSize);
        }

        Files.deleteIfExists(TEST_FILE_PATH);
    }

    private void serializationWithMethodsTest(boolean isWarmup) throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH);
        Files.createDirectories(TEST_DIRECTORY_PATH);
        Files.createFile(TEST_FILE_PATH);

        long beforeSerialization = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, TEST_FILE_PATH.toString());
        long afterSerialization = System.currentTimeMillis();

        List<AnimalWithMethods> animalsDeserialized = serializer.deserializeWithMethods(TEST_FILE_PATH.toString());
        long afterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(TEST_FILE_PATH);

        assertEquals(animalsWithMethods, animalsDeserialized);

        if (!isWarmup) {
            printResults("Serialization with methods", beforeSerialization, afterSerialization,
                    afterDeserialization, fileSize);
        }

        Files.deleteIfExists(TEST_FILE_PATH);
    }

    private void serializationWithExternalizable(boolean isWarmup) throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH);
        Files.createDirectories(TEST_DIRECTORY_PATH);
        Files.createFile(TEST_FILE_PATH);

        long beforeSerialization = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, TEST_FILE_PATH.toString());
        long afterSerialization = System.currentTimeMillis();

        List<AnimalExternalizable> animalsDeserialized =
                serializer.deserializeWithExternalizable(TEST_FILE_PATH.toString());
        long afterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(TEST_FILE_PATH);

        assertEquals(animalsExternalizable, animalsDeserialized);

        if (!isWarmup) {
            printResults("Serialization with externalizable", beforeSerialization, afterSerialization,
                    afterDeserialization, fileSize);
        }

        Files.deleteIfExists(TEST_FILE_PATH);
    }

    private void customSerializationTest(boolean isWarmup) throws IOException {
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

        if (!isWarmup) {
            printResults("Custom serialization", beforeSerialization, afterSerialization, afterDeserialization,
                    fileSize);
        }

        Files.deleteIfExists(TEST_FILE_PATH);
    }

    private static Animal generateAnimal(Random random) {
        if (random.nextDouble() < NULL_CHANCE) {
            return null;
        }

        return new Animal(
                random.nextInt(),
                random.nextBoolean(),
                random.nextBoolean(),
                generateString(random),
                generateAnimalType(random),
                generateAnimalOwner(random)
        );
    }

    private static AnimalWithMethods generateAnimalWithMethods(Random random) {
        if (random.nextDouble() < NULL_CHANCE) {
            return null;
        }

        return new AnimalWithMethods(
                random.nextInt(),
                random.nextBoolean(),
                random.nextBoolean(),
                generateString(random),
                generateAnimalType(random),
                generateAnimalOwnerWithMethods(random)
        );
    }

    private static AnimalExternalizable generateAnimalExternalizable(Random random) {
        if (random.nextDouble() < NULL_CHANCE) {
            return null;
        }

        return new AnimalExternalizable(
                random.nextInt(),
                random.nextBoolean(),
                random.nextBoolean(),
                generateString(random),
                generateAnimalType(random),
                generateAnimalOwnerExternalizable(random)
        );
    }

    private static String generateString(Random random) {
        if (random.nextDouble() < NULL_CHANCE) {
            return null;
        }

        byte[] array = new byte[random.nextInt(MAX_STRING_LENGTH)];
        random.nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private static AnimalType generateAnimalType(Random random) {
        if (random.nextDouble() < NULL_CHANCE) {
            return null;
        }

        return AnimalType.values()[random.nextInt(6)];
    }

    private static AnimalOwner generateAnimalOwner(Random random) {
        if (random.nextDouble() < NULL_CHANCE) {
            return null;
        }

        return new AnimalOwner(
                generateString(random),
                generateString(random),
                random.nextLong()
        );
    }

    private static AnimalOwnerWithMethods generateAnimalOwnerWithMethods(Random random) {
        if (random.nextDouble() < NULL_CHANCE) {
            return null;
        }

        return new AnimalOwnerWithMethods(
                generateString(random),
                generateString(random),
                random.nextLong()
        );
    }

    private static AnimalOwnerExternalizable generateAnimalOwnerExternalizable(Random random) {
        if (random.nextDouble() < NULL_CHANCE) {
            return null;
        }

        return new AnimalOwnerExternalizable(
                generateString(random),
                generateString(random),
                random.nextLong()
        );
    }

    private void printResults(String serializationName, long beforeSerialization, long afterSerialization,
                              long afterDeserialization, long fileSize) {
        System.out.println(serializationName.toUpperCase(Locale.ROOT));
        System.out.println("\tРазмер файла: " + fileSize + " байт");
        System.out.println("\tВремя записи: " + (afterSerialization - beforeSerialization) + " мс");
        System.out.println("\tВремя чтения: " + (afterDeserialization - afterSerialization) + " мс");
        System.out.println();
    }
}
