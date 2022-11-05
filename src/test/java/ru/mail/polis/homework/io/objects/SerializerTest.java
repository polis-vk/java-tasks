package ru.mail.polis.homework.io.objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class SerializerTest {
    private static final int DATA_LENGTH = 100;
    private final Serializer serializer = new Serializer();

    private static class DummyForWarmUp {
        int act() {
            // Just a dummy logic
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                list.add(ThreadLocalRandom.current().nextInt(100));
            }
            list.sort(Comparator.comparingInt(o -> o));
            return list.get(0);
        }
    }

    // JVM needs to be warmed up when it's necessary to measure something
    @BeforeClass
    public static void warmUp() {
        System.out.println("Warmup is starting...");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            DummyForWarmUp dummyForWarmUp = new DummyForWarmUp();
            int a = dummyForWarmUp.act();
            list.add(a);
        }
        list.sort(Comparator.comparingInt(o -> o));
        System.out.println("Warmup has finished!");
        System.out.println();
    }

    private static Animal getRandomAnimal() {
        return new Animal(
                generateLocation(),
                generateString(),
                generateInt(),
                generateBoolean(),
                generateBoolean(),
                generateAnimalAbilityType()
        );
    }

    private static AnimalExternalizable getRandomAnimalExternalizable() {
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
        if (ThreadLocalRandom.current().nextBoolean()) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
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
        if (ThreadLocalRandom.current().nextBoolean()) return null;
        int randomIndex = ThreadLocalRandom.current().nextInt(0, AnimalAbilityType.values().length);
        return AnimalAbilityType.values()[randomIndex];
    }

    private static Location generateLocation() {
        if (ThreadLocalRandom.current().nextBoolean()) return null;
        return new Location(
                generateString(),
                generateString()
        );
    }

    private static List<Animal> getRandomAnimalList() {
        List<Animal> list = new ArrayList<>();
        for (int i = 0; i < DATA_LENGTH; i++) {
            list.add(getRandomAnimal());
        }
        return list;
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

    @Test
    public void testDefaultSerializeAndDeserialize() {
        List<Animal> animals = getRandomAnimalList();
        long start;
        long end;

        System.out.println("Default serialization:");
        start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, "default.bin");
        end = System.currentTimeMillis();
        System.out.println("Time spent on serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.defaultDeserialize("default.bin");
        end = System.currentTimeMillis();
        System.out.println("Time spent on deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);
    }

    @Test
    public void testSerializeAndDeserializeWithMethods() {
        List<AnimalWithMethods> animals = getRandomAnimalWithMethodsList();
        long start;
        long end;

        System.out.println("Serialization with methods:");
        start = System.currentTimeMillis();
        serializer.serializeWithMethods(animals, "withMethods.bin");
        end = System.currentTimeMillis();
        System.out.println("Time spent on serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods("withMethods.bin");
        end = System.currentTimeMillis();
        System.out.println("Time spent on deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);
    }

    @Test
    public void testSerializeAndDeserializeWithExternalizable() {
        List<AnimalExternalizable> animals = getRandomAnimalExternalizableList();
        long start;
        long end;

        System.out.println("Serialization with Externalizable:");
        start = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animals, "withExternalizable.bin");
        end = System.currentTimeMillis();
        System.out.println("Time spent on serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable("withExternalizable.bin");
        end = System.currentTimeMillis();
        System.out.println("Time spent on deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);
    }

    @Test
    public void testCustomSerializeAndDeserialize() {
        List<Animal> animals = getRandomAnimalList();
        long start;
        long end;

        System.out.println("Custom serialization:");
        start = System.currentTimeMillis();
        serializer.customSerialize(animals, "custom.bin");
        end = System.currentTimeMillis();
        System.out.println("Time spent on serializing: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.customDeserialize("custom.bin");
        end = System.currentTimeMillis();
        System.out.println("Time spent on deserializing: " + (end - start) + "ms");

        assertEquals(animals, deserializedAnimals);
    }
}