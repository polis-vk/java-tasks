package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.mail.polis.homework.oop.vet.MoveType;

import static org.junit.Assert.assertEquals;


public class SerializerTest {
    private static final Random rnd = new Random();
    private static final int SIZE_OF_ANIMALS_LIST = 200000;
    private static final int MAX_COUNT_LEGS = 4;
    private static final List<Animal> animals = generateAnimalsList(SerializerTest::generateAnimal);
    private static final List<AnimalWithMethods> animalsWithMethods = generateAnimalsList(SerializerTest::generateAnimalWithMethods);
    private static final List<AnimalExternalizable> animalsExternalizable = generateAnimalsList(SerializerTest::generateAnimalExternalizable);
    private static final Serializer structureSerializer = new Serializer();

    @Before
    public void setUp() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "serializer");
        Files.createDirectories(dir);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "serializer", "animals.bin").toFile());
    }

    @Test
    public void testDefaultSerialize() throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animals.bin");
        Files.createFile(file);

        System.out.println("testDefaultSerialize");
        long start = System.currentTimeMillis();
        structureSerializer.defaultSerialize(animals, file.toString());
        long finish = System.currentTimeMillis();
        System.out.println("Time to write list of " + SIZE_OF_ANIMALS_LIST + " " + animals.getClass().toString() + " = " + (finish - start) + " ms");

        start = System.currentTimeMillis();
        List<Animal> readAnimals = structureSerializer.defaultDeserialize(file.toString());
        finish = System.currentTimeMillis();
        System.out.println("Time to read file = " + (finish - start) + " ms");

        assertEquals(animals, readAnimals);

        System.out.println("Size of file = " + Files.size(file) + " bytes");
    }

    @Test
    public void testSerializeWithMethods() throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animals.bin");
        Files.createFile(file);

        System.out.println("testSerializeWithMethods");
        long start = System.currentTimeMillis();
        structureSerializer.serializeWithMethods(animalsWithMethods, file.toString());
        long finish = System.currentTimeMillis();
        System.out.println("Time to write list of " + SIZE_OF_ANIMALS_LIST + " " + animalsWithMethods.getClass().toString() + " = " + (finish - start) + " ms");

        start = System.currentTimeMillis();
        List<AnimalWithMethods> readAnimals = structureSerializer.deserializeWithMethods(file.toString());
        finish = System.currentTimeMillis();
        System.out.println("Time to read file = " + (finish - start) + " ms");

        assertEquals(animalsWithMethods, readAnimals);

        System.out.println("Size of file = " + Files.size(file) + " bytes");
    }

    @Test
    public void testSerializeExternalizable() throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animals.bin");
        Files.createFile(file);

        System.out.println("testSerializeExternalizable");
        long start = System.currentTimeMillis();
        structureSerializer.serializeWithExternalizable(animalsExternalizable, file.toString());
        long finish = System.currentTimeMillis();
        System.out.println("Time to write list of " + SIZE_OF_ANIMALS_LIST + " " + animalsExternalizable.getClass().toString() + " = " + (finish - start) + " ms");

        start = System.currentTimeMillis();
        List<AnimalExternalizable> readAnimals = structureSerializer.deserializeWithExternalizable(file.toString());
        finish = System.currentTimeMillis();
        System.out.println("Time to read file = " + (finish - start) + " ms");

        assertEquals(animalsExternalizable, readAnimals);

        System.out.println("Size of file = " + Files.size(file) + " bytes");
    }

    @Test
    public void testCustomSerialize() throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animals.bin");
        Files.createFile(file);

        System.out.println("testCustomSerialize");
        long start = System.currentTimeMillis();
        structureSerializer.customSerialize(animals, file.toString());
        long finish = System.currentTimeMillis();
        System.out.println("Time to write list of " + SIZE_OF_ANIMALS_LIST + " " + animals.getClass().toString() + " = " + (finish - start) + " ms");

        start = System.currentTimeMillis();
        List<Animal> readAnimals = structureSerializer.customDeserialize(file.toString());
        finish = System.currentTimeMillis();
        System.out.println("Time to read file = " + (finish - start) + " ms");

        assertEquals(animals, readAnimals);

        System.out.println("Size of file = " + Files.size(file) + " bytes");
    }

    private static Animal generateAnimal() {
        if (rnd.nextInt() % (SIZE_OF_ANIMALS_LIST / 1000) == 1) {
            return null;
        }
        Animal animal = new Animal();
        animal.setCountLegs(rnd.nextInt(MAX_COUNT_LEGS));
        animal.setName(generateString());
        animal.setFly(rnd.nextBoolean());
        animal.setPet(rnd.nextBoolean());
        animal.setMoveType(MoveType.values()[rnd.nextInt(MoveType.values().length)]);
        if (rnd.nextInt() % (SIZE_OF_ANIMALS_LIST / 1000) == 1) {
            animal.setPopulation(null);
        } else {
            animal.setPopulation(new Population(Mainland.values()[rnd.nextInt(Mainland.values().length)], rnd.nextLong()));
        }
        return animal;
    }

    private static AnimalWithMethods generateAnimalWithMethods() {
        AnimalWithMethods animal = new AnimalWithMethods();
        animal.setCountLegs(rnd.nextInt(MAX_COUNT_LEGS));
        animal.setName(generateString());
        animal.setFly(rnd.nextBoolean());
        animal.setPet(rnd.nextBoolean());
        animal.setMoveType(MoveType.values()[rnd.nextInt(MoveType.values().length)]);
        animal.setPopulation(new Population(Mainland.values()[rnd.nextInt(Mainland.values().length)], rnd.nextLong()));
        return animal;
    }

    private static AnimalExternalizable generateAnimalExternalizable() {
        AnimalExternalizable animal = new AnimalExternalizable();
        animal.setCountLegs(rnd.nextInt(MAX_COUNT_LEGS));
        animal.setName(generateString());
        animal.setFly(rnd.nextBoolean());
        animal.setPet(rnd.nextBoolean());
        animal.setMoveType(MoveType.values()[rnd.nextInt(MoveType.values().length)]);
        animal.setPopulation(new Population(Mainland.values()[rnd.nextInt(Mainland.values().length)], rnd.nextLong()));
        return animal;
    }

    private static String generateString() {
        if (rnd.nextInt() % (SIZE_OF_ANIMALS_LIST / 1000) == 1) {
            return null;
        }
        char[] chars = new char[rnd.nextInt(18) + 2];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (rnd.nextInt('z' - '0') + '0');
        }
        return new String(chars);
    }

    private static <T> List<T> generateAnimalsList(Supplier<T> generator) {
        List<T> animals = new ArrayList<>();
        for (int i = 0; i < SIZE_OF_ANIMALS_LIST; i++) {
            animals.add(generator.get());
        }
        return animals;
    }
}