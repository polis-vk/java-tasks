package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.JVM)
public class SerializerTest {
    private static final double NULL_PERCENTAGE = 0.05;
    private static final int NUM_OF_ANIMALS = 1_000_000;
    private static List<Animal> animalList;
    private static List<AnimalWithMethods> animalWithMethodsList;
    private static List<AnimalExternalizable> animalExternalizableList;
    private static Serializer serializer;

    @BeforeClass
    public static void generateLists() {
        serializer = new Serializer();
        animalList = new ArrayList<>(NUM_OF_ANIMALS);
        animalWithMethodsList = new ArrayList<>(NUM_OF_ANIMALS);
        animalExternalizableList = new ArrayList<>(NUM_OF_ANIMALS);
        Random random = new Random();
        for (int i = 0; i < NUM_OF_ANIMALS; i++) {
            animalList.add(generateRandomAnimal(random));
            animalWithMethodsList.add(generateRandomAnimalWithMethods(random));
            animalExternalizableList.add(generateRandomAnimalExternalizable(random));
        }
    }

    @Test
    public void warmUp() throws IOException {
        for (int i = 0; i < 3; i++) {
            System.out.println(i + " итерация прогрева");
            customSerializeTest();
            defaultSerializeTest();
            serializeExternalizableTest();
            serializeWithMethodsTest();
        }
    }

