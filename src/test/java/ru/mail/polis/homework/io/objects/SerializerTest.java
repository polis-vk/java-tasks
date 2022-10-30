package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class SerializerTest {
    private static List<Animal> animalList;
    private static List<AnimalWithMethods> animalWithMethodsList;
    private static List<AnimalExternalizable> animalExternalizableList;

    @BeforeClass
    public static void generateLists() {
        animalList = new LinkedList<>();
        animalWithMethodsList = new LinkedList<>();
        animalExternalizableList = new LinkedList<>();
        for (int i = 0; i < 100_000; i++) {
            animalList.add(generateRandomAnimal());
            animalWithMethodsList.add(generateRandomAnimalWithMethods());
            animalExternalizableList.add(generateRandomAnimalExternalizable());
        }
    }

    @Test
    public void warmUp() throws IOException, ClassNotFoundException {
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

        Serializer serializer = new Serializer();
        serializer.customSerialize(animalList, "custom.bin");

        System.out.println(System.currentTimeMillis() - lastCheckTime);
        System.out.println(Files.size(Paths.get("custom.bin")));
        lastCheckTime = System.currentTimeMillis();

        List<Animal> test = serializer.customDeserialize("custom.bin");
        assert test.equals(animalList);

        System.out.println(System.currentTimeMillis() - lastCheckTime);
    }

    @Test
    public void defaultSerializeTest() throws IOException, ClassNotFoundException {
        long lastCheckTime = System.currentTimeMillis();
        System.out.println("defaultSerializeTest");
        Serializer serializer = new Serializer();
        serializer.defaultSerialize(animalList, "default.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        System.out.println(Files.size(Paths.get("default.bin")));
        lastCheckTime = System.currentTimeMillis();
        List<Animal> test = serializer.defaultDeserialize("default.bin");
        assert test.equals(animalList);
        System.out.println(System.currentTimeMillis() - lastCheckTime);
    }

    @Test
    public void serializeWithMethodsTest() throws IOException, ClassNotFoundException {
        long lastCheckTime = System.currentTimeMillis();
        System.out.println("serializeWithMethodsTest");
        Serializer serializer = new Serializer();
        serializer.serializeWithMethods(animalWithMethodsList, "methods.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        System.out.println(Files.size(Paths.get("methods.bin")));
        lastCheckTime = System.currentTimeMillis();
        List<AnimalWithMethods> test = serializer.deserializeWithMethods("methods.bin");
        assert test.equals(animalWithMethodsList);
        System.out.println(System.currentTimeMillis() - lastCheckTime);
    }

    @Test
    public void serializeExternalizableTest() throws IOException, ClassNotFoundException {
        long lastCheckTime = System.currentTimeMillis();
        System.out.println("serializeExternalizableTest");
        Serializer serializer = new Serializer();
        serializer.serializeWithExternalizable(animalExternalizableList, "externalizable.bin");
        System.out.println(System.currentTimeMillis() - lastCheckTime);
        System.out.println(Files.size(Paths.get("externalizable.bin")));
        lastCheckTime = System.currentTimeMillis();
        List<AnimalExternalizable> test = serializer.deserializeWithExternalizable("externalizable.bin");
        assert test.equals(animalExternalizableList);
        System.out.println(System.currentTimeMillis() - lastCheckTime);
    }





    private static Animal generateRandomAnimal() {
        Random random = new Random();
        return new Animal(
                random.nextBoolean(),
                random.nextBoolean(),
                Math.abs(random.nextInt(10)),
                Math.abs(random.nextDouble()),
                generateRandomString(random),
                TypeOfAnimal.values()[random.nextInt(5)],
                new OwnerOfAnimal(
                        generateRandomString(random),
                        generateRandomString(random),
                        Gender.values()[random.nextInt(3)],
                        new Address(
                                generateRandomString(random),
                                generateRandomString(random),
                                generateRandomString(random),
                                random.nextInt(100),
                                random.nextInt(100),
                                String.valueOf((char) (random.nextInt(33) + 'a')),
                                random.nextInt(1000)
                        )
                )
        );
    }

    private static AnimalExternalizable generateRandomAnimalExternalizable() {
        Random random = new Random();
        return new AnimalExternalizable(
                random.nextBoolean(),
                random.nextBoolean(),
                Math.abs(random.nextInt(10)),
                Math.abs(random.nextDouble()),
                generateRandomString(random),
                TypeOfAnimal.values()[random.nextInt(5)],
                new OwnerOfAnimalExternalizable(
                        generateRandomString(random),
                        generateRandomString(random),
                        Gender.values()[random.nextInt(3)],
                        new AddressExternalizable(
                                generateRandomString(random),
                                generateRandomString(random),
                                generateRandomString(random),
                                random.nextInt(100),
                                random.nextInt(100),
                                String.valueOf((char) (random.nextInt(33) + 'a')),
                                random.nextInt(1000)
                        )
                )
        );
    }

    private static AnimalWithMethods generateRandomAnimalWithMethods() {
        Random random = new Random();
        return new AnimalWithMethods(
                random.nextBoolean(),
                random.nextBoolean(),
                Math.abs(random.nextInt(10)),
                Math.abs(random.nextDouble()),
                generateRandomString(random),
                TypeOfAnimal.values()[random.nextInt(5)],
                new OwnerOfAnimalWithMethods(
                        generateRandomString(random),
                        generateRandomString(random),
                        Gender.values()[random.nextInt(3)],
                        new AddressWithMethods(
                                generateRandomString(random),
                                generateRandomString(random),
                                generateRandomString(random),
                                random.nextInt(100),
                                random.nextInt(100),
                                String.valueOf((char) (random.nextInt(33) + 'a')),
                                random.nextInt(1000)
                        )
                )
        );
    }

    private static String generateRandomString(Random random) {
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
