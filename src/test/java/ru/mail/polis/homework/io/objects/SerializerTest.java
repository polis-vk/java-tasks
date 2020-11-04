package ru.mail.polis.homework.io.objects;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final String fileName = "testFile";
    private static List<Animal> animalList;
    private static List<AnimalWithMethods> animalWithMethodsList;
    private static List<AnimalExternalizable> animalExternalizableList;
    private static Serializer serializer;
    private static final Random random = new Random();

    private static long startTime = 0;
    private static long finishTime = 0;

    @BeforeClass
    public static void setUp() {
        List<String> food = new ArrayList<>();
        food.add("apples");
        food.add("oranges");
        food.add("snakes");
        food.add("mushrooms");
        food.add("rabbits");

        List<String> names = new ArrayList<>();
        names.add("tiger");
        names.add("owl");
        names.add("shark");
        names.add("dog");
        names.add("cat");

        List<String> habitats = new ArrayList<>();
        habitats.add("Russia");
        habitats.add("South America");
        habitats.add("Africa");
        habitats.add("Australia");
        habitats.add("Japan");

        AnimalType[] animalTypes = AnimalType.values();

        int numberOfElements = 10000;

        animalList = new ArrayList<>(numberOfElements);
        animalWithMethodsList = new ArrayList<>(numberOfElements);
        animalExternalizableList = new ArrayList<>(numberOfElements);

        for (int i = 0; i < numberOfElements; i++) {
            String name = names.get(random.nextInt(names.size()));

            boolean isPredator = random.nextBoolean();

            AnimalType animalType = animalTypes[random.nextInt(animalTypes.length)];

            int foodSize = random.nextInt(10);
            ArrayList<String> animalFood = new ArrayList<>(foodSize);

            for (int j = 0; j < foodSize; j++) {
                String f = food.get(random.nextInt(food.size()));
                animalFood.add(f);
            }

            Habitat habitat = new Habitat(habitats.get(random.nextInt(habitats.size())));

            int speed = random.nextInt(100) + 1;

            Animal animal = new Animal(name, isPredator, animalType, animalFood, habitat, speed);
            animalList.add(animal);

            AnimalWithMethods animalWithMethods = new AnimalWithMethods(name, isPredator, animalType, animalFood, habitat, speed);
            animalWithMethodsList.add(animalWithMethods);

            AnimalExternalizable animalExternalizable = new AnimalExternalizable(name, isPredator, animalType, animalFood, habitat, speed);
            animalExternalizableList.add(animalExternalizable);
        }

        serializer = new Serializer();
        System.out.println("Number of serializing elements: " + numberOfElements + "\n");
    }

    @AfterClass
    public static void findDefaultSerializeTimeAndFileSize() throws IOException, ClassNotFoundException {
        System.out.println("Default serialize:");
        startTime = System.currentTimeMillis();
        serializer.defaultSerialize(animalList, fileName);
        finishTime = System.currentTimeMillis();
        System.out.println("time of serializing: " + (finishTime - startTime) + " ms");
        startTime = System.currentTimeMillis();
        serializer.defaultDeserialize(fileName);
        finishTime = System.currentTimeMillis();
        System.out.println("time of deserializing: " + (finishTime - startTime) + " ms");
        System.out.println("size of file: " + Files.size(Paths.get(fileName)) + " bytes");
        deleteFile(Paths.get(fileName));
        System.out.println();
    }

    @AfterClass
    public static void findSerializeWithMethodsTimeAndFileSize() throws IOException, ClassNotFoundException {
        System.out.println("Serialize with methods:");
        startTime = System.currentTimeMillis();
        serializer.serializeWithMethods(animalWithMethodsList, fileName);
        finishTime = System.currentTimeMillis();
        System.out.println("time of serializing: " + (finishTime - startTime) + " ms");
        startTime = System.currentTimeMillis();
        serializer.deserializeWithMethods(fileName);
        finishTime = System.currentTimeMillis();
        System.out.println("time of deserializing: " + (finishTime - startTime) + " ms");
        System.out.println("size of file: " + Files.size(Paths.get(fileName)) + " bytes");
        deleteFile(Paths.get(fileName));
        System.out.println();
    }

    @AfterClass
    public static void findSerializeWithExternalizableTimeAndFileSize() throws IOException, ClassNotFoundException {
        System.out.println("External serialize:");
        startTime = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalExternalizableList, fileName);
        finishTime = System.currentTimeMillis();
        System.out.println("time of serializing: " + (finishTime - startTime) + " ms");
        startTime = System.currentTimeMillis();
        serializer.deserializeWithExternalizable(fileName);
        finishTime = System.currentTimeMillis();
        System.out.println("time of deserializing: " + (finishTime - startTime) + " ms");
        System.out.println("size of file: " + Files.size(Paths.get(fileName)) + " bytes");
        deleteFile(Paths.get(fileName));
        System.out.println();
    }

    @AfterClass
    public static void findCustomSerializeTimeAndFileSize() throws IOException {
        System.out.println("Custom serialize:");
        startTime = System.currentTimeMillis();
        serializer.customSerialize(animalList, fileName);
        finishTime = System.currentTimeMillis();
        System.out.println("time of serializing: " + (finishTime - startTime) + " ms");
        startTime = System.currentTimeMillis();
        serializer.customDeserialize(fileName);
        finishTime = System.currentTimeMillis();
        System.out.println("time of deserializing: " + (finishTime - startTime) + " ms");
        System.out.println("size of file: " + Files.size(Paths.get(fileName)) + " bytes");
        deleteFile(Paths.get(fileName));
        System.out.println();
    }


    @Test
    public void checkDefaultSerialize() throws IOException, ClassNotFoundException {
        serializer.defaultSerialize(animalList, fileName);
        List<Animal> animalsAfter = serializer.defaultDeserialize(fileName);
        assertEquals(animalList, animalsAfter);
        deleteFile(Paths.get(fileName));
    }

    @Test
    public void checkSerializeWithMethods() throws IOException, ClassNotFoundException {
        serializer.serializeWithMethods(animalWithMethodsList, fileName);
        List<AnimalWithMethods> animalsAfter = serializer.deserializeWithMethods(fileName);
        assertEquals(animalWithMethodsList, animalsAfter);
        deleteFile(Paths.get(fileName));
    }

    @Test
    public void checkSerializeWithExternalizable() throws IOException, ClassNotFoundException {
        serializer.serializeWithExternalizable(animalExternalizableList, fileName);
        List<AnimalExternalizable> animalsAfter = serializer.deserializeWithExternalizable(fileName);
        assertEquals(animalExternalizableList, animalsAfter);
        deleteFile(Paths.get(fileName));
    }

    @Test
    public void checkCustomSerialize() throws IOException {
        serializer.customSerialize(animalList, fileName);
        List<Animal> animalsAfter = serializer.customDeserialize(fileName);
        assertEquals(animalList, animalsAfter);
        deleteFile(Paths.get(fileName));
    }

    private static void deleteFile(Path filePath) throws IOException {
        Files.delete(filePath);
    }
}
