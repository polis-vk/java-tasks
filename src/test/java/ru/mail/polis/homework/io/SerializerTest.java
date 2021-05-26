package ru.mail.polis.homework.io;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;

public class SerializerTest {
    @Before
    public void setUp() throws Exception {
        Path dir = Paths.get("src", "test", "resources", "serializer");
        Files.createDirectories(dir);
        Path file = Paths.get("src", "test", "resources", "serializer", "file.txt");
        Files.createFile(file);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "serializer").toFile());
    }

    @Test
    public void testSimpleSerialization() {
        List<Animal> animals = generateRandomList(100);
        Path file = Paths.get("src", "test", "resources", "serializer", "file.txt");

        List<Animal> tmp = new ArrayList<>();
        tmp.addAll(0, animals);

        long writing = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Serializer.defaultSerialize(tmp, file.toString());
            Collections.copy(tmp, animals);
        }
        writing = System.currentTimeMillis() - writing;

        long reading = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Serializer.defaultDeserialize(file.toString());
        }
        reading = System.currentTimeMillis() - reading;

        System.out.println("Simple serialize:");
        System.out.println("Writing: " + writing + "\nReading: " + reading);
//        Simple serialize:
//        Writing: 133
//        Reading: 197
    }

    @Test
    public void testCustomSerialization() {
        List<Animal> animals = generateRandomList(100);
        Path file = Paths.get("src", "test", "resources", "serializer", "file.txt");

        List<Animal> tmp = new ArrayList<>();
        tmp.addAll(0, animals);

        long writing = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Serializer.customSerialize(tmp, file.toString());
            Collections.copy(tmp, animals);
        }
        writing = System.currentTimeMillis() - writing;

        long reading = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Serializer.customDeserialize(file.toString());
        }
        reading = System.currentTimeMillis() - reading;

        System.out.println("Custom serialize:");
        System.out.println("Writing: " + writing + "\nReading: " + reading);
//        Custom serialize:
//        Writing: 24
//        Reading: 20
    }

    List<Animal> generateRandomList(int capacity) {
        Random rand = new Random();
        List<Animal> animals = new ArrayList<>(capacity);
        Animal.Colour[] colours = {Animal.Colour.BLACK, Animal.Colour.WHITE, Animal.Colour.BROWN, Animal.Colour.GRAY};
        for(int i = 0; i < capacity; i++)
        {
            Zoo temp = new Zoo("" + (char)(rand.nextInt(26) + 'a'), "" + (char)(rand.nextInt(26) + 'a'));
            animals.add(new Animal(temp, "" + (char)(rand.nextInt(26) + 'a'), (byte) rand.nextInt(70), colours[rand.nextInt(4)]));
        }
        return animals;
    }

}
