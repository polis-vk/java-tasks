package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AnimalSerializeTest {

    private static final Path FILE_PATH = Paths.get("src", "test", "resources", "directories", "serializable", "animal.txt");
    private static final Serializer serializer = new Serializer();

    @Before
    public void setUp() throws IOException {
        Files.createDirectories(FILE_PATH.getParent());
        Files.createFile(FILE_PATH);
    }

    @After
    public void deleteFile() throws IOException {
        Files.deleteIfExists(FILE_PATH);
        Files.deleteIfExists(FILE_PATH.getParent());
    }


    @Test
    public void testSerializableOfAnimals() throws IOException {
        List<Animal> animals = generateAnimals(400000);
        long start = System.currentTimeMillis();
        serializer.defaultSerialize(animals, FILE_PATH.toString());
        long end = System.currentTimeMillis();
        System.out.println("defaultSerialization: " + (end - start));

        start = System.currentTimeMillis();
        List<Animal> animalsCopy = serializer.defaultDeserialize(FILE_PATH.toString());
        end = System.currentTimeMillis();
        System.out.println("defaultDeserialization: " + (end - start));
        System.out.println("File size: " + Files.size(FILE_PATH));
        assertEquals(animals.size(), animalsCopy.size());
        assertEquals(animals, animalsCopy);

    }

    @Test
    public void testSerializableOfAnimalsWithMethods() throws IOException {
        List<AnimalWithMethods> animals = generateAnimalsWithMethods(400000);
        long start = System.currentTimeMillis();
        serializer.serializeWithMethods(animals, FILE_PATH.toString());
        long end = System.currentTimeMillis();
        System.out.println("serializationWithMethods: " + (end - start));

        start = System.currentTimeMillis();
        List<AnimalWithMethods> animalsCopy = serializer.deserializeWithMethods(FILE_PATH.toString());
        end = System.currentTimeMillis();
        System.out.println("deserializationWithMethods: " + (end - start));
        System.out.println("File size: " + Files.size(FILE_PATH));

        assertEquals(animals.size(), animalsCopy.size());
        assertEquals(animals, animalsCopy);
    }

    @Test
    public void testSerializableOfAnimalsExternalizable() throws IOException {
        List<AnimalExternalizable> animals = generateAnimalsExternalizable(400000);
        Serializer serializer = new Serializer();
        long start = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animals, FILE_PATH.toString());
        long end = System.currentTimeMillis();
        System.out.println("serializationExternalizable: " + (end - start));

        start = System.currentTimeMillis();
        List<AnimalExternalizable> animalsCopy = serializer.deserializeWithExternalizable(FILE_PATH.toString());
        end = System.currentTimeMillis();
        System.out.println("deserializationExternalizable: " + (end - start));
        System.out.println("File size: " + Files.size(FILE_PATH));

        assertEquals(animals.size(), animalsCopy.size());
        assertEquals(animals, animalsCopy);
    }

    @Test
    public void testCustomSerializableOfAnimals() throws IOException {
        List<Animal> animals = generateAnimals(200000);
        long start = System.currentTimeMillis();
        serializer.customSerialize(animals, FILE_PATH.toString());
        long end = System.currentTimeMillis();
        System.out.println("customSerialization: " + (end - start));

        start = System.currentTimeMillis();
        List<Animal> animalsCopy = serializer.customDeserialize(FILE_PATH.toString());
        end = System.currentTimeMillis();
        System.out.println("customDeserialization: " + (end - start));
        System.out.println("File size: " + Files.size(FILE_PATH));

        assertEquals(animals.size(), animalsCopy.size());
        assertEquals(animals, animalsCopy);
    }


    private List<Animal> generateAnimals(int size) {
        List<Animal> animals = new ArrayList<>();
        final int nullElems = (size + 1) / 100 * 5;
        IntStream.range(0, nullElems).forEach(i -> animals.add(null));
        IntStream.range(0, size - nullElems).forEach(i -> animals.add(generateAnimal()));
        Collections.shuffle(animals);
        return animals;
    }

    private List<AnimalWithMethods> generateAnimalsWithMethods(int size) {
        List<AnimalWithMethods> animals = new ArrayList<>();
        final int nullElems = (size + 1) / 100 * 5;
        IntStream.range(0, nullElems).forEach(i -> animals.add(null));
        IntStream.range(0, size - nullElems).forEach(i -> animals.add(generateAnimalWithMethods()));
        Collections.shuffle(animals);
        return animals;
    }

    private List<AnimalExternalizable> generateAnimalsExternalizable(int size) {
        List<AnimalExternalizable> animals = new ArrayList<>();
        final int nullElems = (size + 1) / 100 * 5;
        IntStream.range(0, nullElems).forEach(i -> animals.add(null));
        IntStream.range(0, size - nullElems).forEach(i -> animals.add(generateAnimalExternalizable()));
        Collections.shuffle(animals);
        return animals;
    }

    private Animal generateAnimal() {
        final List<String> animalNames = Stream.of("Luna", "Gerald", "Sharick", "Atos", "Gordon", "Vova")
                .collect(Collectors.toList());
        final List<String> ownerNames = Stream.of("Ivan", "Stepan", "Gena", "Pasha").collect(Collectors.toList());
        final List<String> ownerSurnames = Stream.of("Ivanov", "Sidorov", "Popov").collect(Collectors.toList());

        final Random rand = new Random();
        String name = animalNames.get(rand.nextInt(animalNames.size()));
        int age = rand.nextInt(25) + 10;
        int randomType = rand.nextInt(AnimalType.values().length);
        AnimalType type = AnimalType.values()[randomType];
        boolean isWild = (randomType >= 3);
        boolean isAquatic = (randomType >= 2 && randomType <= 5);
        LocalDate date = LocalDate.of(2022 - age, rand.nextInt(12) + 1, rand.nextInt(25) + 1);
        Animal animal = new Animal(name, age, isWild, isAquatic, type, date);
        if (!isWild) {
            animal.setOwner(new Owner(ownerNames.get(rand.nextInt(ownerNames.size())), ownerSurnames.get(rand.nextInt(ownerSurnames.size())),
                    rand.nextInt(50) + 19));
        }
        return animal;
    }

    private AnimalWithMethods generateAnimalWithMethods() {
        Animal animal = generateAnimal();
        AnimalWithMethods animalWithMethods = new AnimalWithMethods(animal.getName(), animal.getAge(), animal.isWild(),
                animal.isAquaticAnimal(), animal.getAnimalType(), animal.getDateOfBirth());
        if (!animal.isWild()) {
            animalWithMethods.setOwner(animal.getOwner());
        }
        return animalWithMethods;
    }

    private AnimalExternalizable generateAnimalExternalizable() {
        Animal animal = generateAnimal();
        AnimalExternalizable animalExternalizable = new AnimalExternalizable(animal.getName(), animal.getAge(), animal.isWild(),
                animal.isAquaticAnimal(), animal.getAnimalType(), animal.getDateOfBirth());
        if (!animal.isWild()) {
            animalExternalizable.setOwner(animal.getOwner());
        }
        return animalExternalizable;
    }
}
