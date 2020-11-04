package ru.mail.polis.homework.io.objects;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


import static org.junit.Assert.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SerializerTest {

    private static final int LIST_SIZE = 1000;

    private static final List<Animal> animals = new ArrayList<>();

    private static final List<AnimalExternalizable> animalsExternalizable = new ArrayList<>();

    private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<>();

    private final Serializer serializer = new Serializer();

    private final Logger logger = Logger.getLogger(SerializerTest.class.getName());

    private final String BIN_DEFAULT = "default.bin";
    private final String BIN_METHODS = "methods.bin";
    private final String BIN_EXTERNAL = "externalizable.bin";
    private final String BIN_CUSTOM = "custom.bin";


    @BeforeAll
    public static void init() {
        Random animalsRandom = new Random(Utils.SEED);
        Random animalsExternRandom = new Random(Utils.SEED);
        Random animalsMethodRandom = new Random(Utils.SEED);
        for (int i = 0; i < LIST_SIZE; i++) {
            animals.add(Animal.getRandom(animalsRandom));
            animalsExternalizable.add(AnimalExternalizable.getRandom(animalsExternRandom));
            animalsWithMethods.add(AnimalWithMethods.getRandom(animalsMethodRandom));
        }
    }

    @Test
    @Order(0)
    public void defaultSerialize() throws IOException {
        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, BIN_DEFAULT);
        String info = "Size: " + Files.size(Path.of(BIN_DEFAULT)) + " bytes; Execution time: " + (System.currentTimeMillis() - start) + " ms";
        logger.log(Level.INFO, info);
    }

    @Test
    @Order(1)
    public void defaultDeserialize() throws IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        assertEquals(animals, serializer.defaultDeserialize(BIN_DEFAULT));
        String info = "Execution time: " + (System.currentTimeMillis() - start) + " ms";
        logger.log(Level.INFO, info);
    }

    @Test
    @Order(2)
    public void serializeWithMethods() throws IOException {
        long start = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, BIN_METHODS);
        String info = "Size: " + Files.size(Path.of(BIN_METHODS)) + " bytes; Execution time: " + (System.currentTimeMillis() - start) + " ms";
        logger.log(Level.INFO, info);
    }

    @Test
    @Order(3)
    public void deserializeWithMethods() throws IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        assertEquals(animalsWithMethods, serializer.deserializeWithMethods(BIN_METHODS));
        String info = "Execution time: " + (System.currentTimeMillis() - start) + " ms";
        logger.log(Level.INFO, info);
    }

    @Test
    @Order(4)
    public void serializeWithExternalizable() throws IOException {
        long start = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, BIN_EXTERNAL);
        String info = "Size: " + Files.size(Path.of(BIN_EXTERNAL)) + " bytes; Execution time: " + (System.currentTimeMillis() - start) + " ms";
        logger.log(Level.INFO, info);
    }

    @Test
    @Order(5)
    public void deserializeWithExternalizable() throws IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        assertEquals(animalsExternalizable, serializer.deserializeWithExternalizable(BIN_EXTERNAL));
        String info = "Execution time: " + (System.currentTimeMillis() - start) + " ms";
        logger.log(Level.INFO, info);
    }

    /**
     * Если age > 100, то при десериализации выбрасывается IllegalArgumentException
     * {@link ru.mail.polis.homework.io.objects.AnimalExternalizable#readExternal(ObjectInput)}
     */
    @Test
    @Order(6)
    public void deserializeWithExternalizableException() throws IOException {
        List<AnimalExternalizable> animalsExternalizableException = Collections.singletonList(
                AnimalExternalizable.newBuilder().setName("Oleg").setColour(Colour.RED).setAnimalKind(AnimalKind.ELEPHANT).setAge(101).build()
        );
        serializer.serializeWithExternalizable(animalsExternalizableException, BIN_EXTERNAL);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                serializer.deserializeWithExternalizable(BIN_EXTERNAL)
        );
    }

    @Test
    @Order(7)
    public void customSerialize() throws IOException {
        long start = System.currentTimeMillis();
        serializer.customSerialize(animals, BIN_CUSTOM);
        String info = "Size: " + Files.size(Path.of(BIN_CUSTOM)) + " bytes; Execution time: " + (System.currentTimeMillis() - start) + " ms";
        logger.log(Level.INFO, info);
    }

    @Test
    @Order(8)
    public void customDeserialize() throws IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        assertEquals(animals, serializer.customDeserialize(BIN_CUSTOM));
        String info = "Execution time: " + (System.currentTimeMillis() - start) + " ms";
        logger.log(Level.INFO, info);
    }


}