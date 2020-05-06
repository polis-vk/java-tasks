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
    private List<Animal> manyAnimalsList;
    private List<Animal> dogsList;
    private List<Animal> catsList;

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
    public void defaultSerializeEmptyListTest() throws IOException, ClassNotFoundException {
        Path file = Paths.get("src", "test", "resources", "serializerTest",
                "defaultSerializeEmptyListTest.txt");
        serializer.defaultSerialize(Collections.emptyList(), file.toString());
        assertTrue(serializer.defaultDeserialize(file.toString()).isEmpty());
    }

    @Test
    public void customSerializeEmptyListTest() throws IOException, ClassNotFoundException {
        Path file = Paths.get("src", "test", "resources", "serializerTest",
                "customSerializeEmptyListTest.txt");

        serializer.customSerialize(Collections.emptyList(), file.toString());
        assertTrue(serializer.customDeserialize(file.toString()).isEmpty());
    }

    @Test
    public void defaultSerializeSingleListTest() throws IOException, ClassNotFoundException {
        Path file = Paths.get("src", "test", "resources", "serializerTest",
                "defaultSerializeSingleListTest.txt");
        setDogsList();
        serializer.defaultSerialize(dogsList, file.toString());
        assertEquals(dogsList, serializer.defaultDeserialize(file.toString()));
    }

    @Test
    public void customSerializeSingleListTest() throws IOException, ClassNotFoundException {
        Path file = Paths.get("src", "test", "resources", "serializerTest",
                "customSerializeSingleListTest.txt");
        setCatsList();
        serializer.customSerialize(catsList, file.toString());
        assertEquals(catsList, serializer.customDeserialize(file.toString()));
    }

    @Test
    public void defaultSerializeManyListTest() throws IOException, ClassNotFoundException {
        Path file1 = Paths.get("src", "test", "resources", "serializerTest",
                "defaultSerializeManyListTest1.txt");
        Path file2 = Paths.get("src", "test", "resources", "serializerTest",
                "defaultSerializeManyListTest2.txt");
        Path file3 = Paths.get("src", "test", "resources", "serializerTest",
                "defaultSerializeManyListTest3.txt");

        setManyAnimalsList();
        setDogsList();
        setCatsList();
        serializer.defaultSerialize(manyAnimalsList, file1.toString());
        assertEquals(manyAnimalsList, serializer.defaultDeserialize(file1.toString()));
        serializer.defaultSerialize(dogsList, file2.toString());
        assertEquals(dogsList, serializer.defaultDeserialize(file2.toString()));
        serializer.defaultSerialize(catsList, file3.toString());
        assertEquals(catsList, serializer.defaultDeserialize(file3.toString()));
    }

    @Test
    public void customSerializeManyListTest() throws IOException, ClassNotFoundException {
        Path file1 = Paths.get("src", "test", "resources", "serializerTest",
                "customSerializeManyListTest.txt");
        Path file2 = Paths.get("src", "test", "resources", "serializerTest",
                "customSerializeManyListTest.txt");
        Path file3 = Paths.get("src", "test", "resources", "serializerTest",
                "customSerializeManyListTest.txt");

        setManyAnimalsList();
        setDogsList();
        setCatsList();
        serializer.customSerialize(manyAnimalsList, file1.toString());
        List<Animal> check = serializer.customDeserialize(file1.toString());
        assertEquals(manyAnimalsList, check);
        serializer.customSerialize(dogsList, file2.toString());
        assertEquals(dogsList, serializer.customDeserialize(file2.toString()));
        serializer.customSerialize(catsList, file3.toString());
        assertEquals(catsList, serializer.customDeserialize(file3.toString()));
    }

    private void setManyAnimalsList() {
        manyAnimalsList = new ArrayList<>();
        Animal dog1 = new Animal(1, "Bob", Type.Dog);
        Animal cat1 = new Animal(4, "Bib", Type.Dog);
        Animal lion = new Animal(27, "Sherhan", Type.Lion);
        Animal crocodile = new Animal(7, "Gena", Type.Crocodile);
        Animal fish = new Animal(2, "Dory", Type.Fish);

        manyAnimalsList.add(dog1);
        manyAnimalsList.add(cat1);
        manyAnimalsList.add(lion);
        manyAnimalsList.add(crocodile);
        manyAnimalsList.add(fish);
    }

    private void setDogsList() {
        dogsList = new ArrayList<>();
        Animal dog1 = new Animal(1, "Bob", Type.Dog);
        Animal dog2 = new Animal(10, "Dave", Type.Dog);
        Animal dog3 = new Animal(3, "Pit", Type.Dog);
        Animal dog4 = new Animal(9, "Rog", Type.Dog);
        Animal dog5 = new Animal(11, "Lol", dog3, dog4, Type.Dog);

        dogsList.add(dog1);
        dogsList.add(dog2);
        dogsList.add(dog3);
        dogsList.add(dog4);
        dogsList.add(dog5);
    }

    private void setCatsList() {
        catsList = new ArrayList<>();
        Animal cat1 = new Animal(4, "Bib", Type.Cat);
        Animal cat2 = new Animal(1, "Bup", Type.Cat);
        Animal cat3 = new Animal(3, "Lel", Type.Cat);
        Animal cat4 = new Animal(4, "Mel", Type.Cat);
        Animal cat5 = new Animal(9, "Lol", cat3, cat4, Type.Cat);

        catsList.add(cat1);
        catsList.add(cat2);
        catsList.add(cat3);
        catsList.add(cat4);
        catsList.add(cat5);
    }
}
