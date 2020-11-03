package ru.mail.polis.homework.io.objects;

import org.junit.Before;
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
    private List<Animal> animalList;
    private List<AnimalWithMethods> animalWithMethodsList;
    private List<AnimalExternalizable> animalExternalizableList;
    private Serializer serializer;
    private static final Random random = new Random();

    @Before
    public void setUp() {
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
        animalList = new ArrayList<>();
        animalWithMethodsList = new ArrayList<>();
        animalExternalizableList = new ArrayList<>();

        int numberOfElements = 10000;

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

    private void deleteFile(Path filePath) throws IOException {
        Files.delete(filePath);
    }
}
