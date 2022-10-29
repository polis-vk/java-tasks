package ru.mail.polis.homework.io.objects;

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

    private static final Path DIR_PATH = Paths.get(
            "src", "test", "resources", "directories", "objects");
    private static final Path DEFAULT_FILE_PATH = Paths.get(
            "src", "test", "resources", "directories", "objects", "default.txt");
    private static final int MIN_STR_LEN = 5;
    private static final int MAX_STR_LEN = 20;
    private static final int FIRST_ASCII_CODE = 97;
    private static final int LAST_ASCII_CODE = 122;
    private static final int ANIMALS_COUNT = 200000;
    private static final Serializer SERIALIZER = new Serializer();

    @Before
    public void setUp() throws Exception {
        Files.createDirectories(DIR_PATH);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(DIR_PATH.toFile());
    }

    @Test
    public void defaultSerializationTest() throws Exception {
        List<Animal> generatedAnimals = generateAnimals(ANIMALS_COUNT);

        SERIALIZER.defaultSerialize(generatedAnimals, DEFAULT_FILE_PATH.toString());

        List<Animal> deserializedAnimals = SERIALIZER.defaultDeserialize(DEFAULT_FILE_PATH.toString());

        assertEquals(generatedAnimals, deserializedAnimals);
        for (int i = 0; i < generatedAnimals.size(); i++) {
            assertEquals(generatedAnimals.get(i), deserializedAnimals.get(i));
        }
    }

    private int generateInt() {
        Random random = new Random();
        return Math.abs(random.nextInt());
    }

    private int generateInt(int min, int max) {
        Random random = new Random();
        return min + Math.abs(random.nextInt()) % (max - min + 1);
    }

    private String generateString() {
        char[] result = new char[generateInt(MIN_STR_LEN, MAX_STR_LEN)];

        for (int i = 0; i < result.length; i++) {
            result[i] = (char) generateInt(FIRST_ASCII_CODE, LAST_ASCII_CODE);
        }

        return new String(result);
    }

    private boolean generateBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private AnimalType generateAnimalType() {
        AnimalType[] values = AnimalType.values();
        return values[generateInt(0, values.length - 1)];
    }

    private Organization generateOrganization() {
        return new Organization(
                generateString(),
                generateBoolean(),
                generateInt()
        );
    }

    private Animal generateAnimal() {
        return new Animal(
                generateString(),
                generateBoolean(),
                generateBoolean(),
                generateInt(),
                generateAnimalType(),
                generateOrganization()
        );
    }

    private List<Animal> generateAnimals(int count) {
        List<Animal> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            result.add(generateAnimal());
        }

        return result;
    }
}
