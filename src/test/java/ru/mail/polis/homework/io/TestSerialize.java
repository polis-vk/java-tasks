package ru.mail.polis.homework.io;

import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TestSerialize {
    private List<Animal> animals;

    private List<AnimalWithMethods> animalsWithMethods;

    private List<AnimalExternalizable> animalsExternalizables;

    private final Serializer serialize = new Serializer();
    private final String pathToFile = "/Users/gvgromov/java-tasks/src/test/java/ru/mail/polis/homework/io/test.bin";

    @Before
    public void fillObjects() throws IOException {
        final int countOfAnimals = 10000;
        animals = new ArrayList<>(countOfAnimals);

        animalsWithMethods = new ArrayList<>(countOfAnimals);

        animalsExternalizables = new ArrayList<>(countOfAnimals);

        List<String> names = new ArrayList<>(countOfAnimals);
        List<Integer> ages = new ArrayList<>(countOfAnimals);
        List<String> friendsName = new ArrayList<>(countOfAnimals);
        List<AnimalStructure> animalsStructures = new ArrayList<>(countOfAnimals);
        List<Boolean> isPredator = new ArrayList<>(countOfAnimals);
        List<Size> animalSize = new ArrayList<>(countOfAnimals);
        Random random = new Random();
        String randomString;


        String englishSymbols = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < countOfAnimals; ++i) { //fill random names
            randomString = random.ints(15, 0, englishSymbols.length())
                    .mapToObj(englishSymbols::charAt)
                    .map(Object::toString)
                    .collect(Collectors.joining());
            names.add(randomString);
        }


        for (int i = 0; i < countOfAnimals; ++i) { //fill random ages
            ages.add((int) (Math.random() * 100 + 1));
        }


        for (int i = 0; i < 10; ++i) { //fill random friend names
            randomString = random.ints(15, 0, englishSymbols.length())
                    .mapToObj(englishSymbols::charAt)
                    .map(Object::toString)
                    .collect(Collectors.joining());
            friendsName.add(randomString);
        }


        for (int i = 0; i < countOfAnimals; ++i) { // fill random animal structure
            if (i % 2 == 0) {
                animalsStructures.add(AnimalStructure.MULTICELLULAR);
            } else {
                animalsStructures.add(AnimalStructure.UNICELLULAR);
            }
        }


        for (int i = 0; i < countOfAnimals; ++i) { //fill predator
            if (i % 2 == 0) {
                isPredator.add(true);
            } else {
                isPredator.add(false);
            }
        }


        for (int i = 0; i < countOfAnimals; ++i) { //fill animal size
            animalSize.add(new Size((int) (Math.random() * 100 + 1), (int) (Math.random() * 100 + 1)));
        }


        for (int i = 0; i < countOfAnimals; ++i) { // fill animal classes
            animals.add(new Animal(ages.get(i), names.get(i), friendsName, isPredator.get(i), animalSize.get(i), animalsStructures.get(i)));
            animalsWithMethods.add(new AnimalWithMethods(ages.get(i), names.get(i), friendsName, isPredator.get(i), animalSize.get(i), animalsStructures.get(i)));
            animalsExternalizables.add(new AnimalExternalizable(ages.get(i), names.get(i), friendsName, isPredator.get(i), animalSize.get(i), animalsStructures.get(i)));
        }

    }

    @Test
    public void defaultSerializeTest() throws IOException {
        System.out.println("Default");
        long start = System.currentTimeMillis();
        serialize.defaultSerialize(animals, pathToFile);
        System.out.println(System.currentTimeMillis() - start);
        File f = new File(pathToFile);
        System.out.println(f.length());
        start = System.currentTimeMillis();
        List<Animal> deserializeObjects = serialize.defaultDeserialize(pathToFile);
        System.out.println(System.currentTimeMillis() - start);
        assertEquals(animals, deserializeObjects);
        Files.delete(Paths.get(pathToFile));
    }

    @Test
    public void serializeWithMethodsTest() throws IOException {
        System.out.println("WithMethods");
        long start = System.currentTimeMillis();
        serialize.serializeWithMethods(animalsWithMethods, pathToFile);
        System.out.println(System.currentTimeMillis() - start);
        File f = new File(pathToFile);
        System.out.println(f.length());
        start = System.currentTimeMillis();
        List<AnimalWithMethods> deserializeObjects = serialize.deserializeWithMethods(pathToFile);
        System.out.println(System.currentTimeMillis() - start);
        assertEquals(animalsWithMethods, deserializeObjects);
        Files.delete(Paths.get(pathToFile));
    }

    @Test
    public void serializeWithExternalizableTest() throws IOException {
        System.out.println("Externalizable");
        long start = System.currentTimeMillis();
        serialize.serializeWithExternalizable(animalsExternalizables, pathToFile);
        System.out.println(System.currentTimeMillis() - start);
        File f = new File(pathToFile);
        System.out.println(f.length());
        start = System.currentTimeMillis();
        List<AnimalExternalizable> deserializeObjects = serialize.deserializeWithExternalizable(pathToFile);
        System.out.println(System.currentTimeMillis() - start);
        assertEquals(animalsExternalizables, deserializeObjects);
        Files.delete(Paths.get(pathToFile));
    }

    @Test
    public void customSerializeTest() throws IOException {
        System.out.println("Custom");
        long start = System.currentTimeMillis();
        serialize.customSerialize(animals, pathToFile);
        System.out.println(System.currentTimeMillis() - start);
        File f = new File(pathToFile);
        System.out.println(f.length());
        start = System.currentTimeMillis();
        List<Animal> deserializeObjects = serialize.customDeserialize(pathToFile);
        System.out.println(System.currentTimeMillis() - start);
        assertEquals(animals, deserializeObjects);
        Files.delete(Paths.get(pathToFile));
    }

    @Test
    public void hugeSerializeTest() throws IOException {
        int countOfApproach = 99;
        List<Animal> deserializeObjects = new ArrayList<>();
        List<AnimalWithMethods> deserializeObjectsWithMethods = new ArrayList<>();
        List<AnimalExternalizable> deserializeObjectsExternalizable = new ArrayList<>();
        List<Animal> deserializeObjectsCustom = new ArrayList<>();
        serialize.defaultSerialize(animals, pathToFile);
        deserializeObjects = serialize.defaultDeserialize(pathToFile);
        serialize.serializeWithMethods(animalsWithMethods, pathToFile);
        deserializeObjectsWithMethods = serialize.deserializeWithMethods(pathToFile);
        serialize.serializeWithExternalizable(animalsExternalizables, pathToFile);
        deserializeObjectsExternalizable = serialize.deserializeWithExternalizable(pathToFile);
        serialize.customSerialize(animals, pathToFile);
        deserializeObjectsCustom = serialize.customDeserialize(pathToFile);
        long start = 0;
        long sumDefault = 0;
        long sumDefaultDes = 0;
        long sumMethods = 0;
        long sumMethodsDes = 0;
        long sumExtern = 0;
        long sumExternDes = 0;
        long sumCustom = 0;
        long sumCustomDes = 0;

        for(int i = 0; i < countOfApproach; ++i) {
            start = System.currentTimeMillis();
            serialize.defaultSerialize(animals, pathToFile);
            sumDefault += System.currentTimeMillis() - start;
            deserializeObjects = serialize.defaultDeserialize(pathToFile);
            assertEquals(animals, deserializeObjects);
        }
        for(int i = 0; i < countOfApproach; ++i) {
            serialize.defaultSerialize(animals, pathToFile);
            start = System.currentTimeMillis();
            deserializeObjects = serialize.defaultDeserialize(pathToFile);
            assertEquals(animals, deserializeObjects);
            sumDefaultDes += System.currentTimeMillis() - start;
        }
        System.out.println("Default\n" + "Ser: " + sumDefault / countOfApproach + "\nDes: " + sumDefaultDes / countOfApproach + "\n");


        for(int i = 0; i < countOfApproach; ++i) {
            start = System.currentTimeMillis();
            serialize.serializeWithMethods(animalsWithMethods, pathToFile);
            sumMethods += System.currentTimeMillis() - start;
            deserializeObjectsWithMethods = serialize.deserializeWithMethods(pathToFile);
            assertEquals(animalsWithMethods, deserializeObjectsWithMethods);
        }
        for(int i = 0; i < countOfApproach; ++i) {
            serialize.serializeWithMethods(animalsWithMethods, pathToFile);
            start = System.currentTimeMillis();
            deserializeObjectsWithMethods = serialize.deserializeWithMethods(pathToFile);
            assertEquals(animalsWithMethods, deserializeObjectsWithMethods);
            sumMethodsDes += System.currentTimeMillis() - start;
        }
        System.out.println("With methods\n" + "Ser: " + sumMethods / countOfApproach + "\nDes: " + sumMethodsDes / countOfApproach + "\n");

        for(int i = 0; i < countOfApproach; ++i) {
            start = System.currentTimeMillis();
            serialize.serializeWithExternalizable(animalsExternalizables, pathToFile);
            sumExtern += System.currentTimeMillis() - start;
            deserializeObjectsExternalizable = serialize.deserializeWithExternalizable(pathToFile);
            assertEquals(animalsExternalizables, deserializeObjectsExternalizable);
        }
        for(int i = 0; i < countOfApproach; ++i) {
            serialize.serializeWithExternalizable(animalsExternalizables, pathToFile);
            start = System.currentTimeMillis();
            deserializeObjectsExternalizable = serialize.deserializeWithExternalizable(pathToFile);
            assertEquals(animalsExternalizables, deserializeObjectsExternalizable);
            sumExternDes += System.currentTimeMillis() - start;
        }
        System.out.println("Externizable\n" + "Ser: " + sumExtern / countOfApproach + "\nDes: " + sumExternDes / countOfApproach + "\n");


        for(int i = 0; i < countOfApproach; ++i) {
            start = System.currentTimeMillis();
            serialize.customSerialize(animals, pathToFile);
            sumCustom += System.currentTimeMillis() - start;
            deserializeObjectsCustom = serialize.customDeserialize(pathToFile);
            assertEquals(animals, deserializeObjectsCustom);
        }
        for(int i = 0; i < countOfApproach; ++i) {
            serialize.customSerialize(animals, pathToFile);
            start = System.currentTimeMillis();
            deserializeObjectsCustom = serialize.customDeserialize(pathToFile);
            assertEquals(animals, deserializeObjectsCustom);
            sumCustomDes += System.currentTimeMillis() - start;
        }
        System.out.println("Custom\n" + "Ser: " + sumCustom / countOfApproach + "\nDes: " + sumCustomDes / countOfApproach + "\n");
    }
}
