package ru.mail.polis.homework.io.objects;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class SerializerTest {

    private static final int ANIMALS_QUANTITY = 10_000;
    private static final int SEED = 228;
    private static final List<String> todosList = Arrays.asList("sleep", "eat", "walk", "play", "swim");
    private final String fileName = "src/test/java/ru/mail/polis/homework/io/objects/serialize";
    Serializer serializer = new Serializer();

    private static final List<Animal> animals = new ArrayList<Animal>(){{
        Random random = new Random(SEED);
        Animal animal;
        for (int i = 0; i < ANIMALS_QUANTITY; i++){
            animal = new Animal(String.valueOf(random.nextInt()),
                    random.nextInt(),
                    random.nextInt(),
                    Arrays.asList(todosList.get(random.nextInt(5)), todosList.get(random.nextInt(5)), todosList.get(random.nextInt(5))),
                    Species.values()[random.nextInt(5)],
                    new Clothes(String.valueOf(random.nextInt()), random.nextInt())
            );
            add(animal);
        }
    }};

    private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<AnimalWithMethods>(){{
        Random random = new Random(SEED);
        AnimalWithMethods animal;
        for (int i = 0; i < ANIMALS_QUANTITY; i++){
            animal = new AnimalWithMethods(String.valueOf(random.nextInt()),
                    random.nextInt(),
                    random.nextInt(),
                    Arrays.asList(todosList.get(random.nextInt(5)), todosList.get(random.nextInt(5)), todosList.get(random.nextInt(5))),
                    Species.values()[random.nextInt(5)],
                    new Clothes(String.valueOf(random.nextInt()), random.nextInt())
            );
            add(animal);
        }
    }};

    private static final List<AnimalExternalizable> animalsExternalizable = new ArrayList<AnimalExternalizable>(){{
        Random random = new Random(SEED);
        AnimalExternalizable animal;
        for (int i = 0; i < ANIMALS_QUANTITY; i++){
            animal = new AnimalExternalizable(String.valueOf(random.nextInt()),
                    random.nextInt(),
                    random.nextInt(),
                    Arrays.asList(todosList.get(random.nextInt(5)), todosList.get(random.nextInt(5)), todosList.get(random.nextInt(5))),
                    Species.values()[random.nextInt(5)],
                    new ClothesExternalizable(String.valueOf(random.nextInt()), random.nextInt())
            );
            add(animal);
        }
    }};

    @Test
    public void defaultSerialize() {
        long startTime = System.currentTimeMillis();
        serializer.defaultSerialize(animals, fileName);
        long serializeEndTime = System.currentTimeMillis();
        List<Animal> animalsDeserialized = serializer.defaultDeserialize(fileName);
        long deserializeEndTime = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / 1024; // Объём файла в Кб

        Assert.assertArrayEquals(animals.toArray(), animalsDeserialized.toArray());

        System.out.println("Default Ser/Deser: ");
        System.out.println("time of serialize = " + (serializeEndTime - startTime) + " ms ");
        System.out.println("time of deserialize = " + (deserializeEndTime - serializeEndTime) + " ms ");
        System.out.println("total time = " + (deserializeEndTime - startTime) + " ms");
        System.out.println("file size = " + fileSize + "Kbs");
        System.out.println();
    }

    @Test
    public void serializeWithMethods() {
        long startTime = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, fileName);
        long serializeEndTime = System.currentTimeMillis();
        List<AnimalWithMethods> animalsDeserialized = serializer.deserializeWithMethods(fileName);
        long deserializeEndTime = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / 1024; // Объём файла в Кб

        Assert.assertArrayEquals(animalsWithMethods.toArray(), animalsDeserialized.toArray());

        System.out.println("WithMethods Ser/Deser: ");
        System.out.println("time of serialize = " + (serializeEndTime - startTime) + " ms ");
        System.out.println("time of deserialize = " + (deserializeEndTime - serializeEndTime) + " ms ");
        System.out.println("total time = " + (deserializeEndTime - startTime) + " ms");
        System.out.println("file size = " + fileSize + "Kbs");
        System.out.println();
    }

    @Test
    public void serializeWithExternalizable() {
        long startTime = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, fileName);
        long serializeEndTime = System.currentTimeMillis();
        List<AnimalExternalizable> animalsDeserialized = serializer.deserializeWithExternalizable(fileName);
        long deserializeEndTime = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / 1024; // Объём файла в Кб

        Assert.assertArrayEquals(animalsExternalizable.toArray(), animalsDeserialized.toArray());

        System.out.println("Externalizable Ser/Deser: ");
        System.out.println("time of serialize = " + (serializeEndTime - startTime) + " ms ");
        System.out.println("time of deserialize = " + (deserializeEndTime - serializeEndTime) + " ms ");
        System.out.println("total time = " + (deserializeEndTime - startTime) + " ms");
        System.out.println("file size = " + fileSize + "Kbs");
        System.out.println();
    }

    @Test
    public void customSerialize() {
        long startTime = System.currentTimeMillis();
        serializer.customSerialize(animals, fileName);
        long serializeEndTime = System.currentTimeMillis();
        List<Animal> animalsDeserialized = serializer.customDeserialize(fileName);
        long deserializeEndTime = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / 1024; // Объём файла в Кб

        Assert.assertArrayEquals(animals.toArray(), animalsDeserialized.toArray());

        System.out.println("Custom Ser/Deser: ");
        System.out.println("time of serialize = " + (serializeEndTime - startTime) + " ms ");
        System.out.println("time of deserialize = " + (deserializeEndTime - serializeEndTime) + " ms ");
        System.out.println("total time = " + (deserializeEndTime - startTime) + " ms");
        System.out.println("file size = " + fileSize + "Kbs");
        System.out.println();
    }

    @After
    public void deleteFile(){
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}