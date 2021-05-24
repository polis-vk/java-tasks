package ru.mail.polis.homework.io;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.Owner;
import ru.mail.polis.homework.io.objects.Serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SerializerTest {
    @Before
    public void setUp() throws Exception {
        Path dir = Paths.get("src", "test", "resources", "serializer", "simple");
        Files.createDirectories(dir);
        Path file = Paths.get("src", "test", "resources", "serializer", "simple", "file.txt");
        Files.createFile(file);
    }


    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "serializer", "simple").toFile());
    }

    @Test
    public void testSimpleSerialization() {
        Random r = new Random();
        Animal.Color[] colors = {Animal.Color.BLUE, Animal.Color.BLUE, Animal.Color.GREEN, Animal.Color.RED};
        List<Animal> animals = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            animals.add(getAnimal(r, colors[r.nextInt(4)]));
        }
        List<Animal> tmp = new ArrayList<>();
        tmp.addAll(0, animals);
        long timeOfWriting = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Serializer.defaultSerialize(tmp, "src\\test\\resources\\serializer\\simple\\file.txt");
            Collections.copy(tmp, animals);
        }
        timeOfWriting = System.currentTimeMillis() - timeOfWriting;

        long timeOfReading = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            List<Animal> animals1 = Serializer.defaultDeserialize("src\\test\\resources\\serializer\\simple\\file.txt");
            assertEquals(animals1, animals);
        }
        timeOfReading = System.currentTimeMillis() - timeOfReading;
        System.out.println("Writing: " + timeOfWriting + "\nReading: " + timeOfReading);
        //Среднее время
        //Writing: 29
        //Reading: 25
    }

    @Test
    public void testCustomSerialization() {
        Random r = new Random();
        Animal.Color[] colors = {Animal.Color.BLUE, Animal.Color.BLUE, Animal.Color.GREEN, Animal.Color.RED};
        List<Animal> animals = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            animals.add(getAnimal(r, colors[r.nextInt(4)]));
        }
        List<Animal> tmp = new ArrayList<>();
        tmp.addAll(0, animals);
        long timeOfWriting = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Serializer.customSerialize(tmp, "src\\test\\resources\\serializer\\simple\\file.txt");
            Collections.copy(tmp, animals);
        }
        timeOfWriting = System.currentTimeMillis() - timeOfWriting;

        long timeOfReading = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            List<Animal> animals1 = Serializer.customDeserialize("src\\test\\resources\\serializer\\simple\\file.txt");
            assertEquals(animals1, animals);
        }
        timeOfReading = System.currentTimeMillis() - timeOfReading;
        System.out.println("Writing: " + timeOfWriting + "\nReading: " + timeOfReading);
        //Среднее время
        //Writing: 6
        //Reading: 4
    }

    private Animal getAnimal(Random r, Animal.Color color) {
        return new Animal(r.nextInt(100), "" + (char) (r.nextInt(26) + 'a'),
                color,
                new Owner(r.nextInt(100), "" + (char) (r.nextInt(26) + 'a')));
    }
}
