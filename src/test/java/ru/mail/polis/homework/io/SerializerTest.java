package ru.mail.polis.homework.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SerializerTest {
    
    private final Path DIRECTORY = Paths.get("src", "test", "resources", "serializer");
    private final Path FILE_PATH_1 = Paths.get("src", "test", "resources", "serializer", "fileForSerialize1.txt");
    private final Path FILE_PATH_2 = Paths.get("src", "test", "resources", "serializer", "fileForSerialize2.txt");
    private final Path FILE_PATH_3 = Paths.get("src", "test", "resources", "serializer", "fileForSerialize3.txt");
    private final Path FILE_PATH_4 = Paths.get("src", "test", "resources", "serializer", "fileForSerialize4.txt");
    private final Path FILE_PATH_5 = Paths.get("src", "test", "resources", "serializer", "fileForSerialize5.txt");
    private final Path FILE_PATH_6 = Paths.get("src", "test", "resources", "serializer", "fileForSerialize6.txt");
    
    private final static Serializer serializer = new Serializer();
    
    @Before
    public void setUp() throws IOException {
        Files.createDirectories(DIRECTORY);
        Files.createFile(FILE_PATH_1);
        Files.createFile(FILE_PATH_2);
        Files.createFile(FILE_PATH_3);
        Files.createFile(FILE_PATH_4);
        Files.createFile(FILE_PATH_5);
        Files.createFile(FILE_PATH_6);
    }
    
    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(FILE_PATH_1);
        Files.deleteIfExists(FILE_PATH_2);
        Files.deleteIfExists(FILE_PATH_3);
        Files.deleteIfExists(FILE_PATH_4);
        Files.deleteIfExists(FILE_PATH_5);
        Files.deleteIfExists(FILE_PATH_6);
        Files.deleteIfExists(DIRECTORY);
    }
    
    @Test
    public void emptyList() throws IOException, ClassNotFoundException {
        List<Animal> animals = new ArrayList<>();
        serializer.defaultSerialize(animals, FILE_PATH_1.toString());
        List<Animal> animalsFromFile = serializer.defaultDeserialize(FILE_PATH_1.toString());
        assertTrue(animalsFromFile.isEmpty());
    }
    
    @Test
    public void someList() throws IOException, ClassNotFoundException {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(10, "first", new Animal.Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals.add(new Animal(20, "second", new Animal.Kind("secondKind", 2000), Animal.Owner.PERSON));
        animals.add(new Animal(30, "third", new Animal.Kind("thirdKind", 3000), Animal.Owner.ZOO));
        animals.add(new Animal(40, "fourth", new Animal.Kind("fourthKind", 4000), Animal.Owner.PERSON));
        
        serializer.defaultSerialize(animals, FILE_PATH_2.toString());
        List<Animal> animalsFromFile = serializer.defaultDeserialize(FILE_PATH_2.toString());
        assertEquals(animals, animalsFromFile);
    }
    
    @Test
    public void differentLists() throws IOException, ClassNotFoundException {
        List<Animal> animals1 = new ArrayList<>();
        animals1.add(new Animal(10, "first", new Animal.Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals1.add(new Animal(20, "second", new Animal.Kind("secondKind", 2000), Animal.Owner.PERSON));
        
        List<Animal> animals2 = new ArrayList<>();
        animals2.add(new Animal(50, "first", new Animal.Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals2.add(new Animal(20, "second", new Animal.Kind("secondKind", 2000), Animal.Owner.PERSON));
        
        List<Animal> animals3 = new ArrayList<>();
        animals3.add(new Animal(10, "first", new Animal.Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals3.add(new Animal(20, "notFirst", new Animal.Kind("secondKind", 2000), Animal.Owner.PERSON));
        
        List<Animal> animals4 = new ArrayList<>();
        animals4.add(new Animal(10, "first", new Animal.Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals4.add(new Animal(20, "second", new Animal.Kind("secondKind", 2000), Animal.Owner.PERSON));
        
        serializer.defaultSerialize(animals1, FILE_PATH_3.toString());
        serializer.defaultSerialize(animals2, FILE_PATH_4.toString());
        serializer.defaultSerialize(animals3, FILE_PATH_5.toString());
        serializer.defaultSerialize(animals4, FILE_PATH_6.toString());
        
        List<Animal> animals1FromFile = serializer.defaultDeserialize(FILE_PATH_3.toString());
        List<Animal> animals2FromFile = serializer.defaultDeserialize(FILE_PATH_4.toString());
        List<Animal> animals3FromFile = serializer.defaultDeserialize(FILE_PATH_5.toString());
        List<Animal> animals4FromFile = serializer.defaultDeserialize(FILE_PATH_6.toString());
    
        assertEquals(animals1, animals4);
        assertEquals(animals1, animals1FromFile);
        assertEquals(animals4, animals4FromFile);
        assertNotEquals(animals1, animals2);
        assertNotEquals(animals1, animals3);
        assertEquals(animals2, animals2FromFile);
        assertEquals(animals3, animals3FromFile);
    }
}


