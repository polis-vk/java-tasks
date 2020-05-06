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

    @Before
    public void setUp() throws IOException {
        serializer = new Serializer();
        Path mainDirectory = Paths.get("src", "test", "resources", "serializer");
        Files.createDirectories(mainDirectory);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "serializer").toFile());
    }


    @Test
    public void writeReadEmptyList()  {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal1.bin");
        serializer.defaultSerialize(Collections.emptyList(), file.toString());
        List<Animal> outputAnimals = serializer.defaultDeserialize(file.toString());
        assertTrue(outputAnimals.isEmpty());
    }

    @Test
    public void writeReadEqualList()  {
        Food grass = new Food("Grass", 10);
        Food meat = new Food("Meat", 1000);
        Food fish = new Food("Fish", 100);
        Food milk = new Food("Milk", 1);

        Animal cat = new Animal("Pushok", Type.Cat);
        cat.foods.add(meat);
        cat.foods.add(milk);
        cat.foods.add(fish);
        Animal cow = new Animal("Burenka", Type.Cow);
        cow.foods.add(grass);
        Animal dog = new Animal("Sharick", 10, Type.Dog);
        dog.foods.add(meat);
        dog.foods.add(milk);
        Animal parrotDad = new Animal("KeshaDad", 1, Type.Parrot);
        parrotDad.foods.add(grass);
        Animal parrotMum = new Animal("KeshaMum", 1, Type.Parrot);
        parrotMum.foods.add(grass);
        Animal parrotChild = new Animal("KeshaChild", 1, parrotDad, parrotMum, Type.Parrot);
        parrotChild.foods.add(grass);
        Animal fish1 = new Animal("White", Type.Fish);
        Animal fish2 = new Animal("Blue", 1, Type.Fish);
        Animal fish3 = new Animal("Red", 0, fish1, fish2, Type.Fish);
        Animal fish4 = new Animal("Black", 1, fish1, fish2, Type.Fish);
        Animal fish5 = new Animal("Orange", 0, fish2, fish3, Type.Fish);
        Animal fish6 = new Animal("Yellow", 1, fish5, fish1, Type.Fish);

        List<Animal> allAnimals = new ArrayList<>();

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

        Path file = Paths.get("src", "test", "resources", "serializer", "animal2.bin");
        serializer.defaultSerialize(allAnimals, file.toString());
        List<Animal> outputAnimals = serializer.defaultDeserialize(file.toString());
        assertEquals(allAnimals, outputAnimals);
    }

    @Test
    public void writeReadEqualManyLists() {
        Food grass = new Food("Grass", 10);
        Food meat = new Food("Meat", 1000);
        Food fish = new Food("Fish", 100);
        Food milk = new Food("Milk", 1);

        Animal cat = new Animal("Pushok", Type.Cat);
        cat.foods.add(meat);
        cat.foods.add(milk);
        cat.foods.add(fish);
        Animal cow = new Animal("Burenka", Type.Cow);
        cow.foods.add(grass);
        Animal dog = new Animal("Sharick", 10, Type.Dog);
        dog.foods.add(meat);
        dog.foods.add(milk);
        Animal parrotDad = new Animal("KeshaDad", 1, Type.Parrot);
        parrotDad.foods.add(grass);
        Animal parrotMum = new Animal("KeshaMum", 1, Type.Parrot);
        parrotMum.foods.add(grass);
        Animal parrotChild = new Animal("KeshaChild", 1, parrotDad, parrotMum, Type.Parrot);
        parrotChild.foods.add(grass);
        Animal fish1 = new Animal("White", Type.Fish);
        Animal fish2 = new Animal("Blue", 1, Type.Fish);
        Animal fish3 = new Animal("Red", 0, fish1, fish2, Type.Fish);
        Animal fish4 = new Animal("Black", 1, fish1, fish2, Type.Fish);
        Animal fish5 = new Animal("Orange", 0, fish2, fish3, Type.Fish);
        Animal fish6 = new Animal("Yellow", 1, fish5, fish1, Type.Fish);

        List<Animal> allAnimals = new ArrayList<>();
        List<Animal> simpleAnimal = new ArrayList<>();
        List<Animal> parrots = new ArrayList<>();
        List<Animal> fishes = new ArrayList<>();

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
    public void writeReadEmptyListCustom() {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal1C.bin");
        serializer.customSerialize(Collections.emptyList(), file.toString());
        List<Animal> outputAnimals = serializer.customDeserialize(file.toString());
        assertTrue(outputAnimals.isEmpty());
    }

    @Test
    public void writeReadEqualListCustom() {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal2C.bin");
        Food grass = new Food("Grass", 10);
        Food meat = new Food("Meat", 1000);
        Food fish = new Food("Fish", 100);
        Food milk = new Food("Milk", 1);

        Animal cat = new Animal("Pushok", Type.Cat);
        cat.foods.add(meat);
        cat.foods.add(milk);
        cat.foods.add(fish);
        Animal cow = new Animal("Burenka", Type.Cow);
        cow.foods.add(grass);
        Animal dog = new Animal("Sharick", 10, Type.Dog);
        dog.foods.add(meat);
        dog.foods.add(milk);
        List<Animal> simpleAnimal = new ArrayList<>();
        simpleAnimal.add(cat);
        simpleAnimal.add(cow);
        simpleAnimal.add(dog);

        serializer.customSerialize(simpleAnimal, file.toString());
        List<Animal> outputAnimals = serializer.customDeserialize(file.toString());
        assertEquals(simpleAnimal, outputAnimals);
    }

    @Test
    public void writeReadEqualManyListsCustom() {
        Food grass = new Food("Grass", 10);
        Food meat = new Food("Meat", 1000);
        Food fish = new Food("Fish", 100);
        Food milk = new Food("Milk", 1);

        Animal cat = new Animal("Pushok", Type.Cat);
        cat.foods.add(meat);
        cat.foods.add(milk);
        cat.foods.add(fish);
        Animal cow = new Animal("Burenka", Type.Cow);
        cow.foods.add(grass);
        Animal dog = new Animal("Sharick", 10, Type.Dog);
        dog.foods.add(meat);
        dog.foods.add(milk);
        Animal parrotDad = new Animal("KeshaDad", 1, Type.Parrot);
        parrotDad.foods.add(grass);
        Animal parrotMum = new Animal("KeshaMum", 1, Type.Parrot);
        parrotMum.foods.add(grass);
        Animal parrotChild = new Animal("KeshaChild", 1, parrotDad, parrotMum, Type.Parrot);
        parrotChild.foods.add(grass);
        Animal fish1 = new Animal("White", Type.Fish);
        Animal fish2 = new Animal("Blue", 1, Type.Fish);
        Animal fish3 = new Animal("Red", 0, fish1, fish2, Type.Fish);
        Animal fish4 = new Animal("Black", 1, fish1, fish2, Type.Fish);
        Animal fish5 = new Animal("Orange", 0, fish2, fish3, Type.Fish);
        Animal fish6 = new Animal("Yellow", 1, fish5, fish1, Type.Fish);

        List<Animal> allAnimals = new ArrayList<>();
        List<Animal> simpleAnimal = new ArrayList<>();
        List<Animal> parrots = new ArrayList<>();
        List<Animal> fishes = new ArrayList<>();

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
