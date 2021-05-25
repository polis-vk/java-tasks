package ru.mail.polis.homework.io.objects;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SerializerTest extends TestCase {
    private final List<Animal> animals;
    private final Serializer serializer;

    public SerializerTest() {
        animals = new ArrayList<>();
        serializer = new Serializer();
        Animal animal1 = new Animal(AnimalType.AMPHIBIAN, true, new AnimalOwner("test1", "test2"), "test3", 4);
        Animal animal2 = new Animal(AnimalType.FISH, false, new AnimalOwner("test4", "test5"), "test6", 15);
        Animal animal3 = new Animal(AnimalType.AMPHIBIAN, true, new AnimalOwner("test7", "test8"), "test9", 10);
        animals.add(animal1);
        animals.add(animal2);
        animals.add(animal3);
    }

    @Test
    public void testDefaultSerializeAndDeserialize() throws IOException, ClassNotFoundException {
        serializer.defaultSerialize(animals, "src/test/resources/animals");
        List<Animal> deserialized = serializer.defaultDeserialize("src/test/resources/animals");
        Files.delete(Paths.get("src", "test", "resources", "animals"));
        assertEquals(animals, deserialized);
    }

    @Test
    public void testCustomSerializeAndDeserialize() throws IOException {
        serializer.customSerialize(animals, "src/test/resources/animals");
        List<Animal> deserialized = serializer.customDeserialize("src/test/resources/animals");
        Files.delete(Paths.get("src", "test", "resources", "animals"));
        assertEquals(animals, deserialized);
    }
}