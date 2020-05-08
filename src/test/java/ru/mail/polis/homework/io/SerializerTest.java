package ru.mail.polis.homework.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class SerializerTest {
    private Serializer serializer;
    private final Path mainPath = Path.of("src", "test", "resources", "serializer");

    @Before
    public void setUp() throws IOException {
        serializer = new Serializer();
        if (!Files.exists(mainPath)) {
            Files.createDirectories(mainPath);
        }
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(mainPath);
    }

    @Test
    public void simpleEmptyListTest() {
        Path filePath = Path.of(mainPath + "Test1.txt");
        serializer.defaultSerialize(Collections.emptyList(), filePath.toString());
        assertTrue(serializer.defaultDeserialize(filePath.toString()).isEmpty());
    }
    @Test
    public void customEmptyTest() {
        Path filePath = Path.of(mainPath + "Test1.txt");
        serializer.customSerialize(Collections.emptyList(), filePath.toString());
        assertTrue(serializer.defaultDeserialize(filePath.toString()).isEmpty());
    }


    @Test
    public void simpleListTest() {
        Path filePath = Path.of(mainPath + "Test2.txt");
        List<Animal> animalList = createAnimalList();

        serializer.defaultSerialize(animalList, filePath.toString());
        assertEquals(animalList, serializer.defaultDeserialize(filePath.toString()));
    }

    // очень простенько))
    private List<Animal> createAnimalList() {
        List<Animal> animalList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Animal animal = new Animal("Panther", 5 + i, new Owner("Max", 32 + i), Eats.PREDATOR);
            animal.addEaten(new Food("Beef", 2.15 * i, new Date()));
            animal.addEaten(new Food("Water", 1.2 + i, new Date()));
            animal.addEaten(new Food("Fish", 0.15 + 0.3 * i, new Date()));
            animalList.add(animal);
        }
        return animalList;
    }
}
