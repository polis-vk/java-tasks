package ru.mail.polis.homework.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.Serializer;
import ru.mail.polis.homework.io.objects.Type;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private Serializer serializer = new Serializer();
    private final List<Animal> animals = new ArrayList<>();
    private final Path dir = Paths.get("src", "test", "resources", "serialization");

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(dir.toFile());
    }

    @Before
    public void createAnimals() {
        Animal raichu = new Animal(150, "Raichu", Arrays.asList(Type.ELECTRIC));
        Animal pikachu = new Animal(100, "Pikachu", Arrays.asList(Type.ELECTRIC), Arrays.asList(raichu));

        Animal dragonite = new Animal(160, "Dragonite", Arrays.asList(Type.DRAGON, Type.FLYING));
        Animal dragonair = new Animal(115, "Dragonair", Arrays.asList(Type.DRAGON), Arrays.asList(dragonite));
        Animal dratini = new Animal(40, "Dratini", Arrays.asList(Type.DRAGON), Arrays.asList(dragonair, dragonite));

        Animal xerneas = new Animal(160, "Xerneas", Arrays.asList(Type.FAIRY));

        Animal persian = new Animal(90, "Persian", Arrays.asList(Type.NORMAL));
        Animal meowth = new Animal(69, "Meowth", Arrays.asList(Type.NORMAL), Arrays.asList(persian));

        Animal golduck = new Animal(148, "Golduck", Arrays.asList(Type.WATER));
        Animal psyduck = new Animal(105, "Psyduck", Arrays.asList(Type.WATER), Arrays.asList(golduck));


        List<Animal> tenAnimals = Arrays.asList(
            raichu,
            pikachu,
            dragonite,
            dragonair,
            dratini,
            xerneas,
            persian,
            meowth,
            golduck,
            psyduck
        );

        animals.addAll(
                Collections.nCopies(100, tenAnimals).stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        );
    }

    @Before
    public void setUp() throws IOException {
        Files.createDirectory(dir);
    }

    @Test
    public void defaultSerializeTest() {
        Path file = Paths.get(dir.toString(), "defaultSerialization.txt");

        long writeStart = System.currentTimeMillis();
        serializer.defaultSerialize(animals, file.toString());
        long writeEnd = System.currentTimeMillis();

        List<Animal> deserialized;
        long readStart = System.currentTimeMillis();
        deserialized = serializer.defaultDeserialize(file.toString());
        long readEnd = System.currentTimeMillis();

        String log = "Default serialize\n" +
                "Serialize time: " + (writeEnd - writeStart) + "\n" +
                "Deserialize time: " + (readEnd - readStart) + "\n" +
                "File size: " + getFileSize(file) + " bytes";
        System.out.println(log);

        assertEquals(animals, deserialized);
    }

    @Test
    public void customSerializeTest() {
        Path file = Paths.get(dir.toString(), "customSerialization.txt");

        long writeStart = System.currentTimeMillis();
        serializer.customSerialize(animals, file.toString());
        long writeEnd = System.currentTimeMillis();

        List<Animal> deserialized;
        long readStart = System.currentTimeMillis();
        deserialized = serializer.customDeserialize(file.toString());
        long readEnd = System.currentTimeMillis();

        String log = "Custom serialize\n" +
                "Serialize time: " + (writeEnd - writeStart) + "\n" +
                "Deserialize time: " + (readEnd - readStart) + "\n" +
                "File size: " + getFileSize(file) + " bytes";
        System.out.println(log);

        assertEquals(animals, deserialized);
    }

    private long getFileSize(Path fileName) {
        try {
            return Files.size(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
