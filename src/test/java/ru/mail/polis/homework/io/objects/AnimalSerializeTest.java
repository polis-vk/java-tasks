package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnimalSerializeTest {

    @Before
    public void setUp() throws IOException {
        Path file = Paths.get("src", "test", "resources", "directories", "serializable", "animal.txt");
        Files.createDirectories(file.getParent());
        Files.createFile(file);
    }


    @Test
    public void testSerializableOfOneAnimal() throws IOException {
        LocalDate date = LocalDate.of(2010, 12, 23);
        List<Animal> animals = Stream.of(new Animal("Zina", 20,false, false, AnimalType.CAT, date)).collect(Collectors.toList());
        Serializer serializer = new Serializer();
        serializer.defaultSerialize(animals, Paths.get("src", "test", "resources", "directories", "serializable", "animal.txt").toString());
        List<Animal> animals2 = serializer.defaultDeserialize(Paths.get("src", "test", "resources", "directories", "serializable", "animal.txt").toString());
        assertEquals(animals.get(0), animals2.get(0));
    }
}
