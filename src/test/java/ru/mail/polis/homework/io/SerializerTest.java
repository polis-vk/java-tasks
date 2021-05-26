package ru.mail.polis.homework.io;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.Fluffiness;
import ru.mail.polis.homework.io.objects.Serializer;
import ru.mail.polis.homework.io.objects.Stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SerializerTest {
    private static final int MAX_AGE = 20;
    private static final int MAX_HP = 100;
    private static final int MAX_DAMAGE = 100;
    private static final int MAX_ENERGY = 100;
    private static final int ANIMALS_TESTED_COUNT = 1000;

    @Before
    public void setUp() throws IOException {
        Path mainDirectory = Paths.get("src", "test", "resources", "serializer");
        Files.createDirectories(mainDirectory);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "serializer").toFile());
    }

    @Test
    public void testDefaultSerializerEmptyList()  {
        Path file = Paths.get("src", "test", "resources", "serializer", "empty.txt");
        Serializer.defaultSerialize(Collections.emptyList(), file.toString());
        List<Animal> outputAnimals = Serializer.defaultDeserialize(file.toString());
        assertTrue(outputAnimals.isEmpty());
    }

    @Test
    public void testDefaultSerialize()
    {
        List<Animal> animals = generateAnimalList();
        Path file = Paths.get("src", "test", "resources", "serializer", "default.txt");
        Serializer.defaultSerialize(animals, file.toString());
        assertEquals(animals, Serializer.defaultDeserialize(file.toString()));
    }

    @Test
    public void testCustomSerialize()
    {
        List<Animal> animals = generateAnimalList();
        Path file = Paths.get("src", "test", "resources", "serializer", "custom.txt");
        Serializer.defaultSerialize(animals, file.toString());
        assertEquals(animals, Serializer.defaultDeserialize(file.toString()));
    }

    @Test
    public void testComparisonDefaultAndCustomSerializers()
    {
        List<Animal> animals = generateAnimalList();
        Path file = Paths.get("src", "test", "resources", "serializer", "customComparison.txt");
        long defaultSerializeStart = System.currentTimeMillis();
        Serializer.defaultSerialize(animals, file.toString());
        long defaultSerializeEnd = System.currentTimeMillis();

        long defaultDeserializeStart = System.currentTimeMillis();
        List<Animal> outputAnimals = Serializer.defaultDeserialize(file.toString());
        long defaultDeserializeEnd = System.currentTimeMillis();
        assertEquals(animals, outputAnimals);
        long defaultFileSize = file.toFile().length();

        animals = generateAnimalList();
        file = Paths.get("src", "test", "resources", "serializer", "defaultComparison.txt");
        long customSerializeStart = System.currentTimeMillis();
        Serializer.customSerialize(animals, file.toString());
        long customSerializeEnd = System.currentTimeMillis();

        long customDeserializeStart = System.currentTimeMillis();
        outputAnimals = Serializer.customDeserialize(file.toString());
        long customDeserializeEnd = System.currentTimeMillis();
        assertEquals(animals, outputAnimals);
        long customFileSize = file.toFile().length();

        long defaultSerializeTime = defaultSerializeEnd - defaultSerializeStart;
        long defaultDeserializeTime = defaultDeserializeEnd - defaultDeserializeStart;
        System.out.println("Default:" +
                "\n serializer: " + defaultSerializeTime +
                "\n deserializer: " + defaultDeserializeTime +
                "\n file size: " + defaultFileSize);
        long customSerializeTime = customSerializeEnd - customSerializeStart;
        long customDeserializeTime = customDeserializeEnd - customDeserializeStart;
        System.out.println("Custom:" +
                "\n serializer: " + customSerializeTime +
                "\n deserializer: " + customDeserializeTime +
                "\n file size: " + customFileSize);
    }

    private List<Animal> generateAnimalList() {
        List<Animal> animals = new ArrayList<>(ANIMALS_TESTED_COUNT);
        for (int i = 0; i < ANIMALS_TESTED_COUNT; i++) {
            animals.add(generateAnimal());
        }
        return animals;
    }

    private static Animal generateAnimal()
    {
        return new Animal(generateName(), (int) (Math.random() * MAX_AGE + 1),
                Fluffiness.values()[(int) (Math.random() * Fluffiness.values().length)],
                new Stats(Math.random() * MAX_HP, Math.random() * MAX_DAMAGE, Math.random() * MAX_ENERGY));
    }

    private static String generateName()
    {
        int length = (int) (Math.random() * 8 + 3);
        StringBuilder name = new StringBuilder();

        name.append(Character.toUpperCase((char) ((int) (Math.random() * 26 + 97))));
        --length;
        for (int i = 0; i < length; ++i)
        {
            name.append((char) ((int) (Math.random() * 26 + 97)));
        }
        return name.toString();
    }
}
