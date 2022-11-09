package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class SerializerTest {
    private static final int DATA_LENGTH = 300_000;
    private final Serializer serializer = new Serializer();

    @BeforeClass
    public static void warmUp() {
        Serializer serializer = new Serializer();
        final String filename = "test.bin";
        for (int i = 0; i < 5; i++) {
            serializer.defaultSerialize(getRandomAnimalList(10_000), filename);
            List<Animal> deserializedAnimals = serializer.defaultDeserialize(filename);
            deserializedAnimals = deserializedAnimals.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            deserializedAnimals.sort(Comparator.comparingInt(Animal::getAge));
            tearDown(filename);
        }
    }

    private static Animal getRandomAnimal() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }
        return new Animal(
                generateString(),
                generateInt(),
                generateBoolean(),
                generateBoolean(),
                generateAnimalAbilityType(),
                generateLocation()
        );
    }

    private static AnimalExternalizable getRandomAnimalExternalizable() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }
        return new AnimalExternalizable(
                generateString(),
                generateInt(),
                generateBoolean(),
                generateBoolean(),
                generateAnimalAbilityType(),
                generateLocation()
        );
    }

    private static AnimalWithMethods getRandomAnimalWithMethods() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }
        return new AnimalWithMethods(
                generateString(),
                generateInt(),
                generateBoolean(),
                generateBoolean(),
                generateAnimalAbilityType(),
                generateLocation()
        );
    }

    private static String generateString() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int size = ThreadLocalRandom.current().nextInt(5, 25);
        for (int i = 0; i < size; i++) {
            sb.append((char) ThreadLocalRandom.current().nextInt('a', 'z'));
        }
        return sb.toString();
    }

    private static int generateInt() {
        return ThreadLocalRandom.current().nextInt(1, 100);
    }

    private static boolean generateBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static AnimalAbilityType generateAnimalAbilityType() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(0, AnimalAbilityType.values().length);
        return AnimalAbilityType.values()[randomIndex];
    }

    private static Location generateLocation() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }
        return new Location(
                generateString(),
                generateString()
        );
    }

    private static List<Animal> getRandomAnimalList(int size) {
        List<Animal> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandomAnimal());
        }
        return list;
    }

    private static List<Animal> getRandomAnimalList() {
        return getRandomAnimalList(DATA_LENGTH);
    }

    private static List<AnimalExternalizable> getRandomAnimalExternalizableList() {
        List<AnimalExternalizable> list = new ArrayList<>();
        for (int i = 0; i < DATA_LENGTH; i++) {
            list.add(getRandomAnimalExternalizable());
        }
        return list;
    }

    private static List<AnimalWithMethods> getRandomAnimalWithMethodsList() {
        List<AnimalWithMethods> list = new ArrayList<>();
        for (int i = 0; i < DATA_LENGTH; i++) {
            list.add(getRandomAnimalWithMethods());
        }
        return list;
    }

    private static void tearDown(String filename) {
        try {
            Files.delete(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDefaultSerializeAndDeserialize() {
        List<Animal> animals = getRandomAnimalList();
        long start;
        long end;
        final String filename = "default.bin";

        System.out.println("Default serialization:");
        start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, filename);
        end = System.currentTimeMillis();
        System.out.println("Time spent on serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(filename);
        end = System.currentTimeMillis();
        System.out.println("Time spent on deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);

        tearDown(filename);
    }

    @Test
    public void testSerializeAndDeserializeWithMethods() {
        List<AnimalWithMethods> animals = getRandomAnimalWithMethodsList();
        long start;
        long end;
        final String filename = "withMethods.bin";

        System.out.println("Serialization with methods:");
        start = System.currentTimeMillis();
        serializer.serializeWithMethods(animals, filename);
        end = System.currentTimeMillis();
        System.out.println("Time spent on serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods(filename);
        end = System.currentTimeMillis();
        System.out.println("Time spent on deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);

        tearDown(filename);
    }

    @Test
    public void testSerializeAndDeserializeWithExternalizable() {
        List<AnimalExternalizable> animals = getRandomAnimalExternalizableList();
        long start;
        long end;
        final String filename = "withExternalizable.bin";

        System.out.println("Serialization with Externalizable:");
        start = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animals, filename);
        end = System.currentTimeMillis();
        System.out.println("Time spent on serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable(filename);
        end = System.currentTimeMillis();
        System.out.println("Time spent on deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);

        tearDown(filename);
    }

    @Test
    public void testCustomSerializeAndDeserialize() {
        List<Animal> animals = getRandomAnimalList();
        long start;
        long end;
        final String filename = "custom.bin";

        System.out.println("Custom serialization:");
        start = System.currentTimeMillis();
        serializer.customSerialize(animals, filename);
        end = System.currentTimeMillis();
        System.out.println("Time spent on serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.customDeserialize(filename);
        end = System.currentTimeMillis();
        System.out.println("Time spent on deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);

        tearDown(filename);
    }
}
