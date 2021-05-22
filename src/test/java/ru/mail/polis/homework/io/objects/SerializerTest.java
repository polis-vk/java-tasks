package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.Color;
import ru.mail.polis.homework.io.objects.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final int ANIMALS_COUNT = 100;
    private Serializer serializer;
    private static final List<Animal> animals = new ArrayList<>();
    private static final String[] ALIAS = new String[]{
            "Liam", "Olivia",
            "Noah", "Emma",
            "Oliver", "Ava",
            "Elijah", "Charlotte",
            "William", "Sophia",
            "James", "Amelia",
            "Benjamin", "Isabella",
            "Lucas", "Mia",
            "Henry", "Evelyn",
            "Alexander", "Harper"
    };
    private static final String[] COLORS = new String[]{
            "AIR_FORCE_BLUE",
            "ALICE_BLUE",
            "ALIZARIN_CRIMSON",
            "ALMOND",
            "AMBER",
            "AMETHYST",
            "AZURE",
            "BATTLESHIP_GREY",
            "BISTRE",
            "BLUE",
            "BONDI_BLUE"
    };

    @Before
    public void initAnimals() {
        Random random = new Random();
        for (int i = 0; i < ANIMALS_COUNT; ++i) {
            Animal father = new Animal(
                    random.nextInt(),
                    ALIAS[random.nextInt(ALIAS.length - 1)],
                    Color.valueOf(COLORS[random.nextInt(COLORS.length - 1)]),
                    null,
                    null
            );
            Animal mother = new Animal(
                    random.nextInt(),
                    ALIAS[random.nextInt(ALIAS.length - 1)],
                    Color.valueOf(COLORS[random.nextInt(COLORS.length - 1)]),
                    null,
                    null
            );
            animals.add(new Animal(
                    random.nextInt(),
                    ALIAS[random.nextInt(ALIAS.length - 1)],
                    Color.valueOf(COLORS[random.nextInt(COLORS.length - 1)]),
                    father,
                    mother
            ));
        }
    }


    @Before
    public void setUp() throws IOException {
        serializer = new Serializer();
        Path newTestDir = Paths.get("src", "test", "resources", "serializerTest");
        Files.createDirectory(newTestDir);
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "serializerTest").toFile());
    }

    @Test
    public void defaultSerializeTest() {
        Path file = Paths.get("src", "test", "resources", "serializerTest",
                "defaultSerializeSingleListTest.txt");
        serializer.defaultSerialize(animals, file.toString());
        assertEquals(animals, serializer.defaultDeserialize(file.toString()));
    }

    @Test
    public void customSerializeTest() {
        Path file = Paths.get("src", "test", "resources", "serializerTest",
                "customSerializeSingleListTest.txt");
        serializer.customSerialize(animals, file.toString());
        assertEquals(animals, serializer.customDeserialize(file.toString()));
    }
}
