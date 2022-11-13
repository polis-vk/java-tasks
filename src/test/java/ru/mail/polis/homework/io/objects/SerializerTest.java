package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final int ANIMAL_SIZE = 1000000;
    private static final Path fileDefault = Paths.get("src", "test", "resources", "test_default.txt");
    private static final Path fileWithMethods = Paths.get("src", "test", "resources", "test_with_methods.txt");
    private static final Path fileExternalizable = Paths.get("src", "test", "resources", "test_externalizable.txt");
    private static final Path fileCustom = Paths.get("src", "test", "resources", "test_custom.txt");
    private static final String[] nurseryAddress = new String[]{null, "0", "1", "2", "3", "4", "5", "6"};
    private static final String[] animalNames = new String[]{null, "Vasya", "Petya", "Kolya", "Zubor", "Rex", "Cake"};
    private static final Serializer serializer = new Serializer();
    private static final List<Animal> animal = new ArrayList<>();
    private static final List<AnimalWithMethods> animalWithMethods = new ArrayList<>();
    private static final List<AnimalExternalizable> animalExternalizable = new ArrayList<>();

    @BeforeClass
    public static void createData() throws IOException {
        createDirectories();
        for (int i = 0; i < ANIMAL_SIZE; i++) {
            createAnimal();
            createAnimalWithMethods();
            createAnimalExternalizable();
        }
    }

    @AfterClass
    public static void cleanTempFile() throws IOException {
        Files.deleteIfExists(fileDefault);
        Files.deleteIfExists(fileExternalizable);
        Files.deleteIfExists(fileWithMethods);
        Files.deleteIfExists(fileCustom);
    }

    @Test
    public void testDefaultSerialize() throws IOException {
        long startTimer = System.currentTimeMillis();
        serializer.defaultSerialize(animal, fileDefault.toString());
        long DefaultSerializeTime = System.currentTimeMillis() - startTimer;

        startTimer = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(fileDefault.toString());
        long DefaultDeserializeTime = System.currentTimeMillis() - startTimer;

        assertEquals(animal, deserializedAnimals);

        System.out.println("Serialization | Deserialization Default: \n" + "Size: " + Files.size(fileDefault) / 1024 + " Kb.");
        System.out.println("Time: " + DefaultSerializeTime + " | " + DefaultDeserializeTime + " ms.\n");
    }

    @Test
    public void testSerializeWithMethods() throws IOException {
        long startTimer = System.currentTimeMillis();
        serializer.serializeWithMethods(animalWithMethods, fileWithMethods.toString());
        long SerializeWithMethodsTime = System.currentTimeMillis() - startTimer;

        startTimer = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods(fileWithMethods.toString());
        long DeserializeWithMethodsTime = System.currentTimeMillis() - startTimer;

        assertEquals(animalWithMethods, deserializedAnimals);

        System.out.println("Serialization | Deserialization with Methods: \n" + "Size: " + Files.size(fileWithMethods) / 1024 + " Kb.");
        System.out.println("Time: " + SerializeWithMethodsTime + " | " + DeserializeWithMethodsTime + " ms.\n");
    }

    @Test
    public void testSerializeWithExternalizable() throws IOException {
        long startTimer = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalExternalizable, fileExternalizable.toString());
        long SerializeWithExternalizableTime = System.currentTimeMillis() - startTimer;

        startTimer = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable(fileExternalizable.toString());
        long DeserializeWithExternalizableTime = System.currentTimeMillis() - startTimer;

        assertEquals(animalExternalizable, deserializedAnimals);

        System.out.println("Serialization | Deserialization with Externalizable: \n" + "Size: " + Files.size(fileExternalizable) / 1024 + " Kb.");
        System.out.println("Time: " + SerializeWithExternalizableTime + " | " + DeserializeWithExternalizableTime + " ms.\n");
    }

    @Test
    public void testCustomSerialize() throws IOException {
        long startTimer = System.currentTimeMillis();
        serializer.customSerialize(animal, fileCustom.toString());
        long CustomSerializeTime = System.currentTimeMillis() - startTimer;

        startTimer = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.customDeserialize(fileCustom.toString());
        long CustomDeserializeTime = System.currentTimeMillis() - startTimer;

        assertEquals(animal, deserializedAnimals);

        System.out.println("Serialization | Deserialization with Custom: \n" + "Size: " + Files.size(fileCustom) / 1024 + " Kb.");
        System.out.println("Time: " + CustomSerializeTime + " | " + CustomDeserializeTime + " ms.\n");
    }

    private static String getNurseryAddress() {
        int index = ThreadLocalRandom.current().nextInt(nurseryAddress.length);
        return nurseryAddress[index];
    }

    private static String getAnimalName() {
        int index = ThreadLocalRandom.current().nextInt(animalNames.length);
        return animalNames[index];
    }

    private static boolean getIsWork() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static boolean getIsSeek() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static boolean getInNursery() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static int getAnimalAge() {
        return ThreadLocalRandom.current().nextInt(100);
    }

    private static AnimalType getAnimalType() {
        int index = ThreadLocalRandom.current().nextInt(6);
        switch (index) {
            case 0:
                return AnimalType.CAT;
            case 1:
                return AnimalType.DOG;
            case 2:
                return AnimalType.BIRD;
            case 3:
                return AnimalType.CROC;
            case 4:
                return AnimalType.FERRET;
            default:
                return AnimalType.NONE;
        }
    }

    private static AnimalNursery getAnimalNursery() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }
        return new AnimalNursery(getNurseryAddress(), getIsWork());
    }

    private static AnimalNurseryWithMethods getAnimalNurseryWithMethods() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }
        return new AnimalNurseryWithMethods(getNurseryAddress(), getIsWork());
    }

    private static AnimalNurseryExternalizable getAnimalNurseryExternalizable() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }
        return new AnimalNurseryExternalizable(getNurseryAddress(), getIsWork());
    }

    private static void createAnimal() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            animal.add(new Animal(
                    getAnimalNursery(),
                    getAnimalType(),
                    getAnimalName(),
                    getAnimalAge(),
                    getIsSeek(),
                    getInNursery()));
        }
        animal.add(null);
    }

    private static void createAnimalWithMethods() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            animalWithMethods.add(new AnimalWithMethods(
                    getAnimalNurseryWithMethods(),
                    getAnimalType(),
                    getAnimalName(),
                    getAnimalAge(),
                    getIsSeek(),
                    getInNursery()));
        }
        animalWithMethods.add(null);
    }

    private static void createAnimalExternalizable() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            animalExternalizable.add(new AnimalExternalizable(
                    getAnimalNurseryExternalizable(),
                    getAnimalType(),
                    getAnimalName(),
                    getAnimalAge(),
                    getIsSeek(),
                    getInNursery()));
        }
        animalExternalizable.add(null);
    }

    private static void createDirectories() throws IOException {
        if (Files.notExists(fileDefault.getParent())) {
            Files.createDirectories(fileDefault.getParent());
        }
        if (Files.notExists(fileWithMethods.getParent())) {
            Files.createDirectories(fileWithMethods.getParent());
        }
        if (Files.notExists(fileExternalizable.getParent())) {
            Files.createDirectories(fileExternalizable.getParent());
        }
        if (Files.notExists(fileCustom.getParent())) {
            Files.createDirectories(fileCustom.getParent());
        }
    }


}
