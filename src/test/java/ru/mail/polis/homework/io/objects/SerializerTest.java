package ru.mail.polis.homework.io.objects;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class SerializerTest {

    private static final List<Animal> simpleAnimals = new ArrayList<>();
    private static final List<AnimalWithMethods> animalWithMethods = new ArrayList<>();
    private static final List<AnimalExternalizable> externalizableAnimals = new ArrayList<>();

    private static final Serializer serializer = new Serializer();

    private static final String fileName = "D:\\test.ser";

    @BeforeClass
    public static void prepare() {
        List<String> allKinds = Arrays.asList("cat", "dog", "bear", "wolf", "fox",
                "hippo", "koala", "horse", "ferret", "giraffe");

        List<String> allHabitats = Arrays.asList("Europe", "Asia", "South America", "North America",
                "Africa", "Australia", "Ocean");

        Random rnd = new Random();

        for (int i = 0; i < 10000; i++) {
            String kind = allKinds.get(rnd.nextInt(allKinds.size()));
            boolean isTailLong = rnd.nextBoolean();
            double energy = 100;
            Animal.FoodPreferences foodPreferences =
                    Animal.FoodPreferences.values()[rnd.nextInt(Animal.FoodPreferences.values().length)];
            int averageLifeExpectancy = rnd.nextInt(50);
            List<String> habitats = Arrays.asList(
                    allHabitats.get(rnd.nextInt(allHabitats.size())),
                    allHabitats.get(rnd.nextInt(allHabitats.size()))
            );

            Tail tail = new Tail(isTailLong);

            simpleAnimals.add(new Animal(kind, tail, energy, foodPreferences, averageLifeExpectancy, habitats));
            animalWithMethods.add(new AnimalWithMethods(kind, tail, energy, foodPreferences, averageLifeExpectancy, habitats));
            externalizableAnimals.add(new AnimalExternalizable(kind, tail, energy, foodPreferences, averageLifeExpectancy, habitats));
        }
    }

    @Test
    public void defaultSerialize() {
        try {
            long startTime = System.currentTimeMillis();
            serializer.defaultSerialize(simpleAnimals, fileName);
            long endTime = System.currentTimeMillis();

            System.out.println("DEFAULT");
            System.out.println("Serialization: " + (endTime - startTime) + " milliseconds");

            startTime = System.currentTimeMillis();
            List<Animal> deserializedAnimals = serializer.defaultDeserialize(fileName);
            endTime = System.currentTimeMillis();

            System.out.println("Deserialization: " + (endTime - startTime) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length() + " bytes");
            file.delete();

            assertArrayEquals(simpleAnimals.toArray(), deserializedAnimals.toArray());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeWithMethods() {
        try {
            long startTime = System.currentTimeMillis();
            serializer.serializeWithMethods(animalWithMethods, fileName);
            long endTime = System.currentTimeMillis();

            System.out.println("WITH METHODS");
            System.out.println("Serialization: " + (endTime - startTime) + " milliseconds");

            startTime = System.currentTimeMillis();
            List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods(fileName);
            endTime = System.currentTimeMillis();

            System.out.println("Deserialization: " + (endTime - startTime) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length() + " bytes");
            file.delete();

            assertArrayEquals(animalWithMethods.toArray(), deserializedAnimals.toArray());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeWithExternalizable() {
        try {
            long startTime = System.currentTimeMillis();
            serializer.serializeWithExternalizable(externalizableAnimals, fileName);
            long endTime = System.currentTimeMillis();

            System.out.println("EXTERNALIZABLE");
            System.out.println("Serialization: " + (endTime - startTime) + " milliseconds");

            startTime = System.currentTimeMillis();
            List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable(fileName);
            endTime = System.currentTimeMillis();

            System.out.println("Deserialization: " + (endTime - startTime) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length() + " bytes");
            file.delete();

            assertArrayEquals(externalizableAnimals.toArray(), deserializedAnimals.toArray());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void customSerialize() {
        try {
            long startTime = System.currentTimeMillis();
            serializer.customSerialize(simpleAnimals, fileName);
            long endTime = System.currentTimeMillis();

            System.out.println("CUSTOM");
            System.out.println("Serialization: " + (endTime - startTime) + " milliseconds");

            startTime = System.currentTimeMillis();
            List<Animal> deserializedAnimals = serializer.customDeserialize(fileName);
            endTime = System.currentTimeMillis();

            System.out.println("Deserialization: " + (endTime - startTime) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length() + " bytes");
            file.delete();

            assertArrayEquals(simpleAnimals.toArray(), deserializedAnimals.toArray());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
