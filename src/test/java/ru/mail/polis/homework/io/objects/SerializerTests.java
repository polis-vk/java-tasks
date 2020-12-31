package ru.mail.polis.homework.io.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SerializerTests {
    private static final List<Animal> animals = new ArrayList<>();
    private static final List<AnimalExternalizable> animalsExternalizable = new ArrayList<>();
    private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<>();
    private static final Serializer serializer = new Serializer();

    private static final String fileName = "test.txt";

    @Before
    public void prepare() {
        List<String> names = Arrays.asList("cat", "dog", "shark", "wolf", "fox", "horse", "bear", "rabbit");
        List<Boolean> isPredator = Arrays.asList(true, true, true, true, true, false, true, false);
        Animal.Food[] allFood = Animal.Food.values();

        Random rnd = new Random();

        for (int i = 0; i < 1000; i++) {
            int ind = rnd.nextInt(names.size());
            String name = names.get(ind);
            int age = rnd.nextInt(100);
            Animal.Food food = allFood[rnd.nextInt(3)];
            int sizeFriend = rnd.nextInt(100);
            ArrayList<String> friends = new ArrayList<>();
            for (int g = 0; g < sizeFriend; g++) {
                friends.add(names.get(rnd.nextInt(names.size())));
            }

            animals.add(new Animal(name, age, food, sizeFriend, friends, new Animal.Size(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble()),
                    isPredator.get(ind)));
            animalsExternalizable.add(new AnimalExternalizable(name, age, food, sizeFriend, friends, new AnimalExternalizable.Size(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble()),
                    isPredator.get(ind)));
            animalsWithMethods.add(new AnimalWithMethods(name, age, food, sizeFriend, friends, new AnimalWithMethods.Size(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble()),
                    isPredator.get(ind)));
        }
    }


    @Test
    public void defaultSerialize() {
        try {
            long start = System.currentTimeMillis();
            serializer.defaultSerialize(animals, fileName);
            long finish = System.currentTimeMillis();

            System.out.println("defaultSerialization: " + (finish - start) + " milliseconds");

            start = System.currentTimeMillis();
            List<Animal> deserializedAnimals = serializer.defaultDeserialize(fileName);
            finish = System.currentTimeMillis();

            System.out.println("defaultDeserialization: " + (finish - start) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length());
            file.delete();

            for (int i = 0; i < animals.size(); i++) {
                assertEquals(animals.get(i), deserializedAnimals.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeWithMethods() {
        try {
            long start = System.currentTimeMillis();
            serializer.serializeWithMethods(animalsWithMethods, fileName);
            long finish = System.currentTimeMillis();

            System.out.println("SerializationWithMethods: " + (finish - start) + " milliseconds");

            start = System.currentTimeMillis();
            List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods(fileName);
            finish = System.currentTimeMillis();

            System.out.println("DeserializationWithMethods: " + (finish - start) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length());
            file.delete();

            for (int i = 0; i < animals.size(); i++) {
                assertEquals(animals.get(i), deserializedAnimals.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeExternalizable() {
        try {
            long start = System.currentTimeMillis();
            serializer.serializeWithExternalizable(animalsExternalizable, fileName);
            long finish = System.currentTimeMillis();

            System.out.println("ExternalizableSerialization: " + (finish - start) + " milliseconds");

            start = System.currentTimeMillis();
            List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable(fileName);
            finish = System.currentTimeMillis();

            System.out.println("ExternalizableDeserialization: " + (finish - start) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length());
            file.delete();

            for (int i = 0; i < animalsExternalizable.size(); i++) {
                assertEquals(animalsExternalizable.get(i), deserializedAnimals.get(i));
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void customSerialize() {
            long start = System.currentTimeMillis();
            serializer.customSerialize(animals, fileName);
            long finish = System.currentTimeMillis();

            System.out.println("CustomSerialization: " + (finish - start) + " milliseconds");

            start = System.currentTimeMillis();
            List<Animal> deserializedAnimals = serializer.customDeserialize(fileName);
            finish = System.currentTimeMillis();

            System.out.println("CustomDeserialization: " + (finish - start) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length());
            file.delete();

            for (int i = 0; i < animals.size(); i++) {
                assertEquals(animals.get(i), deserializedAnimals.get(i));
            }
    }
}