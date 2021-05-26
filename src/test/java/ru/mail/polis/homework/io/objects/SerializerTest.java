package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final int ANIMALS_NUMBER = 100;
    private Serializer serializer;
    private static final List<Animal> animalsList = new ArrayList<>();
    private static final String[] NAMES = new String[]{
            "Diluc", "Albedo",
            "Kaeya", "Tartaglia",
            "Venti", "Zhongli",
            "Xiao", "Chongyun",
            "Keqing", "Rosaria",
            "Dainsleif", "Scaramouche",
            "Baizhu", "Traveler",
    };
    private static final String[] SPECIES = new String[]{
            "FOX",
            "ELEPHANT",
            "JAGUAR",
            "OCELOT",
            "ALPACA",
            "PENGUIN",
            "BISON",
            "WOLF",
            "MOOSE",
            "KANGAROO",
            "SEAL"
    };
    private static final String[] AREALS = new String[]{
            "AFRICA",
            "EURASIA",
            "ANTARCTICA",
            "AUSTRALIA",
            "NORTH_AMERICA",
            "SOUTH_AMERICA"
    };

    @Before
    public void setUp() throws IOException {
        serializer = new Serializer();
        Path testDirectory = Paths.get("src", "test", "resources", "serializerTest");
        Files.createDirectory(testDirectory);
    }

    @Before
    public void initAnimals() {
        Random random = new Random();
        for (int i = 0; i < ANIMALS_NUMBER; ++i) {
            Animal ancestor = new Animal(
                    random.nextInt(),
                    NAMES[random.nextInt(NAMES.length - 1)],
                    Species.valueOf(SPECIES[random.nextInt(SPECIES.length - 1)]),
                    Areal.valueOf(AREALS[random.nextInt(AREALS.length - 1)]),
                    null
            );
            animalsList.add(new Animal(
                    random.nextInt(),
                    NAMES[random.nextInt(NAMES.length - 1)],
                    Species.valueOf(SPECIES[random.nextInt(SPECIES.length - 1)]),
                    Areal.valueOf(AREALS[random.nextInt(AREALS.length - 1)]),
                    ancestor
            ));
        }
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "serializerTest").toFile());
    }

    @Test
    public void defaultSerializeTest() {
        long start = System.currentTimeMillis();
        Path file = Paths.get("src", "test", "resources", "serializerTest",
                "defaultSerializeTest.txt");
        serializer.defaultSerialize(animalsList, file.toString());
        long finish = System.currentTimeMillis();
        assertEquals(animalsList, serializer.defaultDeserialize(file.toString()));

        StringBuilder info = new StringBuilder();
        info.append("Default serialize\nSize of file: ");
        try {
            info.append(Files.size(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        info.append(" bytes\nTime serialize and deserialize: ");
        info.append(finish - start);
        info.append("ms");
        System.out.println(info);
    }

    @Test
    public void customSerializeTest() {
        long start = System.currentTimeMillis();
        Path file = Paths.get("src", "test", "resources", "serializerTest",
                "customSerializeTest.txt");
        serializer.customSerialize(animalsList, file.toString());
        long finish = System.currentTimeMillis();
        assertEquals(animalsList, serializer.customDeserialize(file.toString()));

        StringBuilder info = new StringBuilder();
        info.append("Custom serialize\nSize of file: ");
        try {
            info.append(Files.size(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        info.append(" bytes\nTime serialize and deserialize: ");
        info.append(finish - start);
        info.append("ms");
        System.out.println(info);
    }
}
