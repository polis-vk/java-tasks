package ru.mail.polis.homework.io;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private final static int COUNT = 10;
    private final static int LENGTH_NAME = 5;
    private final static String fileName = "testFile.bin";
    private static final Serializer serializer = new Serializer();
    private static final String englishSymbols = "abcdefghijklmnopqrstuvwxyz";

    private static String getRandomName() {
        return new Random().ints(LENGTH_NAME, 0, englishSymbols.length())
                .mapToObj(englishSymbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

    }

    private static List<String> getRandomListName() {
        List<String> tmpList = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            tmpList.add(getRandomName());
        }
        return tmpList;
    }

    private static AnimalsType getRandomAnimalType() {
        return AnimalsType.fromValue(Math.abs(new Random().nextInt()) % AnimalsType.Length.getValue());
    }

    private static Animal getRandomAnimal() {
        Random rand = new Random();
        return new Animal(
                getRandomAnimalType(),
                getRandomName(),
                getRandomListName(),
                rand.nextInt(),
                rand.nextInt(),
                rand.nextBoolean(),
                new Mind(rand.nextInt())
        );
    }

    private static AnimalWithMethods getRandomAnimalWithMethods() {
        Random rand = new Random();
        return new AnimalWithMethods(
                getRandomAnimalType(),
                getRandomName(),
                getRandomListName(),
                rand.nextInt(),
                rand.nextInt(),
                rand.nextBoolean(),
                new Mind(rand.nextInt())
        );
    }

    private static AnimalExternalizable getRandomAnimalExternalizables() {
        Random rand = new Random();
        return new AnimalExternalizable(
                getRandomAnimalType(),
                getRandomName(),
                getRandomListName(),
                rand.nextInt(),
                rand.nextInt(),
                rand.nextBoolean(),
                new Mind(rand.nextInt())
        );
    }

    private static final List<Animal> justAnimals = new ArrayList<Animal>() {{
        for (int i = 0; i < COUNT; i++) {
            add(getRandomAnimal());
        }
    }};

    private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<AnimalWithMethods>() {{
        for (int i = 0; i < COUNT; i++) {
            add(getRandomAnimalWithMethods());
        }
    }};

    private static final List<AnimalExternalizable> animalsExtern = new ArrayList<AnimalExternalizable>() {{
        for (int i = 0; i < COUNT; i++) {
            add(getRandomAnimalExternalizables());
        }
    }};

    @Test
    public void defaultSerializeTest() throws IOException {
        Path path = Paths.get(fileName);
        long before = System.currentTimeMillis();

        try {
            serializer.defaultSerialize(justAnimals, fileName);
            List<Animal> animals = serializer.defaultDeserialize(fileName);

            long time = System.currentTimeMillis() - before;
            long size = Files.size(path);

            Assert.assertArrayEquals(justAnimals.toArray(), animals.toArray());

            printTestInfo("Default Serialize Test", size, time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeWithMethodsTest() {
        Path path = Paths.get(fileName);
        long before = System.currentTimeMillis();

        try {
            serializer.serializeWithMethods(animalsWithMethods, fileName);

            List<AnimalWithMethods> animals = serializer.deserializeWithMethods(fileName);

            long time = System.currentTimeMillis() - before;
            long size = Files.size(path);

            Assert.assertArrayEquals(animalsWithMethods.toArray(), animals.toArray());

            printTestInfo("Serialize With Methods Test", size, time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeWithExternalizableTest() {
        Path path = Paths.get(fileName);
        long before = System.currentTimeMillis();

        try {
            serializer.serializeWithExternalizable(animalsExtern, fileName);

            List<AnimalExternalizable> animals = serializer.deserializeWithExternalizable(fileName);

            long time = System.currentTimeMillis() - before;
            long size = Files.size(path);

            Assert.assertArrayEquals(animalsExtern.toArray(), animals.toArray());

            printTestInfo("Serialize With Externalizable Test", size, time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void customSerializeTest() throws IOException {
        List<Animal> justAnimals1 = justAnimals;
        serializer.customSerialize(justAnimals, fileName);
        List<Animal> deserializeObjects = serializer.customDeserialize(fileName);
        assertEquals(justAnimals, deserializeObjects);
        Files.delete(Paths.get(fileName));
        /*Path path = Paths.get(fileName);
        long before = System.currentTimeMillis();

        try {
            serializer.customSerialize(justAnimals, fileName);

            List<Animal> animals = serializer.customDeserialize(fileName);


            long time = System.currentTimeMillis() - before;
            long size = Files.size(path);

            Assert.assertArrayEquals(justAnimals.toArray(), animals.toArray());
            printTestInfo("Custom Serialize Test", size, time);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @After
    public void deleteFile() {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printTestInfo(String testName, long size, long time) {
        System.out.println(testName + "\n"
                + "Animal amount: " + COUNT + "\n"
                + "File size in bytes:  " + size + "\n"
                + "Time in millis: " + time + "\n");
    }
}
