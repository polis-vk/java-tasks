package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SerializerTest {

    private static final Path FILE_PATH = Paths.get("src", "test", "resources", "object");
    private final static String fileName = "./src/test/resources/object/serializeTest.bin";
    private static final Serializer SERIALIZER = new Serializer();
    private static final Random RANDOM = new Random();
    private static final int NUMBER_OF_OBJECT = 1_000_000;

    @Before
    public void setUp() throws Exception {
        Files.createDirectories(FILE_PATH);
        Files.createFile(Paths.get(fileName));
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(fileName));
    }

    @Test
    public void testDefaultSerialize() throws IOException {
        List<Animal> animalList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OBJECT; i++) {
            Animal animal = RandomAnimal.getAnimal();
            animalList.add(animal);
        }
        long startInput = System.currentTimeMillis();
        SERIALIZER.defaultSerialize(animalList, fileName);
        long finishInput = System.currentTimeMillis();

        long startOutput = System.currentTimeMillis();
        SERIALIZER.defaultDeserialize(fileName);
        long finishOutput = System.currentTimeMillis();

        printResultTest("\nDefault serialize: ", finishInput - startInput,
                finishOutput - startOutput, Files.size(Paths.get(fileName)));
    }

    @Test
    public void testCustomSerialize() throws IOException {
        List<Animal> animalList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OBJECT; i++) {
            Animal animal = RandomAnimal.getAnimal();
            animalList.add(animal);
        }
        long startInput = System.currentTimeMillis();
        SERIALIZER.customSerialize(animalList, fileName);
        long finishInput = System.currentTimeMillis();

        long startOutput = System.currentTimeMillis();
        SERIALIZER.customDeserialize(fileName);
        long finishOutput = System.currentTimeMillis();

        printResultTest("\nCustom serialize: ", finishInput - startInput,
                finishOutput - startOutput, Files.size(Paths.get(fileName)));
    }

    @Test
    public void testSerializeWithExternalize() throws IOException {
        List<AnimalExternalizable> animalExternalizeList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OBJECT; i++) {
            AnimalExternalizable animal = RandomAnimal.getAnimalExternalize();
            animalExternalizeList.add(animal);
        }
        long startInput = System.currentTimeMillis();
        SERIALIZER.serializeWithExternalizable(animalExternalizeList, fileName);
        long finishInput = System.currentTimeMillis();

        long startOutput = System.currentTimeMillis();
        SERIALIZER.deserializeWithExternalizable(fileName);
        long finishOutput = System.currentTimeMillis();

        printResultTest("\nSerialize with externalize: ", finishInput - startInput,
                finishOutput - startOutput, Files.size(Paths.get(fileName)));
    }

    @Test
    public void testSerializeWithMethods() throws IOException {
        List<AnimalWithMethods> animalWithMethodsList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OBJECT; i++) {
            AnimalWithMethods animal = RandomAnimal.getAnimalWithMethods();
            animalWithMethodsList.add(animal);
        }
        long startInput = System.currentTimeMillis();
        SERIALIZER.serializeWithMethods(animalWithMethodsList, fileName);
        long finishInput = System.currentTimeMillis();

        long startOutput = System.currentTimeMillis();
        SERIALIZER.deserializeWithMethods(fileName);
        long finishOutput = System.currentTimeMillis();

        printResultTest("\nSerialize with methods: ", finishInput - startInput,
                finishOutput - startOutput, Files.size(Paths.get(fileName)));
    }

    private static class RandomAnimal {
        private static final int MIN_NAME_LENGTH = 3;
        private static final int MAX_NAME_LENGTH = 10;
        private static final int MAX_AGE = 100;
        private static final int ANIMAL_COUNT = AnimalType.values().length;

        public static Animal getAnimal() {
            return new Animal(RandomAnimal.getName(), RandomAnimal.getAge(),
                    RandomAnimal.getIsFriendly(), RandomAnimal.getIsWarmBlooded(),
                    RandomAnimal.getAnimalType(), RandomAnimal.getPopulation());
        }

        public static AnimalExternalizable getAnimalExternalize() {
            return new AnimalExternalizable(RandomAnimal.getName(), RandomAnimal.getAge(),
                    RandomAnimal.getIsFriendly(), RandomAnimal.getIsWarmBlooded(),
                    RandomAnimal.getAnimalType(), RandomAnimal.getPopulation());
        }

        public static AnimalWithMethods getAnimalWithMethods() {
            return new AnimalWithMethods(RandomAnimal.getName(), RandomAnimal.getAge(),
                    RandomAnimal.getIsFriendly(), RandomAnimal.getIsWarmBlooded(),
                    RandomAnimal.getAnimalType(), RandomAnimal.getPopulation());
        }

        private static String getName() {
            char[] name = new char[RANDOM.nextInt(MAX_NAME_LENGTH - MIN_NAME_LENGTH) + MIN_NAME_LENGTH];
            name[0] = (char) (RANDOM.nextInt(26) + 'A');
            for (int i = 1; i < name.length; i++) {
                name[i] = (char) (RANDOM.nextInt(26) + 'a');
            }
            return new String(name);
        }

        private static int getAge() {
            return RANDOM.nextInt(MAX_AGE);
        }

        private static boolean getIsFriendly() {
            return RANDOM.nextBoolean();
        }

        private static boolean getIsWarmBlooded() {
            return RANDOM.nextBoolean();
        }

        private static AnimalType getAnimalType() {
            return AnimalType.values()[RANDOM.nextInt(ANIMAL_COUNT)];
        }

        private static Population getPopulation() {
            return new Population(getName(), RANDOM.nextInt(Integer.MAX_VALUE) + 1, RANDOM.nextInt(Integer.MAX_VALUE) + 1);
        }
    }

    private void printResultTest(String testName, long serializeTime, long deserializeTime, long size) {
        System.out.println(
                testName + "\n" +
                        "Serialization time: " + serializeTime + " ms\n" +
                        "Deserialization time: " + deserializeTime + " ms\n" +
                        "File size: " + size + " bytes\n"
        );
    }
}