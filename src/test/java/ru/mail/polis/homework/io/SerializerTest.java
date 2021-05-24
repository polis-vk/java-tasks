package ru.mail.polis.homework.io;


import net.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.PhylumOfAnimals;
import ru.mail.polis.homework.io.objects.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class SerializerTest {

    private static final List<Animal> animals = new ArrayList<>();
    private static final int SIZE_LIST = 100000;
    private static final String FILENAME = "file.bin";


    @Before
    public void setUp() {
        Random random = new Random();
        Animal animal;
        List<String> foods;
        int size;
        for (int i = 0; i < SIZE_LIST; i++) {
            animal = new Animal(RandomString.make(), random.nextInt(), PhylumOfAnimals.getRandom(random));
            foods = new ArrayList<>();
            size = random.nextInt(10);
            for (int j = 0; j < size; j++) {
                foods.add(RandomString.make());
            }
            animal.setFoods(foods);
            animals.add(animal);
        }
    }

    @Test
    public void testDefault() {
        long start = System.currentTimeMillis();
        Serializer serializer = new Serializer();
        serializer.defaultSerialize(animals, FILENAME);
        long finish = System.currentTimeMillis();
        assertArrayEquals(animals.toArray(), serializer.defaultDeserialize(FILENAME).toArray());


        StringBuilder info = new StringBuilder();
        info.append("Default serialize\nSize of file: ");
        try {
            info.append(Files.size(Path.of(FILENAME)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        info.append(" bytes\nTime serialize and deserialize: " );
        info.append(finish - start);
        info.append(" ms");

        System.out.println(info);
    }

    @Test
    public void testCustom() {
        long start = System.currentTimeMillis();
        Serializer serializer = new Serializer();
        serializer.customSerialize(animals, FILENAME);
        long finish = System.currentTimeMillis();
        assertArrayEquals(animals.toArray(), serializer.customDeserialize(FILENAME).toArray());

        StringBuilder info = new StringBuilder();
        info.append("Custom serialize\nSize of file: ");
        try {
            info.append(Files.size(Path.of(FILENAME)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        info.append(" bytes\nTime serialize and deserialize: " );
        info.append(finish - start);
        info.append(" ms");

        System.out.println(info);
    }

}