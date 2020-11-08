package ru.mail.polis.homework.io;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;


public class SerializerTest {
    private final static int COUNT = 100;
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
        List<AnimalsType> arrayList = new ArrayList<>(EnumSet.allOf(AnimalsType.class));
        return arrayList.get(Math.abs(new Random().nextInt()) % arrayList.size());
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
            long timeWrite = System.currentTimeMillis() - before;

            List<Animal> animals = serializer.defaultDeserialize(fileName);
            long timeRead = System.currentTimeMillis() - timeWrite - before;

            long size = Files.size(path);

            Assert.assertThat(justAnimals, is(animals));
            printTestInfo("Default Serialize Test", size, timeWrite, timeRead);
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
            long timeWrite = System.currentTimeMillis() - before;

            List<AnimalWithMethods> animals = serializer.deserializeWithMethods(fileName);
            long timeRead = System.currentTimeMillis() - timeWrite - before;

            long size = Files.size(path);

            Assert.assertThat(animalsWithMethods, is(animals));
            printTestInfo("Serialize With Methods Test", size, timeWrite, timeRead);
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
            long timeWrite = System.currentTimeMillis() - before;

            List<AnimalExternalizable> animals = serializer.deserializeWithExternalizable(fileName);
            long timeRead = System.currentTimeMillis() - timeWrite - before;

            long size = Files.size(path);

            Assert.assertThat(animalsExtern, is(animals));
            printTestInfo("Serialize With Externalizable Test", size, timeWrite, timeRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void customSerializeTest() throws IOException {
        Path path = Paths.get(fileName);
        long before = System.currentTimeMillis();

        try {
            serializer.customSerialize(justAnimals, fileName);
            long timeWrite = System.currentTimeMillis() - before;

            List<Animal> animals = serializer.customDeserialize(fileName);
            long timeRead = System.currentTimeMillis() - timeWrite - before;

            long size = Files.size(path);

            Assert.assertThat(justAnimals, is(animals));
            printTestInfo("Custom Serialize Test", size, timeWrite, timeRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteFile() {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printTestInfo(String testName, long size, long timeWrite, long timeRead) {
        System.out.println(testName + "\n"
                + "Animal amount: " + COUNT + "\n"
                + "File size in bytes:  " + size + "\n"
                + "TimeWrite in millis: " + timeWrite + "\n"
                + "TimeRead in millis: " + timeRead + "\n");
    }
}
