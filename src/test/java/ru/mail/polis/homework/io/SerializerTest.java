package ru.mail.polis.homework.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SerializerTest {

    private static final int NUMBER_OF_OBJECTS = 100000;
    private static final Path PATH = Paths.get("src", "test", "resources", "objects");
    private final static String fileName = "src/test/resources/objects/serializeTest.bin";
    private static final Serializer serializer = new Serializer();
    Random random = new Random();

    @Before
    public void setUp() throws IOException {
        Files.createDirectories(PATH);
        Files.createFile(Paths.get(fileName));
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(fileName));
        Files.delete(PATH);
    }

    @Test
    public void defaultSerialize() throws IOException {
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OBJECTS; i++) {
            animals.add(generateAnimal());
        }

        long startOut = System.currentTimeMillis();
        serializer.defaultSerialize(animals, fileName);
        long endOut = System.currentTimeMillis();

        long startIn = System.currentTimeMillis();
        serializer.defaultDeserialize(fileName);
        long endIn = System.currentTimeMillis();


        System.out.println("Default Serialization: \n\tSerialization time: \t" + (endOut - startOut) + " ms\n" +
                "\tDeserialization time: \t" + (endIn - startIn) + " ms\n" +
                "\tFile size: \t" + Files.size(Paths.get(fileName)) + " b\n");
    }

    @Test
    public void serializeWithMethods() throws IOException {
        List<AnimalWithMethods> animals = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OBJECTS; i++) {
            animals.add(generateAnimalWithMethods());
        }

        long startOut = System.currentTimeMillis();
        serializer.serializeWithMethods(animals, fileName);
        long finishOut = System.currentTimeMillis();

        long startIn = System.currentTimeMillis();
        serializer.deserializeWithMethods(fileName);
        long finishIn = System.currentTimeMillis();

        System.out.println("Serialization with methods: \n\tSerialization time: \t" + (finishOut - startOut) + " ms\n" +
                "\tDeserialization time: \t" + (finishIn - startIn) + " ms\n" +
                "\tFile size: \t" + Files.size(Paths.get(fileName)) + " b\n");
    }

    @Test
    public void serializeWithExternalizable() throws IOException {
        List<AnimalExternalizable> animals = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OBJECTS; i++) {
            animals.add(generateAnimalExternalizable());
        }

        long startOut = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animals, fileName);
        long finishOut = System.currentTimeMillis();

        long startIn = System.currentTimeMillis();
        serializer.deserializeWithExternalizable(fileName);
        long finishIn = System.currentTimeMillis();

        System.out.println("Serialization with externalizable: \n\tSerialization time: \t" + (finishOut - startOut) + " ms\n" +
                "\tDeserialization time: \t" + (finishIn - startIn) + " ms\n" +
                "\tFile size: \t" + Files.size(Paths.get(fileName)) + " b\n");
    }

    @Test
    public void customSerialize() throws IOException {
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OBJECTS; i++) {
            animals.add(generateAnimal());
        }

        long startOut = System.currentTimeMillis();
        serializer.customSerialize(animals, fileName);
        long finishOut = System.currentTimeMillis();

        long startIn = System.currentTimeMillis();
        serializer.customDeserialize(fileName);
        long finishIn = System.currentTimeMillis();

        System.out.println("Custom serialization: \n\tSerialization time: \t" + (finishOut - startOut) + " ms\n" +
                "\tDeserialization time: \t" + (finishIn - startIn) + " ms\n" +
                "\tFile size: \t" + Files.size(Paths.get(fileName)) + " b\n");
    }


    private Animal generateAnimal() {
        Animal animal = new Animal(generateString(),
                random.nextInt(30),
                random.nextDouble() * random.nextInt(100),
                random.nextBoolean(),
                AnimalType.values()[random.nextInt(AnimalType.values().length)],
                random.nextBoolean(), new PlaceOfResidence(generateString(), generateString()));
        if (animal.getAge() % 2 == 0 && random.nextInt(100) % 2 == 0) {
            animal.setName(null);
        }
        if (animal.getWeight() - random.nextDouble() * random.nextInt(100) < 0) {
            animal.setType(null);
        }
        if (random.nextInt(100) - 50 > 40) {
            animal.setPlaceOfResidence(null);
        }
        if (animal.getAge() % 2 == 0 && random.nextInt(100) % 2 == 0) {
            animal.setPlaceOfResidence(null);
        }
        if (animal.getWeight() - random.nextDouble() * random.nextInt(100) < 0) {
            animal.setName(null);
        }
        if (random.nextInt(100) - 50 > 40) {
            animal.setType(null);
        }
        if (random.nextInt(200) < 100 && random.nextInt(100) % 10 == 0) {
            animal = null;
        }
        return animal;
    }

    private AnimalWithMethods generateAnimalWithMethods() {
        AnimalWithMethods animal = new AnimalWithMethods(generateString(),
                random.nextInt(30),
                random.nextDouble() * random.nextInt(100),
                random.nextBoolean(),
                AnimalType.values()[random.nextInt(AnimalType.values().length)],
                random.nextBoolean(), new PlaceOfResidence(generateString(), generateString()));
        if (animal.getAge() % 2 == 0 && random.nextInt(100) % 2 == 0) {
            animal.setName(null);
        }
        if (animal.getWeight() - random.nextDouble() * random.nextInt(100) < 0) {
            animal.setType(null);
        }
        if (random.nextInt(100) - 50 > 40) {
            animal.setPlaceOfResidence(null);
        }
        if (animal.getAge() % 2 == 0 && random.nextInt(100) % 2 == 0) {
            animal.setPlaceOfResidence(null);
        }
        if (animal.getWeight() - random.nextDouble() * random.nextInt(100) < 0) {
            animal.setName(null);
        }
        if (random.nextInt(100) - 50 > 40) {
            animal.setType(null);
        }
        if (random.nextInt(200) < 100 && random.nextInt(100) % 10 == 0) {
            animal = null;
        }
        return animal;
    }

    private AnimalExternalizable generateAnimalExternalizable() {
        AnimalExternalizable animal = new AnimalExternalizable(generateString(),
                random.nextInt(30),
                random.nextDouble() * random.nextInt(100),
                random.nextBoolean(),
                AnimalType.values()[random.nextInt(AnimalType.values().length)],
                random.nextBoolean(), new PlaceOfResidence(generateString(), generateString()));
        if (animal.getAge() % 2 == 0 && random.nextInt(100) % 2 == 0) {
            animal.setName(null);
        }
        if (animal.getWeight() - random.nextDouble() * random.nextInt(100) < 0) {
            animal.setType(null);
        }
        if (random.nextInt(100) - 50 > 40) {
            animal.setPlaceOfResidence(null);
        }
        if (animal.getAge() % 2 == 0 && random.nextInt(100) % 2 == 0) {
            animal.setPlaceOfResidence(null);
        }
        if (animal.getWeight() - random.nextDouble() * random.nextInt(100) < 0) {
            animal.setName(null);
        }
        if (random.nextInt(100) - 50 > 40) {
            animal.setType(null);
        }
        if (random.nextInt(200) < 100 && random.nextInt(100) % 10 == 0) {
            animal = null;
        }
        return animal;
    }

    private String generateString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int stringLength = 10;
        return random.ints(leftLimit, rightLimit + 1)
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
