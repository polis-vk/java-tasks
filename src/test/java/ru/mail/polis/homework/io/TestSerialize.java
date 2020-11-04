package ru.mail.polis.homework.io;

import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    private final int countOfAnimals = 10;
    private final Serializer serialize = new Serializer();
    private final String pathToFile = "/Users/gvgromov/java-tasks/src/test/java/ru/mail/polis/homework/io/test.bin";
    private final String englishSymbols = "abcdefghijklmnopqrstuvwxyz";

    @Before
    public void fillObjects() {
        animals = new ArrayList<>(countOfAnimals);
        animalsWithMethods = new ArrayList<>(countOfAnimals);
        animalsExternalizables = new ArrayList<>(countOfAnimals);
        List<String> names = new ArrayList<>(countOfAnimals);
        List<Integer> ages = new ArrayList<>(countOfAnimals);
        List<List<String>> friendsName = new ArrayList<>(countOfAnimals);
        List<String> nodeFriendsName = new ArrayList<>(countOfAnimals);
        List<Animal.AnimalStructure> animalsStructures = new ArrayList<>(countOfAnimals);
        List<Boolean> isPredator = new ArrayList<>(countOfAnimals);
        List<Size> animalSize = new ArrayList<>(countOfAnimals);


        for (int i = 0; i < countOfAnimals; ++i) { //fill random names
            String random = new Random().ints(countOfAnimals, 0, englishSymbols.length())
                    .mapToObj(englishSymbols::charAt)
                    .map(Object::toString)
                    .collect(Collectors.joining());
            names.add(random);
        }


        for (int i = 0; i < countOfAnimals; ++i) { //fill random ages
            ages.add((int) (Math.random() * 100 + 1));
        }


        for (int i = 0; i < countOfAnimals; ++i) { //fill random friend names
            for (int j = 0; j < countOfAnimals; ++j) {
                String random = new Random().ints(countOfAnimals, 0, englishSymbols.length())
                        .mapToObj(englishSymbols::charAt)
                        .map(Object::toString)
                        .collect(Collectors.joining());
                nodeFriendsName.add(random);
            }
            friendsName.add(nodeFriendsName);
            nodeFriendsName.clear();
        }


        for (int i = 0; i < countOfAnimals; ++i) { // fill random animal structure
            if (i % 2 == 0) {
                animalsStructures.add(Animal.AnimalStructure.MULTICELLULAR);
            } else {
                animalsStructures.add(Animal.AnimalStructure.UNICELLULAR);
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
            animals.add(new Animal(ages.get(i), names.get(i), friendsName.get(i), isPredator.get(i), animalSize.get(i), animalsStructures.get(i)));
            animalsWithMethods.add(new AnimalWithMethods(ages.get(i), names.get(i), friendsName.get(i), isPredator.get(i), animalSize.get(i), animalsStructures.get(i)));
            animalsExternalizables.add( new AnimalExternalizable(ages.get(i), names.get(i), friendsName.get(i), isPredator.get(i), animalSize.get(i), animalsStructures.get(i)));
        }
    }

    @Test
    public void defaultSerializeTest() throws IOException {
        serialize.defaultSerialize(animals, pathToFile);
        List<Animal> deserializeObjects = serialize.defaultDeserialize(pathToFile);
        assertEquals(animals, deserializeObjects);
        Files.delete(Paths.get(pathToFile));
    }

    @Test
    public void deserializeWithMethodsTest() throws IOException {
        serialize.serializeWithMethods(animalsWithMethods, pathToFile);
        List<AnimalWithMethods> deserializeObjects = serialize.deserializeWithMethods(pathToFile);
        assertEquals(animalsWithMethods, deserializeObjects);
        Files.delete(Paths.get(pathToFile));
    }

    @Test
    public void serializeWithExternalizableTest() throws IOException {
        serialize.serializeWithExternalizable(animalsExternalizables, pathToFile);
        List<AnimalExternalizable> deserializeObjects = serialize.deserializeWithExternalizable(pathToFile);
        assertEquals(animalsExternalizables, deserializeObjects);
        Files.delete(Paths.get(pathToFile));
    }

    @Test
    public void customSerializeTest() throws IOException {
        serialize.customSerialize(animals, pathToFile);
        List<Animal> deserializeObjects = serialize.customDeserialize(pathToFile);
        assertEquals(animals, deserializeObjects);
        Files.delete(Paths.get(pathToFile));
    }
}
