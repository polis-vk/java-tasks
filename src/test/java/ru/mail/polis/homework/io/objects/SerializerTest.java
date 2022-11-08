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

    private static final Path DIRECTORY  = Paths.get("src", "test", "resources", "objects");
    private final static Path FILE_PATH = Paths.get("src", "test", "resources", "objects", "serializeTest.bin");
    private static final int NUMBER_OBJECTS = 400000;
    private static final Random RANDOM = new Random();
    private static final Serializer SERIALIZER = new Serializer();

    @Before
    public void setUp() throws Exception {
        Files.createDirectories(DIRECTORY);
        Files.createFile(FILE_PATH);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(FILE_PATH);
        Files.delete(DIRECTORY);
        Files.delete(Paths.get("src", "test", "resources"));
    }

    @Test
    public void defaultSerialize() throws IOException {
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < NUMBER_OBJECTS; i++) {
            animals.add(getRandomDefaultAnimal());
        }

        long startSerialize = System.currentTimeMillis();
        SERIALIZER.defaultSerialize(animals, FILE_PATH.toString());
        long stopSerialize = System.currentTimeMillis();

        long startDeserialize = System.currentTimeMillis();
        SERIALIZER.defaultDeserialize(FILE_PATH.toString());
        long stopDeserialize = System.currentTimeMillis();

        getResultTest("Test Default Serialize", Files.size(FILE_PATH),
                stopSerialize -  startSerialize,
                stopDeserialize - startDeserialize);
    }

    @Test
    public void serializeWithMethods() throws IOException {
        List<AnimalWithMethods> animals = new ArrayList<>();
        for (int i = 0; i < NUMBER_OBJECTS; i++) {
            animals.add(getRandomWithMethodsAnimal());
        }

        long startSerialize = System.currentTimeMillis();
        SERIALIZER.serializeWithMethods(animals, FILE_PATH.toString());
        long stopSerialize = System.currentTimeMillis();

        long startDeserialize = System.currentTimeMillis();
        SERIALIZER.deserializeWithMethods(FILE_PATH.toString());
        long stopDeserialize = System.currentTimeMillis();

        getResultTest("Test Serialize With Methods: ", Files.size(FILE_PATH),
                stopSerialize -  startSerialize,
                stopDeserialize - startDeserialize);
    }

    @Test
    public void serializeWithExternalizable() throws IOException {
        List<AnimalExternalizable> animals = new ArrayList<>();
        for (int i = 0; i < NUMBER_OBJECTS; i++) {
            animals.add(getRandomWithExternalizableAnimal());
        }

        long startSerialize = System.currentTimeMillis();
        SERIALIZER.serializeWithExternalizable(animals, FILE_PATH.toString());
        long stopSerialize = System.currentTimeMillis();

        long startDeserialize = System.currentTimeMillis();
        SERIALIZER.deserializeWithExternalizable(FILE_PATH.toString());
        long stopDeserialize = System.currentTimeMillis();

        getResultTest("Test Serialize With Externalizable: ", Files.size(FILE_PATH),
                stopSerialize -  startSerialize,
                stopDeserialize - startDeserialize);
    }

    @Test
    public void customSerialize() throws IOException {
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < NUMBER_OBJECTS; i++) {
            animals.add(getRandomDefaultAnimal());
        }

        long startSerialize = System.currentTimeMillis();
        SERIALIZER.customSerialize(animals, FILE_PATH.toString());
        long stopSerialize = System.currentTimeMillis();

        long startDeserialize = System.currentTimeMillis();
        SERIALIZER.customDeserialize(FILE_PATH.toString());
        long stopDeserialize = System.currentTimeMillis();

        getResultTest("Test Custom Serialize: ", Files.size(FILE_PATH),
                stopSerialize -  startSerialize,
                stopDeserialize - startDeserialize);
    }

    private Animal getRandomDefaultAnimal() {
        Animal animal = new Animal();
        animal.setSay(getRandomString());
        animal.setLegs(getRandomInt());
        animal.setIsWild(getRandomBoolean());
        animal.setIsHerbivorous(getRandomBoolean());
        animal.setGender(getRandomGender());
        animal.setClassification(getRandomСlassification());
        return animal;
    }

    private AnimalWithMethods getRandomWithMethodsAnimal() {
        AnimalWithMethods animal = new AnimalWithMethods();
        animal.setSay(getRandomString());
        animal.setLegs(getRandomInt());
        animal.setIsWild(getRandomBoolean());
        animal.setIsHerbivorous(getRandomBoolean());
        animal.setGender(getRandomGender());
        animal.setClassification(getRandomСlassification());
        return animal;
    }

    private AnimalExternalizable getRandomWithExternalizableAnimal() {
        AnimalExternalizable animal = new AnimalExternalizable();
        animal.setSay(getRandomString());
        animal.setLegs(getRandomInt());
        animal.setIsWild(getRandomBoolean());
        animal.setIsHerbivorous(getRandomBoolean());
        animal.setGender(getRandomGender());
        animal.setClassification(getRandomСlassification());
        return animal;
    }

    private boolean isNull() {
        return (Math.random() * 50 == 7);
    }

    private String getRandomString() {
        if (isNull()) {
            return null;
        } else {
            int leftLimit = 97;
            int rightLimit = 122;
            int targetStringLength = 10;
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit +
                        (int) (RANDOM.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            return buffer.toString();
        }
    }
    private int getRandomInt() {
        int min = 2;
        int max = 4;
        return (int) ((Math.random() * (max - min)) + min);
    }

    private boolean getRandomBoolean() {
        return RANDOM.nextBoolean();
    }

    private Gender getRandomGender() {
        Gender[] values = Gender.values();
        return values[RANDOM.nextInt(values.length)];
    }

    private Сlassification getRandomСlassification() {
        Сlassification classification = new Сlassification();
        classification.setFamily(getRandomString());
        classification.setType(getRandomString());
        return classification;
    }

    private void getResultTest(String testName, long fileSize, long serializeTime, long deserializeTime) {
        System.out.println(testName + "\n" + "File size = " + fileSize + "\n"
        + "Serialize Time = " + serializeTime + "\n" + "Deserialize Time = "
        + deserializeTime + "\n");
    }

}
