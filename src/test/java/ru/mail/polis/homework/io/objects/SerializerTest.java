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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SerializerTest {
    private Serializer serializer;
    private List<Animal> allAnimals = new ArrayList<>();
    private List<Animal> simpleAnimal = new ArrayList<>();
    private List<Animal> parrots = new ArrayList<>();
    private List<Animal> fishes = new ArrayList<>();

    @Before
    public void setUp() throws IOException {
        serializer = new Serializer();
        Path mainDirectory = Paths.get("src", "test", "resources", "serializer");
        Files.createDirectories(mainDirectory);

        Animal cat = new Animal("Pushok", Type.Cat);
        Animal cow = new Animal("Burenka", Type.Cow);
        Animal dog = new Animal("Sharick", 10, Type.Dog);
        Animal parrotDad = new Animal("KeshaDad", 1, Type.Parrot);
        Animal parrotMum = new Animal("KeshaMum", 1, Type.Parrot);
        Animal parrotChild = new Animal("KeshaChild", 1, parrotDad, parrotMum, Type.Parrot);
        Animal fish1 = new Animal("White", Type.Fish);
        Animal fish2 = new Animal("Blue", 1, Type.Fish);
        Animal fish3 = new Animal("Red", 0, fish1, fish2, Type.Fish);
        Animal fish4 = new Animal("Black", 1, fish1, fish2, Type.Fish);
        Animal fish5 = new Animal("Orange", 0, fish2, fish3, Type.Fish);
        Animal fish6 = new Animal("Yellow", 1, fish5, fish1, Type.Fish);

        allAnimals.add(cat);
        allAnimals.add(cow);
        allAnimals.add(dog);
        allAnimals.add(parrotDad);
        allAnimals.add(parrotMum);
        allAnimals.add(parrotChild);
        allAnimals.add(fish1);
        allAnimals.add(fish2);
        allAnimals.add(fish3);
        allAnimals.add(fish4);
        allAnimals.add(fish5);
        allAnimals.add(fish6);

        simpleAnimal.add(cat);
        simpleAnimal.add(cow);
        simpleAnimal.add(dog);

        parrots.add(parrotDad);
        parrots.add(parrotMum);
        parrots.add(parrotChild);

        fishes.add(fish1);
        fishes.add(fish2);
        fishes.add(fish3);
        fishes.add(fish4);
        fishes.add(fish5);
        fishes.add(fish6);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "serializer").toFile());
    }


    @Test
    public void writeReadEmptyList() throws IOException, ClassNotFoundException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal1.bin");
        serializer.defaultSerialize(Collections.emptyList(), file.toString());
        List<Animal> outputAnimals = serializer.defaultDeserialize(file.toString());
        assertTrue(outputAnimals.isEmpty());
    }

    @Test
    public void writeReadEqualList() throws IOException, ClassNotFoundException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal2.bin");

        serializer.defaultSerialize(allAnimals, file.toString());
        List<Animal> outputAnimals = serializer.defaultDeserialize(file.toString());
        assertEquals(allAnimals, outputAnimals);
    }

    @Test
    public void writeReadEqualManyLists() throws IOException, ClassNotFoundException {
        Path file1 = Paths.get("src", "test", "resources", "serializer", "animal3.bin");
        Path file2 = Paths.get("src", "test", "resources", "serializer", "animal4.bin");
        Path file3 = Paths.get("src", "test", "resources", "serializer", "animal5.bin");
        Path file4 = Paths.get("src", "test", "resources", "serializer", "animal6.bin");


        serializer.defaultSerialize(allAnimals, file1.toString());
        List<Animal> outputAnimals1 = serializer.defaultDeserialize(file1.toString());
        serializer.defaultSerialize(simpleAnimal, file2.toString());
        List<Animal> outputAnimals2 = serializer.defaultDeserialize(file2.toString());
        serializer.defaultSerialize(parrots, file3.toString());
        List<Animal> outputAnimals3 = serializer.defaultDeserialize(file3.toString());
        serializer.defaultSerialize(fishes, file4.toString());
        List<Animal> outputAnimals4 = serializer.defaultDeserialize(file4.toString());

        assertEquals(allAnimals, outputAnimals1);
        assertEquals(simpleAnimal, outputAnimals2);
        assertEquals(parrots, outputAnimals3);
        assertEquals(fishes, outputAnimals4);
        assertNotEquals(allAnimals, outputAnimals2);
        assertNotEquals(allAnimals, outputAnimals3);
        assertNotEquals(fishes, outputAnimals1);
        assertNotEquals(fishes, outputAnimals2);
        assertNotEquals(fishes, outputAnimals3);
    }

    @Test
    public void writeReadEmptyListCustom() throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal1C.bin");
        serializer.customSerialize(Collections.emptyList(), file.toString());
        List<Animal> outputAnimals = serializer.customDeserialize(file.toString());
        assertTrue(outputAnimals.isEmpty());
    }

    @Test
    public void writeReadEqualListCustom() throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal2C.bin");

        serializer.customSerialize(simpleAnimal, file.toString());
        List<Animal> outputAnimals = serializer.customDeserialize(file.toString());
        assertEquals(simpleAnimal, outputAnimals);
    }

    @Test
    public void writeReadEqualManyListsCustom() throws IOException {
        Path file1 = Paths.get("src", "test", "resources", "serializer", "animal3C.bin");
        Path file2 = Paths.get("src", "test", "resources", "serializer", "animal4C.bin");
        Path file3 = Paths.get("src", "test", "resources", "serializer", "animal5C.bin");
        Path file4 = Paths.get("src", "test", "resources", "serializer", "animal6C.bin");


        serializer.customSerialize(allAnimals, file1.toString());
        List<Animal> outputAnimals1 = serializer.customDeserialize(file1.toString());
        serializer.customSerialize(simpleAnimal, file2.toString());
        List<Animal> outputAnimals2 = serializer.customDeserialize(file2.toString());
        serializer.customSerialize(parrots, file3.toString());
        List<Animal> outputAnimals3 = serializer.customDeserialize(file3.toString());
        serializer.customSerialize(fishes, file4.toString());
        List<Animal> outputAnimals4 = serializer.customDeserialize(file4.toString());

        assertEquals(allAnimals, outputAnimals1);
        assertEquals(simpleAnimal, outputAnimals2);
        assertEquals(parrots, outputAnimals3);
        assertEquals(fishes, outputAnimals4);
        assertNotEquals(allAnimals, outputAnimals2);
        assertNotEquals(allAnimals, outputAnimals3);
        assertNotEquals(fishes, outputAnimals1);
        assertNotEquals(fishes, outputAnimals2);
        assertNotEquals(fishes, outputAnimals3);
    }
}