    @Test
    public void customSerializeTest() throws IOException {
        long lastCheckTime = System.currentTimeMillis();
        System.out.println("customSerializeTest");

        serializer.customSerialize(animalList, "custom.bin");

        System.out.println(System.currentTimeMillis() - lastCheckTime);
        System.out.println(Files.size(Paths.get("custom.bin")));
        lastCheckTime = System.currentTimeMillis();

        List<Animal> test = serializer.customDeserialize("custom.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        assertEquals(test.size(), animalList.size());
        Iterator<Animal> testIt = test.iterator();
        Iterator<Animal> animalIterator = animalList.iterator();
        while (testIt.hasNext()) {
            assertEquals(testIt.next(), animalIterator.next());
        }

    }

    @Test
    public void defaultSerializeTest() throws IOException {
        long lastCheckTime = System.currentTimeMillis();
        System.out.println("defaultSerializeTest");
        serializer.defaultSerialize(animalList, "default.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        System.out.println(Files.size(Paths.get("default.bin")));
        lastCheckTime = System.currentTimeMillis();
        List<Animal> test = serializer.defaultDeserialize("default.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        assertEquals(test.size(), animalExternalizableList.size());
        Iterator<Animal> testIt = test.iterator();
        Iterator<Animal> animalIterator = animalList.iterator();
        while (testIt.hasNext()) {
            assertEquals(testIt.next(), animalIterator.next());
        }
    }

    @Test
    public void serializeWithMethodsTest() throws IOException {
        long lastCheckTime = System.currentTimeMillis();
        System.out.println("serializeWithMethodsTest");
        serializer.serializeWithMethods(animalWithMethodsList, "methods.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        System.out.println(Files.size(Paths.get("methods.bin")));
        lastCheckTime = System.currentTimeMillis();
        List<AnimalWithMethods> test = serializer.deserializeWithMethods("methods.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        assertEquals(test.size(), animalWithMethodsList.size());
        Iterator<AnimalWithMethods> testIt = test.iterator();
        Iterator<AnimalWithMethods> animalWithMethodsIterator = animalWithMethodsList.iterator();
        while (testIt.hasNext()) {
            assertEquals(testIt.next(), animalWithMethodsIterator.next());
        }
    }

    @Test
    public void serializeExternalizableTest() throws IOException {
        long lastCheckTime = System.currentTimeMillis();
        System.out.println("serializeExternalizableTest");
        serializer.serializeWithExternalizable(animalExternalizableList, "externalizable.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        System.out.println(Files.size(Paths.get("externalizable.bin")));
        lastCheckTime = System.currentTimeMillis();
        List<AnimalExternalizable> test = serializer.deserializeWithExternalizable("externalizable.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        assertEquals(test.size(), animalExternalizableList.size());
        Iterator<AnimalExternalizable> testIt = test.iterator();
        Iterator<AnimalExternalizable> animalExternalizableIt = animalExternalizableList.iterator();
        while (testIt.hasNext()) {
            assertEquals(testIt.next(), animalExternalizableIt.next());
        }
    }


    private static Animal generateRandomAnimal(Random random) {
        if (random.nextDouble() < NULL_PERCENTAGE) {
            return null;
        }
        return new Animal(
                random.nextBoolean(),
                random.nextBoolean(),
                Math.abs(random.nextInt(10)),
                Math.abs(random.nextDouble()),
                generateRandomString(random),
                random.nextDouble() < NULL_PERCENTAGE ?
                        null :
                        TypeOfAnimal.values()[random.nextInt(5)],
                random.nextDouble() < NULL_PERCENTAGE ?
                        null :
                        new OwnerOfAnimal(
                                generateRandomString(random),
                                generateRandomString(random),
                                random.nextDouble() < NULL_PERCENTAGE ?
                                        null :
                                        Gender.values()[random.nextInt(3)],
                                random.nextDouble() < NULL_PERCENTAGE ?
                                        null :
                                        new Address(
                                                generateRandomString(random),
                                                generateRandomString(random),
                                                generateRandomString(random),
                                                random.nextInt(100),
                                                random.nextInt(100),
                                                random.nextDouble() < NULL_PERCENTAGE ?
                                                        null :
                                                        String.valueOf((char) (random.nextInt(33) + 'a')),
                                                random.nextInt(1000)
                                        )
                        )
        );
    }

    private static AnimalExternalizable generateRandomAnimalExternalizable(Random random) {
        if (random.nextDouble() < NULL_PERCENTAGE) {
            return null;
        }
        return new AnimalExternalizable(
                random.nextBoolean(),
                random.nextBoolean(),
                Math.abs(random.nextInt(10)),
                Math.abs(random.nextDouble()),
                generateRandomString(random),
                random.nextDouble() < NULL_PERCENTAGE ?
                        null :
                        TypeOfAnimal.values()[random.nextInt(5)],
                random.nextDouble() < NULL_PERCENTAGE ?
                        null :
                        new OwnerOfAnimalExternalizable(
                                generateRandomString(random),
                                generateRandomString(random),
                                random.nextDouble() < NULL_PERCENTAGE ?
                                        null :
                                        Gender.values()[random.nextInt(3)],
                                random.nextDouble() < NULL_PERCENTAGE ?
                                        null :
                                        new AddressExternalizable(
                                                generateRandomString(random),
                                                generateRandomString(random),
                                                generateRandomString(random),
                                                random.nextInt(100),
                                                random.nextInt(100),
                                                random.nextDouble() < NULL_PERCENTAGE ?
                                                        null :
                                                        String.valueOf((char) (random.nextInt(33) + 'a')),
                                                random.nextInt(1000)
                                        )
                        )
        );
    }

    private static AnimalWithMethods generateRandomAnimalWithMethods(Random random) {
        if (random.nextDouble() < NULL_PERCENTAGE) {
            return null;
        }
        return new AnimalWithMethods(
                random.nextBoolean(),
                random.nextBoolean(),
                Math.abs(random.nextInt(10)),
                Math.abs(random.nextDouble()),
                generateRandomString(random),
                random.nextDouble() < NULL_PERCENTAGE ?
                        null :
                        TypeOfAnimal.values()[random.nextInt(5)],
                random.nextDouble() < NULL_PERCENTAGE ?
                        null :
                        new OwnerOfAnimalWithMethods(
                                generateRandomString(random),
                                generateRandomString(random),
                                random.nextDouble() < NULL_PERCENTAGE ?
                                        null :
                                        Gender.values()[random.nextInt(3)],
                                random.nextDouble() < NULL_PERCENTAGE ?
                                        null :
                                        new AddressWithMethods(
                                                generateRandomString(random),
                                                generateRandomString(random),
                                                generateRandomString(random),
                                                random.nextInt(100),
                                                random.nextInt(100),
                                                random.nextDouble() < NULL_PERCENTAGE ?
                                                        null :
                                                        String.valueOf((char) (random.nextInt(33) + 'a')),
                                                random.nextInt(1000)
                                        )
                        )
        );
    }

    private static String generateRandomString(Random random) {
        if (random.nextDouble() < NULL_PERCENTAGE) {
            return null;
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < random.nextInt(16) + 1; i++) {
            ans.append((char) (random.nextInt(33) + 'а'));
        }
        return ans.toString();
    }


    @After
    public void deleteFiles() throws IOException {
        Files.deleteIfExists(Paths.get("default.bin"));
        Files.deleteIfExists(Paths.get("methods.bin"));
        Files.deleteIfExists(Paths.get("externalizable.bin"));
        Files.deleteIfExists(Paths.get("custom.bin"));
    }

}
