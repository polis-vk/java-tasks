package ru.mail.polis.homework.io.objects;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SerializerTest {
    private static final Random RANDOM = new Random();
    private static final Serializer SERIALIZER = new Serializer();
    private static final String WORKING_FILENAME = "ficko.bin";
    private static final Path WORKING_PATH = Paths.get(WORKING_FILENAME);
    private static final int ANIMAL_LEGS_MAX_COUNT = 4;
    private static final int SELECTION_COUNT = 750_000;
    private static final double NULL_OCCURRENCE_PROBABILITY = 0.05;

    @Before
    public void setUp() throws IOException {
        Files.deleteIfExists(WORKING_PATH);
        Files.createFile(WORKING_PATH);
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(WORKING_PATH);
    }

    @Test
    public void serializingWithExternalizable() throws IOException {
        serializingTestTemplate(nullableGenerator(SerializerTest::generateAnimalExternalizable),
                                SERIALIZER::serializeWithExternalizable,
                                SERIALIZER::deserializeWithExternalizable);
    }

    @Test
    public void defaultSerializing() throws IOException {
        serializingTestTemplate(nullableGenerator(SerializerTest::generateAnimal),
                                SERIALIZER::defaultSerialize,
                                SERIALIZER::defaultDeserialize);
    }

    @Test
    public void serializingWithMethods() throws IOException {
        serializingTestTemplate(nullableGenerator(SerializerTest::generateAnimalWithMethods),
                                SERIALIZER::serializeWithMethods,
                                SERIALIZER::deserializeWithMethods);
    }

    @Test
    public void customSerializing() throws IOException {
        serializingTestTemplate(nullableGenerator(SerializerTest::generateAnimal),
                                SERIALIZER::customSerialize,
                                SERIALIZER::customDeserialize);
    }

    public <T> void serializingTestTemplate(Supplier<T> generator,
                                            BiConsumer<List<T>, String> serializer,
                                            Function<String, List<T>> deserializer) throws IOException {
        List<T> generatedAnimals = Stream.generate(generator)
                                         .limit(SELECTION_COUNT)
                                         .collect(Collectors.toList());
        long timeMillisBeforeWriting = System.currentTimeMillis();
        serializer.accept(generatedAnimals, WORKING_FILENAME);
        long timeMillisAfterWriting = System.currentTimeMillis();
        List<T> deserializedAnimals = deserializer.apply(WORKING_FILENAME);
        long timeMillisAfterReading = System.currentTimeMillis();
        assertEquals(generatedAnimals, deserializedAnimals);
        System.out.printf("Размер файла: %d байт\n", Files.size(WORKING_PATH));
        System.out.printf("Время записи: %d мс\n", timeMillisAfterWriting - timeMillisBeforeWriting);
        System.out.printf("Время чтения: %d мс\n", timeMillisAfterReading - timeMillisAfterWriting);
    }

    private static Animal generateAnimal() {
        return new Animal(generateNullable(SerializerTest::generateLetterString),
                          RANDOM.nextInt(ANIMAL_LEGS_MAX_COUNT + 1),
                          RANDOM.nextBoolean(),
                          RANDOM.nextBoolean(),
                          generateNullable(SerializerTest::generateOrganization),
                          generateNullable(SerializerTest::generateGender));
    }

    private static AnimalExternalizable generateAnimalExternalizable() {
        return new AnimalExternalizable(generateNullable(SerializerTest::generateLetterString),
                                        RANDOM.nextInt(ANIMAL_LEGS_MAX_COUNT + 1),
                                        RANDOM.nextBoolean(),
                                        RANDOM.nextBoolean(),
                                        generateNullable(SerializerTest::generateOrganizationExternalizable),
                                        generateNullable(SerializerTest::generateGender));
    }

    private static AnimalWithMethods generateAnimalWithMethods() {
        return new AnimalWithMethods(generateNullable(SerializerTest::generateLetterString),
                                     RANDOM.nextInt(ANIMAL_LEGS_MAX_COUNT + 1),
                                     RANDOM.nextBoolean(),
                                     RANDOM.nextBoolean(),
                                     generateNullable(SerializerTest::generateOrganizationWithMethods),
                                     generateNullable(SerializerTest::generateGender));
    }

    private static OrganizationWithMethods generateOrganizationWithMethods() {
        return new OrganizationWithMethods(generateNullable(SerializerTest::generateLetterString),
                                           generateNullable(SerializerTest::generateLetterString),
                                           RANDOM.nextInt());
    }

    private static Organization generateOrganization() {
        return new Organization(generateNullable(SerializerTest::generateLetterString),
                                generateNullable(SerializerTest::generateLetterString),
                                RANDOM.nextInt());
    }

    private static OrganizationExternalizable generateOrganizationExternalizable() {
        return new OrganizationExternalizable(generateNullable(SerializerTest::generateLetterString),
                                              generateNullable(SerializerTest::generateLetterString),
                                              RANDOM.nextInt());
    }

    private static Gender generateGender() {
        Gender[] genders = Gender.values();
        return genders[RANDOM.nextInt(genders.length)];
    }

    private static String generateLetterString() {
        return generateLetterString(30, 40);
    }

    private static String generateLetterString(int minLength, int maxLength) {
        if (maxLength < minLength) {
            throw new IllegalArgumentException();
        }
        int letterStringLength = minLength + RANDOM.nextInt(maxLength - minLength + 1);
        StringBuilder letterStringBuilder = new StringBuilder();
        for (int i = 0; i < letterStringLength; i++) {
            char letter = (char) ('a' + RANDOM.nextInt('z' - 'a' + 1));
            letterStringBuilder.append(RANDOM.nextBoolean() ? letter : Character.toUpperCase(letter));
        }
        return letterStringBuilder.toString();
    }

    private static <T> Supplier<T> nullableGenerator(Supplier<T> supplier) {
        return () -> generateNullable(supplier);
    }

    private static <T> T generateNullable(Supplier<T> supplier) {
        if (RANDOM.nextDouble() <= NULL_OCCURRENCE_PROBABILITY) {
            return null;
        }
        return supplier.get();
    }
}
