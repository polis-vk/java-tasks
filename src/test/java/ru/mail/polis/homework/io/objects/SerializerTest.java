package ru.mail.polis.homework.io.objects;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SerializerTest {

    private static final int COUNT = 10;

    private final String filename = "testFile.bin";
    private static final Serializer serializer = new Serializer();
    private static final String englishSymbols = "abcdefghijklmnopqrstuvwxyz";

    private static Animal.AnimalClassification getRandomAnimalClassification() {
        Animal.AnimalClassification[] classifications = Animal.AnimalClassification.values();
        return classifications[new Random().nextInt(classifications.length)];
    }

    private static String getRandomName() {
        return new Random().ints(COUNT, 0, englishSymbols.length())
                .mapToObj(englishSymbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private static int getRandomAge() {
        return new Random().nextInt(100);
    }

    private static int getRandomWeight() {
        return new Random().nextInt(200);
    }

    private static boolean getRandomIsPredator() {
        return new Random().nextBoolean();
    }

    private static List<String> getRandomAreas() {
        List<String> areas = new ArrayList<>();

        for (int i = 0; i < COUNT; i++) {
            areas.add(getRandomName());
        }

        return areas;
    }

    private static Animal getRandomAnimal() {
        return new Animal(
                getRandomAnimalClassification(),
                getRandomName(),
                getRandomAge(),
                getRandomWeight(),
                getRandomIsPredator(),
                getRandomAreas()
        );
    }

    private static AnimalExternalizable getRandomAnimalExternizable() {
        return new AnimalExternalizable(
                getRandomAnimalClassification(),
                getRandomName(),
                getRandomAge(),
                getRandomWeight(),
                getRandomIsPredator(),
                getRandomAreas()
        );
    }

    private static AnimalWithMethods getRandomAnimalWithMethods() {
        return new AnimalWithMethods(
                getRandomAnimalClassification(),
                getRandomName(),
                getRandomAge(),
                getRandomWeight(),
                getRandomIsPredator(),
                getRandomAreas()
        );
    }

    private List<Animal> animals = new ArrayList<Animal>() {{
        for (int i = 0; i < COUNT; i++) {
            add(getRandomAnimal());
        }
    }};


    private List<AnimalExternalizable> animalsExternalizable = new ArrayList<AnimalExternalizable>() {{
        for (int i = 0; i < COUNT; i++) {
            add(getRandomAnimalExternizable());
        }
    }};


    private List<AnimalWithMethods> animalsWithMethod = new ArrayList<AnimalWithMethods>() {{
        for (int i = 0; i < COUNT; i++) {
            add(getRandomAnimalWithMethods());
        }
    }};

    private void printTestInfo(String testName, long size, long time) {
        System.out.println(
                testName + "\n" +
                "File size: " + size +
                "Time: " + time + "\n"
        );
    }

    @Test
    public void defaultSerializeTest() throws IOException {
        Path path = Paths.get(filename);
        long timeBeforeSerialization = System.currentTimeMillis();

        try {
            serializer.defaultSerialize(animals, filename);
            List<Animal> animalsAfterDeserialize = serializer.defaultDeserialize(filename);
            long size = Files.size(path);
            long time = System.currentTimeMillis() - timeBeforeSerialization;

            Assert.assertArrayEquals(animals.toArray(), animalsAfterDeserialize.toArray());
            printTestInfo("Default serialize test", size, time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeExternalizable() throws IOException {
        Path path = Paths.get(filename);
        long timeBeforeSerialization = System.currentTimeMillis();

        try {
            serializer.serializeWithExternalizable(animalsExternalizable, filename);
            List<AnimalExternalizable> animalsAfterDeserialize = serializer.deserializeWithExternalizable(filename);
            long size = Files.size(path);
            long time = System.currentTimeMillis() - timeBeforeSerialization;

            Assert.assertArrayEquals(animalsExternalizable.toArray(), animalsAfterDeserialize.toArray());
            printTestInfo("Serialize with Externalizable test", size, time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeWithMethods() throws IOException {
        Path path = Paths.get(filename);
        long timeBeforeSerialization = System.currentTimeMillis();

        try {
            serializer.serializeWithMethods(animalsWithMethod, filename);
            List<AnimalWithMethods> animalsAfterDeserialize = serializer.deserializeWithMethods(filename);
            long size = Files.size(path);
            long time = System.currentTimeMillis() - timeBeforeSerialization;

            Assert.assertArrayEquals(animalsWithMethod.toArray(), animalsAfterDeserialize.toArray());
            printTestInfo("Serialize with methods test", size, time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void customSerializeTest() throws IOException {
        Path path = Paths.get(filename);
        long timeBeforeSerialization = System.currentTimeMillis();

        try {
            serializer.customSerialize(animals, filename);
            List<Animal> animalsAfterDeserialize = serializer.customDeserialize(filename);
            long size = Files.size(path);
            long time = System.currentTimeMillis() - timeBeforeSerialization;

            Assert.assertArrayEquals(animals.toArray(), animalsAfterDeserialize.toArray());
            printTestInfo("Custom serialize test", size, time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
