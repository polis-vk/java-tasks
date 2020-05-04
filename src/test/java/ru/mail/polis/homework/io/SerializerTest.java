package ru.mail.polis.homework.io;

import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.Kind;
import ru.mail.polis.homework.io.objects.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SerializerTest {
    
    private final Path FILE_PATH = Paths.get("src", "test", "resources", "fileForSerialize.bin");
    private final Path FILE_PATH_1 = Paths.get("src", "test", "resources", "fileForSerialize1.bin");
    private final Path FILE_PATH_2 = Paths.get("src", "test", "resources", "fileForSerialize2.bin");
    private final Path FILE_PATH_3 = Paths.get("src", "test", "resources", "fileForSerialize3.bin");
    private final Path FILE_PATH_4 = Paths.get("src", "test", "resources", "fileForSerialize4.bin");
    
    private final static Serializer serializer = new Serializer();
    
    @Test
    public void emptyList() throws IOException {
        Files.createFile(FILE_PATH);
        List<Animal> animals = new ArrayList<>();
        serializer.defaultSerialize(animals, FILE_PATH.toString());
        List<Animal> animalsFromFile = serializer.defaultDeserialize(FILE_PATH.toString());
        assertTrue(animalsFromFile.isEmpty());
        Files.deleteIfExists(FILE_PATH);
    }
    
    @Test
    public void someList() throws IOException {
        Files.createFile(FILE_PATH);
        List<Animal> animals = new ArrayList<>();
        List<Integer> weightByLastTenDays = Arrays.asList(12, 2, 3, 4, 2, 1, 34, 5, 3, 2);
        animals.add(new Animal(10, "first", weightByLastTenDays, new Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals.add(new Animal(20, "second", weightByLastTenDays, new Kind("secondKind", 2000), Animal.Owner.PERSON));
        animals.add(new Animal(30, "third", weightByLastTenDays, new Kind("thirdKind", 3000), Animal.Owner.ZOO));
        animals.add(new Animal(40, "fourth", weightByLastTenDays, new Kind("fourthKind", 4000), Animal.Owner.PERSON));
        
        serializer.defaultSerialize(animals, FILE_PATH.toString());
        List<Animal> animalsFromFile = serializer.defaultDeserialize(FILE_PATH.toString());
        assertEquals(animals, animalsFromFile);
        Files.deleteIfExists(FILE_PATH);
    }
    
    @Test
    public void differentLists() throws IOException {
        List<Animal> animals1 = new ArrayList<>();
        List<Integer> weightByLastTenDays = Arrays.asList(12, 2, 3, 4, 2, 1, 34, 5, 3, 2);
        animals1.add(new Animal(10, "first", weightByLastTenDays, new Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals1.add(new Animal(20, "second", weightByLastTenDays, new Kind("secondKind", 2000), Animal.Owner.PERSON));
        
        List<Animal> animals2 = new ArrayList<>();
        animals2.add(new Animal(50, "first", weightByLastTenDays, new Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals2.add(new Animal(20, "second", weightByLastTenDays, new Kind("secondKind", 2000), Animal.Owner.PERSON));
        
        List<Animal> animals3 = new ArrayList<>();
        animals3.add(new Animal(10, "first", weightByLastTenDays, new Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals3.add(new Animal(20, "notFirst", weightByLastTenDays, new Kind("secondKind", 2000), Animal.Owner.PERSON));
        
        List<Animal> animals4 = new ArrayList<>();
        animals4.add(new Animal(10, "first", weightByLastTenDays, new Kind("firstKind", 1000), Animal.Owner.OWNER_LESS));
        animals4.add(new Animal(20, "second", weightByLastTenDays, new Kind("secondKind", 2000), Animal.Owner.PERSON));
        
        Files.createFile(FILE_PATH_1);
        Files.createFile(FILE_PATH_2);
        Files.createFile(FILE_PATH_3);
        Files.createFile(FILE_PATH_4);
        
        serializer.defaultSerialize(animals1, FILE_PATH_1.toString());
        serializer.defaultSerialize(animals2, FILE_PATH_2.toString());
        serializer.defaultSerialize(animals3, FILE_PATH_3.toString());
        serializer.defaultSerialize(animals4, FILE_PATH_4.toString());
        
        List<Animal> animals1FromFile = serializer.defaultDeserialize(FILE_PATH_1.toString());
        List<Animal> animals2FromFile = serializer.defaultDeserialize(FILE_PATH_2.toString());
        List<Animal> animals3FromFile = serializer.defaultDeserialize(FILE_PATH_3.toString());
        List<Animal> animals4FromFile = serializer.defaultDeserialize(FILE_PATH_4.toString());
        
        Files.deleteIfExists(FILE_PATH_1);
        Files.deleteIfExists(FILE_PATH_2);
        Files.deleteIfExists(FILE_PATH_3);
        Files.deleteIfExists(FILE_PATH_4);
        
        assertEquals(animals1, animals4);
        assertEquals(animals1, animals1FromFile);
        assertEquals(animals4, animals4FromFile);
        assertNotEquals(animals1, animals2);
        assertNotEquals(animals1, animals3);
        assertEquals(animals2, animals2FromFile);
        assertEquals(animals3, animals3FromFile);
    }
}


