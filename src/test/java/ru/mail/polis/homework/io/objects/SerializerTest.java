package ru.mail.polis.homework.io.objects;

import junit.framework.TestCase;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializerTest extends TestCase {
    private static int LIST_SIZE = 250;
    private final Logger logger = Logger.getLogger(SerializerTest.class.getName());

    private static final List<Animal> animals = new ArrayList<>();

    private final Serializer serializer = new Serializer();

    private final String BIN_DEFAULT = "default.bin";
    private final String BIN_CUSTOM = "custom.bin";



    public static void init(){
        Random animalsRandom = new Random(Utils.SEED);
        for (int i = 0; i < LIST_SIZE; i++) {
            Animal animal = new Animal();
            animals.add(animal.getRandomAnimal(animalsRandom));
        }
    }


    public void testDefaultSerialize() throws IOException, ClassNotFoundException {
        init();
        List<Animal> a = animals;
        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, BIN_DEFAULT);
        StringBuilder builder = new StringBuilder();
        builder.append("Size: ");
        builder.append(Files.size(Path.of(BIN_DEFAULT)));
        builder.append("bytes; Reading time: " );
        builder.append((System.currentTimeMillis() - start));
        builder.append(" ms");
        logger.log(Level.INFO,builder.toString());
        long start2 = System.currentTimeMillis();
        assertEquals(animals,serializer.defaultDeserialize(BIN_DEFAULT));
        StringBuilder builder2 = new StringBuilder();
        builder2.append("bytes; Writing time: " );
        builder2.append((System.currentTimeMillis() - start2));
        builder2.append(" ms");
        logger.log(Level.INFO,builder2.toString());
        Files.deleteIfExists(Path.of(BIN_DEFAULT));

    }


    public void testCustomSerialize() throws IOException{
        init();
        Random animalsRandom = new Random(Utils.SEED);
        for (int i = 0; i < LIST_SIZE; i++) {
            Animal animal = new Animal();
            animals.add(animal.getRandomAnimal(animalsRandom));
        }
        long start = System.currentTimeMillis();
        serializer.customSerialize(animals,BIN_CUSTOM);
        StringBuilder builder = new StringBuilder();
        builder.append("Size: ");
        builder.append(Files.size(Path.of(BIN_CUSTOM)));
        builder.append("bytes; Reading time: " );
        builder.append((System.currentTimeMillis() - start));
        builder.append(" ms");
        logger.log(Level.INFO,builder.toString());
        long start2 = System.currentTimeMillis();
        assertEquals(animals,serializer.customDeserialize(BIN_CUSTOM));
        StringBuilder builder2 = new StringBuilder();
        builder2.append("bytes; Writing time: " );
        builder2.append((System.currentTimeMillis() - start2));
        builder2.append(" ms");
        logger.log(Level.INFO,builder2.toString());
        Files.deleteIfExists(Path.of(BIN_CUSTOM));
    }

}