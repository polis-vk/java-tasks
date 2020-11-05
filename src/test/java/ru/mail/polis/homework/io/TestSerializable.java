package ru.mail.polis.homework.io;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestSerializable {

    private static Serializer serializer = new Serializer();

    private static List<Animal> animals = new ArrayList<>();
    private static String fileNameFirst = "TestAnimal.txt";


    private static List<AnimalWithMethods> animalsWithMethods = new ArrayList<>();
    ;
    private String fileNameSecond = "TestAnimalWithMethods.txt";

    private static List<AnimalExternalizable> animalsExternalizable = new ArrayList<>();
    ;
    private String fileNameThird = "TestAnimalWithMethods.txt";

    private String fileNameFourth = "TestCustom.txt";

    @Before
    public void fill() {
        Animal pixie = new Animal("Pixie",
                0.7,
                new Parents("Kate", "George"),
                new ArrayList<Parents>(Arrays.asList(new Parents("Kate", "George"),
                        new Parents("Cora", "Alex"))), Colour.WHITE);


        Animal brutus = new Animal(
                "Brutus",
                20,
                new Parents("Leila", "Max"),
                new ArrayList<Parents>(Arrays.asList(new Parents("Leila", "Max"),
                        new Parents("Megan", "Monster"))), Colour.GREY);

        Animal rex = new Animal(
                "Rex",
                100,
                new Parents("M", "F"),
                new ArrayList<Parents>(Arrays.asList(new Parents("M", "F"),
                        new Parents("GM", "GF"))), Colour.BLACK);

        Animal nemo = new Animal(
                "Nemo",
                0.5,
                new Parents("Coral", "Marlin"),
                new ArrayList<Parents>(Arrays.asList(new Parents("Coral", "Marlin"),
                        new Parents("Tina", "John"))), Colour.RED);
        Animal tina = new Animal(
                "Tina",
                0.4,
                new Parents("Gloria", "Raze"),
                new ArrayList<Parents>(Arrays.asList(new Parents("Luna", "Martin"),
                        new Parents("Lina", "John"))), Colour.RED);
        Animal greed = new Animal(
                "Greed",
                50,
                new Parents("Mother", "Father"),
                new ArrayList<Parents>(Arrays.asList(new Parents("Mother", "Father"),
                        new Parents("Lola", "Crazy"))), Colour.RED);

        Animal cat = new Animal(
                "Mr. Cat",
                5,
                new Parents("Mrs. CatM", "Mr. CatF"),
                new ArrayList<Parents>(Arrays.asList(new Parents("Mrs. CatM", "Mr. CatF"),
                        new Parents("Mrs. CatGM", "Mr. CatGF"))), Colour.RED);

        Animal dog = new Animal(
                "Mr. Dog",
                10,
                new Parents("Mrs. DogM", "Mr. DogF"),
                new ArrayList<Parents>(Arrays.asList(new Parents("Mrs. DogM", "Mr. DogF"),
                        new Parents("Mrs. DogGM", "Mr. DogGF"))), Colour.RED);

        Animal slawa = new Animal(
                "Slawa",
                50,
                new Parents("Valua", "Vova"),
                new ArrayList<Parents>(Arrays.asList(new Parents("Valua", "Vova"),
                        new Parents("Orange", "Vasya"))), Colour.RED);

        Animal dumbo = new Animal(
                "Dumbo",
                6000,
                new Parents("Mom", "Dad"),
                new ArrayList<Parents>(Arrays.asList(new Parents("Mom", "Mr. DogF"),
                        new Parents("GMom", "GDad"))), Colour.RED);


        List<Animal> arrayListOfAnimals = new ArrayList<>(Arrays.asList(
                pixie, brutus, rex, nemo, tina, greed, cat, dog, slawa, dumbo));

        for (int i = 0; i < 10000; ++i) {
            animals.add(new Animal(
                    arrayListOfAnimals.get(ThreadLocalRandom.current().nextInt(0, 10))));
        }

        for (int i = 0; i < 10000; ++i) {
            animalsWithMethods.add(new AnimalWithMethods(
                    arrayListOfAnimals.get(ThreadLocalRandom.current().nextInt(0, 10))));
        }



        for (int i = 0; i < 10000; ++i) {
            animalsExternalizable.add(new AnimalExternalizable(
                    arrayListOfAnimals.get(ThreadLocalRandom.current().nextInt(0, 10))));
        }
    }

    private void printInformation(Long timeSerializable, Long timeDeserializable, Long fileSize, String test) {
        System.out.println("\n\n" + test + ":\n" +
                "Time of Serialization: "
                + timeSerializable + "\n"
                + "Time of Deserialization: "
                + timeDeserializable + "\n"
                + "Size of File: " + fileSize + "\n");
    }

    @Test
    public void testSerializableDefault() throws IOException, ClassNotFoundException {
        long startTimeSerializable = System.currentTimeMillis();
        for (int i = 0; i < 10; ++i) {
            serializer.defaultSerialize(animals, fileNameFirst);
        }

        long endTimeSerializable = System.currentTimeMillis();
        long timeSerializable = endTimeSerializable - startTimeSerializable;


        long startTimeDeserializable = System.currentTimeMillis();
        List<Animal> deserializableList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            deserializableList = serializer.defaultDeserialize(fileNameFirst);
        }

        long endTimeDeserializable = System.currentTimeMillis();
        long timeDeserializable = endTimeDeserializable - startTimeDeserializable;

        long fileSize = Files.size(Paths.get(fileNameFirst));
        printInformation(timeSerializable, timeDeserializable, fileSize, "Default");

        Assert.assertArrayEquals(animals.toArray(), deserializableList.toArray());
    }

    @Test
    public void testSerializableWithMethods() throws IOException, ClassNotFoundException {

        long startTimeSerializable = System.currentTimeMillis();
        for (int i = 0; i < 10; ++i) {
            serializer.serializeWithMethods(animalsWithMethods, fileNameSecond);
        }
        long endTimeSerializable = System.currentTimeMillis();
        long timeSerializable = endTimeSerializable - startTimeSerializable;


        long startTimeDeserializable = System.currentTimeMillis();
        List<AnimalWithMethods> deserializableList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            deserializableList = serializer.deserializeWithMethods(fileNameSecond);
        }
        long endTimeDeserializable = System.currentTimeMillis();
        long timeDeserializable = endTimeDeserializable - startTimeDeserializable;

        long fileSize = Files.size(Paths.get(fileNameSecond));
        printInformation(timeSerializable, timeDeserializable, fileSize, "WithMethods");

        Assert.assertArrayEquals(animalsWithMethods.toArray(), deserializableList.toArray());
    }

    @Test
    public void testExternializable() throws IOException, ClassNotFoundException {

        long startTimeSerializable = System.currentTimeMillis();
        for (int i = 0; i < 10; ++i) {
            serializer.serializeWithExternalizable(animalsExternalizable, fileNameThird);
        }
        long endTimeSerializable = System.currentTimeMillis();
        long timeSerializable = endTimeSerializable - startTimeSerializable;

        long startTimeDeserializable = System.currentTimeMillis();
        List<AnimalExternalizable> deserializableList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            deserializableList = serializer.deserializeWithExternalizable(fileNameThird);
        }
        long endTimeDeserializable = System.currentTimeMillis();
        long timeDeserializable = endTimeDeserializable - startTimeDeserializable;

        long fileSize = Files.size(Paths.get(fileNameThird));
        printInformation(timeSerializable, timeDeserializable, fileSize, "Externalizable");

        Assert.assertArrayEquals(animalsExternalizable.toArray(), deserializableList.toArray());
    }

    @Test
    public void testCustom() throws IOException, ClassNotFoundException {

        long startTimeSerializable = System.currentTimeMillis();
        for (int i = 0; i < 10; ++i) {
            serializer.customSerialize(animals, fileNameFourth);
        }
        long endTimeSerializable = System.currentTimeMillis();
        long timeSerializable = endTimeSerializable - startTimeSerializable;

        long startTimeDeserializable = System.currentTimeMillis();
        List<Animal> deserializableList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            deserializableList = serializer.customDeserialize(fileNameFourth);
        }
        long endTimeDeserializable = System.currentTimeMillis();
        long timeDeserializable = endTimeDeserializable - startTimeDeserializable;

        long fileSize = Files.size(Paths.get(fileNameFourth));
        printInformation(timeSerializable, timeDeserializable, fileSize, "Custom");

        Assert.assertArrayEquals(animals.toArray(), deserializableList.toArray());
    }

    @After
    public void deleteFiles() {
        try {
            Files.deleteIfExists(Paths.get(fileNameFirst));
            Files.deleteIfExists(Paths.get(fileNameSecond));
            Files.deleteIfExists(Paths.get(fileNameThird));
            Files.deleteIfExists(Paths.get(fileNameFourth));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}