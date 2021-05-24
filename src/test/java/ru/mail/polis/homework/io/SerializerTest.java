package ru.mail.polis.homework.io;


import net.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.Serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class SerializerTest {

    private static final List<Animal> animals = new ArrayList<>();
    private static final int SIZE_LIST = 1000;
    private static final String FILENAME = "file.bin";


    @Before
    public void setUp(){
        Random random = new Random();
        for (int i = 0; i < SIZE_LIST; i++) {
            animals.add(new Animal(RandomString.make(), random.nextInt(), Animal.PhylumOfAnimals.getRandom(random)));
        }
    }

    @Test
    public void testDefault() {
        Serializer serializer = new Serializer();
        serializer.defaultSerialize(animals, FILENAME);
        assertArrayEquals(animals.toArray(), serializer.defaultDeserialize(FILENAME).toArray());
    }

    @Test
    public void testCustom()  {
        Serializer serializer = new Serializer();
        serializer.customSerialize(animals, FILENAME);
        assertArrayEquals(animals.toArray(), serializer.customDeserialize(FILENAME).toArray());
    }

}