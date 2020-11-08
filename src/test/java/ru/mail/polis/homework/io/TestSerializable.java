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
    private static String fileNameFirst = "TestAnimal.bin";


    private static List<AnimalWithMethods> animalsWithMethods = new ArrayList<>();
    private String fileNameSecond = "TestAnimalWithMethods.bin";

    private static List<AnimalExternalizable> animalsExternalizable = new ArrayList<>();
    private String fileNameThird = "TestAnimalWithMethods.bin";


    private String fileNameFourth = "TestCustom.bin";

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


        List<Animal> arrayListOfAnimals = Arrays.asList(
                pixie, brutus, rex, nemo, tina, greed, cat, dog, slawa, dumbo);

        for (int i = 0; i < 100000; ++i) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, 10);
            animals.add(new Animal(
                    arrayListOfAnimals.get(randomIndex)));
            animalsWithMethods.add(new AnimalWithMethods(
                    arrayListOfAnimals.get(randomIndex)));
            animalsExternalizable.add((new AnimalExternalizable(
                    arrayListOfAnimals.get(randomIndex))));

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

        serializer.defaultSerialize(animals, fileNameFirst);


        long endTimeSerializable = System.currentTimeMillis();
        long timeSerializable = endTimeSerializable - startTimeSerializable;


        long startTimeDeserializable = System.currentTimeMillis();
        List<Animal> deserializableList = new ArrayList<>();

        deserializableList = serializer.defaultDeserialize(fileNameFirst);


        long endTimeDeserializable = System.currentTimeMillis();
        long timeDeserializable = endTimeDeserializable - startTimeDeserializable;

        long fileSize = Files.size(Paths.get(fileNameFirst));
        printInformation(timeSerializable, timeDeserializable, fileSize, "Default");

        Assert.assertEquals(animals, deserializableList);
    }

    @Test
    public void testSerializableWithMethods() throws IOException, ClassNotFoundException {

        long startTimeSerializable = System.currentTimeMillis();

        serializer.serializeWithMethods(animalsWithMethods, fileNameSecond);

        long endTimeSerializable = System.currentTimeMillis();
        long timeSerializable = endTimeSerializable - startTimeSerializable;


        long startTimeDeserializable = System.currentTimeMillis();
        List<AnimalWithMethods> deserializableList = new ArrayList<>();

        deserializableList = serializer.deserializeWithMethods(fileNameSecond);

        long endTimeDeserializable = System.currentTimeMillis();
        long timeDeserializable = endTimeDeserializable - startTimeDeserializable;

        long fileSize = Files.size(Paths.get(fileNameSecond));
        printInformation(timeSerializable, timeDeserializable, fileSize, "WithMethods");

        Assert.assertEquals(animalsWithMethods, deserializableList);
    }

    @Test
    public void testExternializable() throws IOException, ClassNotFoundException {

        long startTimeSerializable = System.currentTimeMillis();

        serializer.serializeWithExternalizable(animalsExternalizable, fileNameThird);

        long endTimeSerializable = System.currentTimeMillis();
        long timeSerializable = endTimeSerializable - startTimeSerializable;

        long startTimeDeserializable = System.currentTimeMillis();

        List<AnimalExternalizable> deserializableList = serializer.deserializeWithExternalizable(fileNameThird);

        long endTimeDeserializable = System.currentTimeMillis();
        long timeDeserializable = endTimeDeserializable - startTimeDeserializable;

        long fileSize = Files.size(Paths.get(fileNameThird));
        printInformation(timeSerializable, timeDeserializable, fileSize, "Externalizable");

        Assert.assertEquals(animalsExternalizable, deserializableList);
    }

    @Test
    public void testCustom() throws IOException, ClassNotFoundException {

        long startTimeSerializable = System.currentTimeMillis();

        serializer.customSerialize(animals, fileNameFourth);

        long endTimeSerializable = System.currentTimeMillis();
        long timeSerializable = endTimeSerializable - startTimeSerializable;

        long startTimeDeserializable = System.currentTimeMillis();
        List<Animal> deserializableList = new ArrayList<>();

        deserializableList = serializer.customDeserialize(fileNameFourth);

        long endTimeDeserializable = System.currentTimeMillis();
        long timeDeserializable = endTimeDeserializable - startTimeDeserializable;

        long fileSize = Files.size(Paths.get(fileNameFourth));
        printInformation(timeSerializable, timeDeserializable, fileSize, "Custom");

        Assert.assertEquals(animals, deserializableList);
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