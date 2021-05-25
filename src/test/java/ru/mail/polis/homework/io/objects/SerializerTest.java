package ru.mail.polis.homework.io.objects;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SerializerTest extends TestCase {
    private final List<Animal> animals;
    private final Serializer serializer;
    private final Random random;

    public SerializerTest() {
        animals = new ArrayList<>();
        serializer = new Serializer();
        random = new Random();
        for (int i = 0; i < 5000; i++) {
            AnimalOwner animalOwner = new AnimalOwner(randomString(), randomString());
            animals.add(new Animal(randomAnimalType(), random.nextBoolean(), animalOwner, randomString(), random.nextInt()));
        }
    }

    private String randomString() {
        byte[] array = new byte[10];
        random.nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    private AnimalType randomAnimalType() {
        return AnimalType.values()[random.nextInt(AnimalType.values().length)];
    }

    @Test
    public void testDefaultSerializeAndDeserialize() throws IOException, ClassNotFoundException {
        long startTimeSerialize = System.currentTimeMillis();
        serializer.defaultSerialize(animals, "src/test/resources/animals");
        long endTimeSerialize = System.currentTimeMillis();
        long startTimeDeserialize = System.currentTimeMillis();
        List<Animal> deserialized = serializer.defaultDeserialize("src/test/resources/animals");
        long endTimeDeserialize = System.currentTimeMillis();
        Files.delete(Paths.get("src", "test", "resources", "animals"));
        assertEquals(animals, deserialized);
        System.out.println("default serialize: " + (endTimeSerialize - startTimeSerialize));
        System.out.println("default deserialize: " + (endTimeDeserialize - startTimeDeserialize));
    }

    @Test
    public void testCustomSerializeAndDeserialize() throws IOException {
        long startTimeSerialize = System.currentTimeMillis();
        serializer.customSerialize(animals, "src/test/resources/animals");
        long endTimeSerialize = System.currentTimeMillis();
        long startTimeDeserialize = System.currentTimeMillis();
        List<Animal> deserialized = serializer.customDeserialize("src/test/resources/animals");
        long endTimeDeserialize = System.currentTimeMillis();
        Files.delete(Paths.get("src", "test", "resources", "animals"));
        assertEquals(animals, deserialized);
        System.out.println("custom serialize: " + (endTimeSerialize - startTimeSerialize));
        System.out.println("custom deserialize: " + (endTimeDeserialize - startTimeDeserialize));
    }
}