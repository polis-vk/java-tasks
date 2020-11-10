package ru.mail.polis.homework.io.objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SerializerTest {
    private final static String PATH = "Test%d.bin";
    private final static int DATA_AMOUNT = 10;
    private final static int COPIES_AMOUNT = 1000;
    private final static int STRINGS_LENGTH = 10;

    private final static Random rand = new Random();
    private final static Serializer SERIALIZER = new Serializer();

    private static List<Animal> animals = new ArrayList<>();
    private static List<AnimalWithMethods> animalsWithMethods = new ArrayList<>();
    private static List<AnimalExternalizable> animalsExternalizable = new ArrayList<>();

    @BeforeClass
    public static void before() {
        List<Animal> animalsTemp = new ArrayList<>();
        List<AnimalWithMethods> animalsWithMethodsTemp = new ArrayList<>();
        List<AnimalExternalizable> animalsExternalizableTemp = new ArrayList<>();
        for (int i = 0; i < DATA_AMOUNT; ++i) {
            StringBuilder animalName = new StringBuilder();
            for (int j = 0; j < STRINGS_LENGTH; ++j) {
                animalName.append((char) rand.nextInt(Character.MAX_VALUE));
            }

            int age = rand.nextInt();
            double weight = rand.nextDouble();
            AnimalType type = AnimalType.values()[rand.nextInt(AnimalType.values().length)];

            StringBuilder ownerName = new StringBuilder();
            for (int j = 0; j < STRINGS_LENGTH; ++j) {
                ownerName.append((char) rand.nextInt(Character.MAX_VALUE));
            }
            Person owner = new Person(ownerName.toString(), rand.nextInt());

            final int MAX_FOOD_COUNT = 4;
            final int FOOD_COUNT = rand.nextInt(MAX_FOOD_COUNT - 1) + 1;
            List<Food> nutrition = new ArrayList<>();
            for (int j = 0; j < FOOD_COUNT; ++j) {
                StringBuilder foodName = new StringBuilder();
                for (int k = 0; j < STRINGS_LENGTH; ++j) {
                    foodName.append((char) rand.nextInt(Character.MAX_VALUE));
                }
                Food food = new Food(foodName.toString(), rand.nextInt());
                nutrition.add(food);
            }

            animalsTemp.add(new Animal(animalName.toString(), age, weight, type, owner, nutrition));
            animalsWithMethodsTemp.add(new AnimalWithMethods(animalName.toString(), age, weight, type, owner, nutrition));
            animalsExternalizableTemp.add(new AnimalExternalizable(animalName.toString(), age, weight, type, owner, nutrition));
        }
        for (int i = 0; i < COPIES_AMOUNT; ++i) {
            for (int j = 0; j < DATA_AMOUNT; ++j) {
                animals.add(new Animal(animalsTemp.get(j)));
                animalsWithMethods.add(new AnimalWithMethods(animalsWithMethodsTemp.get(j)));
                animalsExternalizable.add(new AnimalExternalizable(animalsExternalizableTemp.get(j)));
            }
            /*animals.addAll(animalsTemp);
            animalsWithMethods.addAll(animalsWithMethodsTemp);
            animalsExternalizable.addAll(animalsExternalizableTemp);*/
        }
    }

    @Test
    public void defaultSerialize() {
        String path = new Formatter().format(PATH, 1).toString();
        long beginTime = System.currentTimeMillis();
        SERIALIZER.defaultSerialize(animals, path);
        long serializeTime = System.currentTimeMillis();
        List<Animal> list = SERIALIZER.defaultDeserialize(path);
        long endTime = System.currentTimeMillis();
        try {
            printTestInfo("Default serialize", (serializeTime - beginTime), (endTime - serializeTime), Files.size(Paths.get(path)));
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.toString(), animals.toString());

    }

    @Test
    public void serializeWithMethods() {
        String path = new Formatter().format(PATH, 2).toString();
        long beginTime = System.currentTimeMillis();
        SERIALIZER.serializeWithMethods(animalsWithMethods, path);
        long serializeTime = System.currentTimeMillis();
        List<AnimalWithMethods> list = SERIALIZER.deserializeWithMethods(path);
        long endTime = System.currentTimeMillis();
        try {
            printTestInfo("Serialize with methods", (serializeTime - beginTime), (endTime - serializeTime), Files.size(Paths.get(path)));
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.toString(), animalsWithMethods.toString());
    }

    @Test
    public void serializeWithExternalizable() {
        String path = new Formatter().format(PATH, 2).toString();
        long beginTime = System.currentTimeMillis();
        SERIALIZER.serializeWithExternalizable(animalsExternalizable, path);
        long serializeTime = System.currentTimeMillis();
        List<AnimalExternalizable> list = SERIALIZER.deserializeWithExternalizable(path);
        long endTime = System.currentTimeMillis();
        try {
            printTestInfo("Serialize with externalizable", (serializeTime - beginTime), (endTime - serializeTime), Files.size(Paths.get(path)));
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.toString(), animalsExternalizable.toString());
    }

    @Test
    public void customSerialization() {
        String path = new Formatter().format(PATH, 2).toString();
        long beginTime = System.currentTimeMillis();
        SERIALIZER.customSerialize(animals, path);
        long serializeTime = System.currentTimeMillis();
        List<Animal> list = SERIALIZER.customDeserialize(path);
        long endTime = System.currentTimeMillis();
        try {
            printTestInfo("Custom serialize", (serializeTime - beginTime), (endTime - serializeTime), Files.size(Paths.get(path)));
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.toString(), animals.toString());
    }

    @Test
    public void customSerializationWithObjectStream() {
        String path = new Formatter().format(PATH, 2).toString();
        long beginTime = System.currentTimeMillis();
        SERIALIZER.customSerializeWithObjectStream(animals, path);
        long serializeTime = System.currentTimeMillis();
        List<Animal> list = SERIALIZER.customDeserializeWithObjectStream(path);
        long endTime = System.currentTimeMillis();
        try {
            printTestInfo("Custom serialize  with object streams", (serializeTime - beginTime), (endTime - serializeTime), Files.size(Paths.get(path)));
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.toString(), animals.toString());
    }

    private void printTestInfo(String testName, long serializeTime, long desirealizeTime, long fileSize) {
        System.out.println(testName + ": serialize time = " + serializeTime + " ms; desirealize time = " + desirealizeTime + " ms; file size = " + fileSize + " b");
    }
}
