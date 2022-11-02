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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SerializerTest {
    private static final Random RANDOM = new Random();

    private static final Serializer SERIALIZER = new Serializer();
    private static final String WORKING_FILENAME = "ficko.txt";
    private static final Path WORKING_PATH = Paths.get(WORKING_FILENAME);
    private static final int MAX_ANIMAL_LEGS_COUNT = 4;
    private static final int SELECTION_COUNT = 6;

    @Before
    public void setUp() throws IOException {
        Files.deleteIfExists(WORKING_PATH);
        Files.createFile(WORKING_PATH);
    }

    @After
    public void undoSetUp() throws IOException {
        Files.deleteIfExists(WORKING_PATH);
    }

    @Test
    public void checkSerializeAndDeserializeWithExternalizableForCorrectness() {
        List<AnimalExternalizable> animals = Stream.generate(SerializerTest::generateAnimalExternalizable)
                .limit(SELECTION_COUNT)
                .collect(Collectors.toList());
        SERIALIZER.serializeWithExternalizable(animals, WORKING_FILENAME);
        List<AnimalExternalizable> animalsDeserialized = SERIALIZER.deserializeWithExternalizable(WORKING_FILENAME);
        assertEquals(animals, animalsDeserialized);
    }

    @Test
    public void checkDefaultSerializeAndDeserializeForCorrectness() {
        List<Animal> animals = Stream.generate(SerializerTest::generateAnimal)
                .limit(SELECTION_COUNT)
                .collect(Collectors.toList());
        SERIALIZER.defaultSerialize(animals, WORKING_FILENAME);
        List<Animal> animalsDeserialized = SERIALIZER.defaultDeserialize(WORKING_FILENAME);
        assertEquals(animals, animalsDeserialized);
    }

    @Test
    public void checkSerializeAndDeserializeWithMethodsForCorrectness() {
        List<AnimalWithMethods> animals = Stream.generate(SerializerTest::generateAnimalWithMethods)
                .limit(SELECTION_COUNT)
                .collect(Collectors.toList());
        SERIALIZER.serializeWithMethods(animals, WORKING_FILENAME);
        List<AnimalWithMethods> animalsDeserialized = SERIALIZER.deserializeWithMethods(WORKING_FILENAME);
        assertEquals(animals, animalsDeserialized);
    }

    private static Animal generateAnimal() {
        return new Animal(generateLetterString(),
                          RANDOM.nextInt(MAX_ANIMAL_LEGS_COUNT + 1),
                          RANDOM.nextBoolean(),
                          RANDOM.nextBoolean(),
                          generateOrganization(),
                          generateGender());
    }

    private static AnimalExternalizable generateAnimalExternalizable() {
        return new AnimalExternalizable(generateLetterString(),
                                        RANDOM.nextInt(MAX_ANIMAL_LEGS_COUNT + 1),
                                        RANDOM.nextBoolean(),
                                        RANDOM.nextBoolean(),
                                        generateOrganizationExternalizable(),
                                        generateGender());
    }

    private static AnimalWithMethods generateAnimalWithMethods() {
        return new AnimalWithMethods(generateLetterString(),
                                     RANDOM.nextInt(MAX_ANIMAL_LEGS_COUNT + 1),
                                     RANDOM.nextBoolean(),
                                     RANDOM.nextBoolean(),
                                     generateOrganizationWithMethods(),
                                     generateGender());
    }

    private static OrganizationWithMethods generateOrganizationWithMethods() {
        return new OrganizationWithMethods(generateLetterString(),
                                           generateLetterString(),
                                           RANDOM.nextInt());
    }

    private static Organization generateOrganization() {
        return new Organization(generateLetterString(),
                                generateLetterString(),
                                RANDOM.nextInt());
    }

    private static OrganizationExternalizable generateOrganizationExternalizable() {
        return new OrganizationExternalizable(generateLetterString(),
                                              generateLetterString(),
                                              RANDOM.nextInt());
    }

    private static Gender generateGender() {
        Gender[] genders = Gender.values();
        return genders[RANDOM.nextInt(genders.length)];
    }

    private static String generateLetterString() {
        return generateLetterString(20, 100);
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
}
