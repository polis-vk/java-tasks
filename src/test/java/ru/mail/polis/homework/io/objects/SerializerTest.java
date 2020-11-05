package ru.mail.polis.homework.io.objects;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final int OBJECTS_QUANTITY = 10000;
    Serializer serializer = new Serializer();
    private final String fileName = "serializingTestsFile";

    private static final List<Animal> animals = new ArrayList<Animal>() {{
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < OBJECTS_QUANTITY; ++i) {
            Animal animal = new Animal(String.valueOf(random.nextInt()),
                    new BioTag(random.nextLong(), String.valueOf(random.nextInt())),
                    random.nextInt(),
                    Arrays.asList(String.valueOf(random.nextInt()), String.valueOf(random.nextInt()), String.valueOf(random.nextInt())),
                    Animal.Size.values()[random.nextInt(3)],
                    random.nextDouble(),
                    random.nextBoolean());
            add(animal);
        }
    }};

    private static final List<AnimalExternalizable> animalsExternalize = new ArrayList<AnimalExternalizable>() {{
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < OBJECTS_QUANTITY; ++i) {
            AnimalExternalizable animal = new AnimalExternalizable(String.valueOf(random.nextInt()),
                    new BioTag(random.nextLong(), String.valueOf(random.nextInt())),
                    random.nextInt(),
                    Arrays.asList(String.valueOf(random.nextInt()), String.valueOf(random.nextInt()), String.valueOf(random.nextInt())),
                    AnimalExternalizable.Size.values()[random.nextInt(3)],
                    random.nextDouble(),
                    random.nextBoolean());
            add(animal);
        }
    }};

    private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<AnimalWithMethods>() {{
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < OBJECTS_QUANTITY; ++i) {
            AnimalWithMethods animal = new AnimalWithMethods(String.valueOf(random.nextInt()),
                    new BioTag(random.nextLong(), String.valueOf(random.nextInt())),
                    random.nextInt(),
                    Arrays.asList(String.valueOf(random.nextInt()), String.valueOf(random.nextInt()), String.valueOf(random.nextInt())),
                    Animal.Size.values()[random.nextInt(3)],
                    random.nextDouble(),
                    random.nextBoolean());
            add(animal);
        }
    }};

    private static final List<Animal> animalsWithCustom = new ArrayList<Animal>() {{
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < OBJECTS_QUANTITY; ++i) {
            Animal animal = new Animal(String.valueOf(random.nextInt()),
                    new BioTag(random.nextLong(), String.valueOf(random.nextInt())),
                    random.nextInt(),
                    Arrays.asList(String.valueOf(random.nextInt()), String.valueOf(random.nextInt()), String.valueOf(random.nextInt())),
                    Animal.Size.values()[random.nextInt(3)],
                    random.nextDouble(),
                    random.nextBoolean());
            add(animal);
        }
    }};

    @After
    public void removeFile() {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    public void serializableWithDefault() {
        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, fileName);
        long serializingExitTime = System.currentTimeMillis();
        List<Animal> test = serializer.defaultDeserialize(fileName);
        long deserializeExitTime = System.currentTimeMillis();

        assertEquals(test, animals);

        printResults("Default", serializingExitTime - start, deserializeExitTime - start);
    }

    @Test
    public void serializableWithMethods() {
        long start = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, fileName);
        long serializingExitTime = System.currentTimeMillis();
        List<AnimalWithMethods> test = serializer.deserializeWithMethods(fileName);
        long deserializeExitTime = System.currentTimeMillis();

        assertEquals(test, animalsWithMethods);

        printResults("WithMethods", serializingExitTime - start, deserializeExitTime - start);
    }

    @Test
    public void serializableWithExternalize() {
        long start = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalize, fileName);
        long serializingExitTime = System.currentTimeMillis();
        List<AnimalExternalizable> test = serializer.deserializeWithExternalizable(fileName);
        long deserializeExitTime = System.currentTimeMillis();

        assertEquals(test, animalsExternalize);

        printResults("Externalize", serializingExitTime - start, deserializeExitTime - start);
    }

    @Test
    public void serializableWithCustom() {
        long start = System.currentTimeMillis();
        serializer.customSerialize(animalsWithCustom, fileName);
        long serializingExitTime = System.currentTimeMillis();
        List<Animal> test = serializer.customDeserialize(fileName);
        long deserializeExitTime = System.currentTimeMillis();

        assertEquals(test, animalsWithCustom);

        printResults("Custom", serializingExitTime - start, deserializeExitTime - start);
    }

    public void printResults(String header, long serTime, long desTime) {
        System.out.println(header + ": ");
        System.out.println("File size - " + new File(fileName).length() + " bytes.");
        System.out.println("Serializing time - " + serTime + " ms.");
        System.out.println("Deserializing time - " + desTime + " ms.");
        System.out.println();
    }
}
