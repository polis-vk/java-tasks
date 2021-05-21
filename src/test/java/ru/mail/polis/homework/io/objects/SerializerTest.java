package ru.mail.polis.homework.io.objects;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializerTest extends TestCase {
    private static int LIST_SIZE = 250000000;
    private final Logger logger = Logger.getLogger(SerializerTest.class.getName());

    private static final List<Animal> animals = new ArrayList<>();

    private final Serializer serializer = new Serializer();

    private final String BIN_DEFAULT = "default.bin";
    private final String BIN_CUSTOM = "custom.bin";


    @BeforeAll
    public static void init(){
        Random animalsRandom = new Random(Utils.SEED);
        for (int i = 0; i < LIST_SIZE; i++) {
            Animal animal = new Animal();
            animals.add(animal.getRandomAnimal(animalsRandom));
        }
    }

    @Order(0)
    public void testDefaultSerialize() throws IOException {
        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, BIN_DEFAULT);
        StringBuilder builder = new StringBuilder();
        builder.append("Size: ");
        builder.append(Files.size(Path.of(BIN_DEFAULT)));
        builder.append("bytes; Execution time: " );
        builder.append((System.currentTimeMillis() - start));
        builder.append(" ms");
        logger.log(Level.INFO,builder.toString());
    }

    @Order(1)
    public void testDefaultDeserialize() throws IOException,ClassNotFoundException {
        long start = System.currentTimeMillis();
        assertEquals(animals,serializer.defaultDeserialize(BIN_DEFAULT));
        StringBuilder builder = new StringBuilder();
        builder.append("bytes; Execution time: " );
        builder.append((System.currentTimeMillis() - start));
        builder.append(" ms");
        logger.log(Level.INFO,builder.toString());
        Files.deleteIfExists(Path.of(BIN_DEFAULT));
    }
    @Order(2)
    public void testCustomSerialize() throws IOException{
        long start = System.currentTimeMillis();
        serializer.customSerialize(animals,BIN_CUSTOM);
        StringBuilder builder = new StringBuilder();
        builder.append("Size: ");
        builder.append(Files.size(Path.of(BIN_CUSTOM)));
        builder.append("bytes; Execution time: " );
        builder.append((System.currentTimeMillis() - start));
        builder.append(" ms");
        logger.log(Level.INFO,builder.toString());
    }
    @Order(3)
    public void testCustomDeserialize() throws IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        assertEquals(animals,serializer.customDeserialize(BIN_CUSTOM));
        StringBuilder builder = new StringBuilder();
        builder.append("bytes; Execution time: " );
        builder.append((System.currentTimeMillis() - start));
        builder.append(" ms");
        logger.log(Level.INFO,builder.toString());
        Files.deleteIfExists(Path.of(BIN_CUSTOM));
    }
}