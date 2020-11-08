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
    private  List<Animal> animals;

    private  List<AnimalWithMethods> animalsWithMethods;

    private  List<AnimalExternalizable> animalsExternalizables;

    private final Serializer serialize = new Serializer();
    private final String pathToFile = "/Users/gvgromov/java-tasks/src/test/java/ru/mail/polis/homework/io/test.bin";

    @Before
    public void fillObjects() throws IOException {
        final int countOfAnimals = 100000;
        animals = new ArrayList<>(countOfAnimals);

        animalsWithMethods = new ArrayList<>(countOfAnimals);

        animalsExternalizables = new ArrayList<>(countOfAnimals);

        List<String> names = new ArrayList<>(countOfAnimals);
        List<Integer> ages = new ArrayList<>(countOfAnimals);
        List<String> friendsName = new ArrayList<>(countOfAnimals);
        List<AnimalStructure> animalsStructures = new ArrayList<>(countOfAnimals);
        List<Boolean> isPredator = new ArrayList<>(countOfAnimals);
        List<Size> animalSize = new ArrayList<>(countOfAnimals);


        String englishSymbols = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < countOfAnimals; ++i) { //fill random names
            String random = new Random().ints(15, 0, englishSymbols.length())
                    .mapToObj(englishSymbols::charAt)
                    .map(Object::toString)
                    .collect(Collectors.joining());
            names.add(random);
        }


        for (int i = 0; i < countOfAnimals; ++i) { //fill random ages
            ages.add((int) (Math.random() * 100 + 1));
        }


        for (int i = 0; i < 10; ++i) { //fill random friend names
            String random = new Random().ints(15, 0, englishSymbols.length())
                    .mapToObj(englishSymbols::charAt)
                    .map(Object::toString)
                    .collect(Collectors.joining());
            friendsName.add(random);
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
            animalsExternalizables.add( new AnimalExternalizable(ages.get(i), names.get(i), friendsName, isPredator.get(i), animalSize.get(i), animalsStructures.get(i)));
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
}
