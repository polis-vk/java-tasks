package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializerTest {

    private static final Path DIR_PATH = Paths.get(
            "src", "test", "resources", "directories", "objects");
    private static final Path DEFAULT_FILE_PATH = Paths.get(
            "src", "test", "resources", "directories", "objects", "default.txt");
    private static final Path EXTERNALIZABLE_FILE_PATH = Paths.get(
            "src", "test", "resources", "directories", "objects", "externalizable.txt");
    private static final Path WITH_METHODS_FILE_PATH = Paths.get(
            "src", "test", "resources", "directories", "objects", "with-methods.txt");
    private static final Path CUSTOM_FILE_PATH = Paths.get(
            "src", "test", "resources", "directories", "objects", "custom.txt");
    private static final int MIN_STR_LEN = 5;
    private static final int MAX_STR_LEN = 20;
    private static final int FIRST_ASCII_CODE = 97;
    private static final int LAST_ASCII_CODE = 122;
    private static final int ANIMALS_COUNT = 1_000_000;
    private static final Serializer SERIALIZER = new Serializer();
    private static Random random;
    private static final double NULL_PERCENT = 30.0;

    @Before
    public void setUp() throws Exception {
        Files.createDirectories(DIR_PATH);
        random = new Random();
    }

    @Test
    public void serializationTest() throws Exception {
        System.out.println("-----------------------------ПЕРВЫЙ ПРОГОН-----------------------------");
        doTests();
        System.out.println();

        System.out.println("-----------------------------ВТОРОЙ ПРОГОН-----------------------------");
        doTests();
        System.out.println();

        System.out.println("-----------------------------ТРЕТИЙ ПРОГОН-----------------------------");
        doTests();
    }

    public void doTests() throws Exception {
        defaultSerializationTest();
        withMethodsSerializationTest();
        externalizableSerializationTest();
        customSerializationTest();
    }

    public void defaultSerializationTest() throws Exception {
        Files.deleteIfExists(DEFAULT_FILE_PATH);

        List<Animal> generatedAnimals = generateAnimals();

        long millisBeforeSerialization = System.currentTimeMillis();
        SERIALIZER.defaultSerialize(generatedAnimals, DEFAULT_FILE_PATH.toString());
        long millisAfterSerialization = System.currentTimeMillis();

        List<Animal> deserializedAnimals = SERIALIZER.defaultDeserialize(DEFAULT_FILE_PATH.toString());
        long millisAfterDeserialization = System.currentTimeMillis();
        long fileLength = getFileLength(DEFAULT_FILE_PATH);

        assertEquals(generatedAnimals.size(), deserializedAnimals.size());
        assertEquals(generatedAnimals, deserializedAnimals);

        printInfoMessage("Дефолтная сериализация", fileLength, millisBeforeSerialization,
                millisAfterSerialization, millisAfterDeserialization);
    }

    public void withMethodsSerializationTest() throws Exception {
        Files.deleteIfExists(WITH_METHODS_FILE_PATH);

        List<AnimalWithMethods> generatedAnimals = generateAnimalsWithMethods();

        long millisBeforeSerialization = System.currentTimeMillis();
        SERIALIZER.serializeWithMethods(generatedAnimals, WITH_METHODS_FILE_PATH.toString());
        long millisAfterSerialization = System.currentTimeMillis();

        List<AnimalWithMethods> deserializedAnimals =
                SERIALIZER.deserializeWithMethods(WITH_METHODS_FILE_PATH.toString());
        long millisAfterDeserialization = System.currentTimeMillis();
        long fileLength = getFileLength(WITH_METHODS_FILE_PATH);

        assertEquals(generatedAnimals.size(), deserializedAnimals.size());
        assertEquals(generatedAnimals, deserializedAnimals);

        printInfoMessage("Сериализация с методами", fileLength, millisBeforeSerialization,
                millisAfterSerialization, millisAfterDeserialization);
    }

    public void externalizableSerializationTest() throws Exception {
        Files.deleteIfExists(EXTERNALIZABLE_FILE_PATH);

        List<AnimalExternalizable> generatedAnimals = generateAnimalsExternalizable();

        long millisBeforeSerialization = System.currentTimeMillis();
        SERIALIZER.serializeWithExternalizable(generatedAnimals, EXTERNALIZABLE_FILE_PATH.toString());
        long millisAfterSerialization = System.currentTimeMillis();

        List<AnimalExternalizable> deserializedAnimals =
                SERIALIZER.deserializeWithExternalizable(EXTERNALIZABLE_FILE_PATH.toString());
        long millisAfterDeserialization = System.currentTimeMillis();
        long fileLength = getFileLength(EXTERNALIZABLE_FILE_PATH);

        assertEquals(generatedAnimals.size(), deserializedAnimals.size());
        assertEquals(generatedAnimals, deserializedAnimals);

        printInfoMessage("Сериализация с Externalizable", fileLength, millisBeforeSerialization,
                millisAfterSerialization, millisAfterDeserialization);
    }

    public void customSerializationTest() throws Exception {
        Files.deleteIfExists(CUSTOM_FILE_PATH);

        List<Animal> generatedAnimals = generateAnimals();

        long millisBeforeSerialization = System.currentTimeMillis();
        SERIALIZER.customSerialize(generatedAnimals, CUSTOM_FILE_PATH.toString());
        long millisAfterSerialization = System.currentTimeMillis();

        List<Animal> deserializedAnimals = SERIALIZER.customDeserialize(CUSTOM_FILE_PATH.toString());
        long millisAfterDeserialization = System.currentTimeMillis();
        long fileLength = getFileLength(CUSTOM_FILE_PATH);

        assertEquals(generatedAnimals.size(), deserializedAnimals.size());
        assertEquals(generatedAnimals, deserializedAnimals);

        printInfoMessage("Кастомная сериализация", fileLength, millisBeforeSerialization,
                millisAfterSerialization, millisAfterDeserialization);
    }

    private int generateInt() {
        return Math.abs(random.nextInt());
    }

    private int generateInt(int min, int max) {
        return min + Math.abs(random.nextInt()) % (max - min + 1);
    }

    private String generateString() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        char[] result = new char[generateInt(MIN_STR_LEN, MAX_STR_LEN)];

        for (int i = 0; i < result.length; i++) {
            result[i] = (char) generateInt(FIRST_ASCII_CODE, LAST_ASCII_CODE);
        }

        return new String(result);
    }

    private boolean generateBoolean() {
        return random.nextBoolean();
    }

    private AnimalType generateAnimalType() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        AnimalType[] values = AnimalType.values();
        return values[generateInt(0, values.length - 1)];
    }

    private Organization generateOrganization() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        return new Organization(
                generateString(),
                generateBoolean(),
                generateInt()
        );
    }

    private Animal generateAnimal() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        return new Animal(
                generateString(),
                generateBoolean(),
                generateBoolean(),
                generateInt(),
                generateAnimalType(),
                generateOrganization()
        );
    }

    private AnimalTypeWithMethods generateAnimalTypeWithMethods() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        AnimalTypeWithMethods[] values = AnimalTypeWithMethods.values();
        return values[generateInt(0, values.length - 1)];
    }

    private OrganizationWithMethods generateOrganizationWithMethods() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        return new OrganizationWithMethods(
                generateString(),
                generateBoolean(),
                generateInt()
        );
    }

    private AnimalWithMethods generateAnimalWithMethods() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        return new AnimalWithMethods(
                generateString(),
                generateBoolean(),
                generateBoolean(),
                generateInt(),
                generateAnimalTypeWithMethods(),
                generateOrganizationWithMethods()
        );
    }

    private AnimalTypeExternalizable generateAnimalTypeExternalizable() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        AnimalTypeExternalizable[] values = AnimalTypeExternalizable.values();
        return values[generateInt(0, values.length - 1)];
    }

    private OrganizationExternalizable generateOrganizationExternalizable() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        return new OrganizationExternalizable(
                generateString(),
                generateBoolean(),
                generateInt()
        );
    }

    private AnimalExternalizable generateAnimalExternalizable() {
        if (isNullWithProbability(NULL_PERCENT)) {
            return null;
        }

        return new AnimalExternalizable(
                generateString(),
                generateBoolean(),
                generateBoolean(),
                generateInt(),
                generateAnimalTypeExternalizable(),
                generateOrganizationExternalizable()
        );
    }


    private List<Animal> generateAnimals() {
        List<Animal> result = new ArrayList<>();

        for (int i = 0; i < ANIMALS_COUNT; i++) {
            result.add(generateAnimal());
        }

        return result;
    }

    private List<AnimalWithMethods> generateAnimalsWithMethods() {
        List<AnimalWithMethods> result = new ArrayList<>();

        for (int i = 0; i < ANIMALS_COUNT; i++) {
            result.add(generateAnimalWithMethods());
        }

        return result;
    }

    private List<AnimalExternalizable> generateAnimalsExternalizable() {
        List<AnimalExternalizable> result = new ArrayList<>();

        for (int i = 0; i < ANIMALS_COUNT; i++) {
            result.add(generateAnimalExternalizable());
        }

        return result;
    }

    private long getFileLength(Path filePath) throws IOException {
        return Files.size(filePath);
    }

    private void printInfoMessage(
            String serializationType,
            long fileLength,
            long millisBeforeSerialization,
            long millisAfterSerialization,
            long millisAfterDeserialization
    ) {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(serializationType + " прошла успешно.");
        System.out.println("Размер получившегося файла: " + fileLength + " байт.");
        System.out.println("Время сериализации: " + (millisAfterSerialization - millisBeforeSerialization) + " " +
                "миллисекунд.");
        System.out.println("Время десериализации: " + (millisAfterDeserialization - millisAfterSerialization) + " " +
                "миллисекунд.");
        System.out.println("-------------------------------------------------------------------------");
    }

    private boolean isNullWithProbability(double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException();
        }

        return Math.abs(random.nextInt()) < Integer.MAX_VALUE * (percent / 100);
    }
}
