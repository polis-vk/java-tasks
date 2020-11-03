package ru.mail.polis.homework.io.objects;

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
    private String fileName;
    private final List<Animal> animalList;
    private final List<AnimalWithMethods> animalWithMethodsList;
    private final List<AnimalExternalizable> animalExternalizableList;
    private final Serializer serializer;

    public SerializerTest() {

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
        Random random = new Random();
        animalList = new ArrayList<>();
        animalWithMethodsList = new ArrayList<>();
        animalExternalizableList = new ArrayList<>();

        int numberOfElements = 100000;

        for (int i = 0; i < numberOfElements; i++) {
            String name = names.get(random.nextInt(names.size()));

            boolean isPredator = random.nextBoolean();

            AnimalType animalType = animalTypes[random.nextInt(animalTypes.length)];
            String food1 = food.get(random.nextInt(food.size()));
            String food2 = food.get(random.nextInt(food.size()));

            ArrayList<String> animalFood = new ArrayList<>();
            animalFood.add(food1);
            animalFood.add(food2);

            Habitat habitat = new Habitat(habitats.get(random.nextInt(habitats.size())));

            int speed = random.nextInt(100);

            Animal animal = new Animal(name, isPredator, animalType, animalFood, habitat, speed);
            animalList.add(animal);

            AnimalWithMethods animalWithMethods = new AnimalWithMethods(name, isPredator, animalType, animalFood, habitat, speed);
            animalWithMethodsList.add(animalWithMethods);

            AnimalExternalizable animalExternalizable = new AnimalExternalizable(name, isPredator, animalType, animalFood, habitat, speed);
            animalExternalizableList.add(animalExternalizable);
        }

        serializer = new Serializer();
    }

    @Test
    public void checkDefaultSerialize() {
        System.out.println("Default serialize");
        fileName = "default";

        List<Animal> animalsBefore = animalList;

        long startTime = System.currentTimeMillis();
        try {
            serializer.defaultSerialize(animalsBefore, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("serialize: " + (endTime - startTime));

        List<Animal> animalsAfter = new ArrayList<>();

        startTime = System.currentTimeMillis();
        try {
            animalsAfter = serializer.defaultDeserialize(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();

        System.out.println("deserialize: " + (endTime - startTime));

        assertEquals(animalsAfter, animalsBefore);

        deleteFile(Paths.get(fileName));
        System.out.println();
    }

    @Test
    public void checkSerializeWithMethods() {
        System.out.println("Serialize with methods");
        fileName = "with_methods";

        List<AnimalWithMethods> animalsBefore = animalWithMethodsList;

        long startTime = System.currentTimeMillis();
        try {
            serializer.serializeWithMethods(animalsBefore, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("serialize: " + (endTime - startTime));

        List<AnimalWithMethods> animalsAfter = new ArrayList<>();

        startTime = System.currentTimeMillis();
        try {
            animalsAfter = serializer.deserializeWithMethods(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();

        System.out.println("deserialize: " + (endTime - startTime));

        assertEquals(animalsAfter, animalsBefore);

        deleteFile(Paths.get(fileName));
        System.out.println();
    }


    @Test
    public void checkSerializeWithExternalizable() {
        System.out.println("Externalizable serialize");
        fileName = "externalizable";

        List<AnimalExternalizable> animalsBefore = animalExternalizableList;

        long startTime = System.currentTimeMillis();
        try {
            serializer.serializeWithExternalizable(animalsBefore, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("serialize: " + (endTime - startTime));

        List<AnimalExternalizable> animalsAfter = new ArrayList<>();

        startTime = System.currentTimeMillis();
        try {
            animalsAfter = serializer.deserializeWithExternalizable(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();

        System.out.println("deserialize: " + (endTime - startTime));

        assertEquals(animalsAfter, animalsBefore);

        deleteFile(Paths.get(fileName));
        System.out.println();
    }


    @Test
    public void checkCustomSerialize() {
        System.out.println("Custom serialize");
        fileName = "custom";

        List<Animal> animalsBefore = animalList;

        long startTime = System.currentTimeMillis();
        try {
            serializer.customSerialize(animalsBefore, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("serialize: " + (endTime - startTime));

        List<Animal> animalsAfter = new ArrayList<>();

        startTime = System.currentTimeMillis();
        try {
            animalsAfter = serializer.customDeserialize(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();

        System.out.println("deserialize: " + (endTime - startTime));

        assertEquals(animalsAfter, animalsBefore);

        deleteFile(Paths.get(fileName));
        System.out.println();
    }

    private void deleteFile(Path filePath) {
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
