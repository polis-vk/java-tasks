package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SerializerTest {
    private static final int OBJECTS_QUANTITY = 50000;
    private static final Path PATH = Paths.get("src", "test", "resources", "objects");
    private static final String FILE_PATH = "src/test/resources/objects/data.bin";
    private static final Serializer serializer = new Serializer();
    private static final Random random = new Random();
    private static final MoveType[] moveTypes = MoveType.values();

    @Before
    public void setUp() {
        try {
            Files.createDirectories(PATH);
            Files.createFile(Paths.get(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void reset() {
        try {
            Files.delete(Paths.get(FILE_PATH));
            Files.delete(PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDefaultSerializeAndDeserialize() {
        List<Animal> originalAnimals = new ArrayList<>();
        for (int i = 0; i < OBJECTS_QUANTITY + random.nextInt(200); i++) {
            originalAnimals.add(randomAnimal());
        }
        long start;
        long end;

        start = System.currentTimeMillis();
        serializer.defaultSerialize(originalAnimals, FILE_PATH);
        end = System.currentTimeMillis();
        System.out.println("Default serialization: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(FILE_PATH);
        end = System.currentTimeMillis();
        System.out.println("Default Deserialization: " + (end - start) + " ms");

        Assert.assertEquals(originalAnimals, deserializedAnimals);
    }

    @Test
    public void testSerializeAndDeserializeWithExternalizable() {
        List<AnimalExternalizable> originalAnimals = new ArrayList<>();
        for (int i = 0; i < OBJECTS_QUANTITY + random.nextInt(200); i++) {
            originalAnimals.add(randomAnimalExternalizable());
        }
        long start;
        long end;

        start = System.currentTimeMillis();
        serializer.serializeWithExternalizable(originalAnimals, FILE_PATH);
        end = System.currentTimeMillis();
        System.out.println("Serialization with Externalizable: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable(FILE_PATH);
        end = System.currentTimeMillis();
        System.out.println("Serialization with Externalizable: " + (end - start) + " ms");

        Assert.assertEquals(originalAnimals, deserializedAnimals);
    }

    @Test
    public void testSerializeAndDeserializeWithMethods() {
        List<AnimalWithMethods> originalAnimals = new ArrayList<>();
        for (int i = 0; i < OBJECTS_QUANTITY + random.nextInt(200); i++) {
            originalAnimals.add(randomAnimalWithMethods());
        }
        long start;
        long end;

        start = System.currentTimeMillis();
        serializer.serializeWithMethods(originalAnimals, FILE_PATH);
        end = System.currentTimeMillis();
        System.out.println("Serialization with Methods: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods(FILE_PATH);
        end = System.currentTimeMillis();
        System.out.println("Serialization with Methods: " + (end - start) + " ms");

        Assert.assertEquals(originalAnimals, deserializedAnimals);
    }

    private static Animal randomAnimal() {
        return new Animal(
                randomInt(),
                randomBoolean(),
                randomBoolean(),
                randomString(),
                randomMoveType(),
                randomAnimalDescription()
        );
    }

    private static AnimalExternalizable randomAnimalExternalizable() {
        return new AnimalExternalizable(
                randomInt(),
                randomBoolean(),
                randomBoolean(),
                randomString(),
                randomMoveType(),
                randomAnimalDescriptionExternalizable()
        );
    }

    private static AnimalWithMethods randomAnimalWithMethods() {
        return new AnimalWithMethods(
                randomInt(),
                randomBoolean(),
                randomBoolean(),
                randomString(),
                randomMoveType(),
                randomAnimalDescriptionWithMethods()
        );
    }

    private static Animal.AnimalDescription randomAnimalDescription() {
        return new Animal.AnimalDescription(randomInt(), randomBoolean(), randomString());
    }

    private static AnimalWithMethods.AnimalDescription randomAnimalDescriptionWithMethods() {
        return new AnimalWithMethods.AnimalDescription(randomInt(), randomBoolean(), randomString());
    }

    private static AnimalExternalizable.AnimalDescription randomAnimalDescriptionExternalizable() {
        return new AnimalExternalizable.AnimalDescription(randomInt(), randomBoolean(), randomString());
    }

    private static MoveType randomMoveType() {
        return moveTypes[random.nextInt(moveTypes.length)];
    }

    private static int randomInt() {
        return random.nextInt(10);
    }

    private static boolean randomBoolean() {
        return random.nextBoolean();
    }

    private static String randomString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < randomInt() + 3; i++) {
            stringBuilder.append((char) (random.nextInt('z' - 'a' + 1) + 'a'));
        }
        return stringBuilder.toString();
    }
}
