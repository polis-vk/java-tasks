package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final Serializer SERIALIZER = new Serializer();
    private static final int ANIMALS_COUNT = 500000;
    private final static Path FILE_PATH = Paths.get("src", "test", "resources", "objects", "serializeTest.bin");

    @Before
    public void setUp() throws Exception {
        Files.createDirectories(Paths.get("src", "test", "resources", "objects"));
        Files.createFile(FILE_PATH);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    public void testDefaultSerialize() throws IOException {
        List<Animal> animalList = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            animalList.add(generateAnimal());
        }
        long startSerialize = System.currentTimeMillis();
        SERIALIZER.defaultSerialize(animalList, FILE_PATH.toString());
        long endSerialize = System.currentTimeMillis();
        long startDeserialize = System.currentTimeMillis();
        List<Animal> deserializedAnimalList = SERIALIZER.defaultDeserialize(FILE_PATH.toString());
        long endDeserialize = System.currentTimeMillis();
        assertEquals(animalList, deserializedAnimalList);
        System.out.println("Default Serialize:" +
                "\nSerialization: " + (endSerialize - startSerialize) +
                "\nDeserialization: " + (endDeserialize - startDeserialize) +
                "\nFile size: " + Files.size(FILE_PATH) + "\n");
    }

    @Test
    public void testSerializeWithMethods() throws IOException {
        List<AnimalWithMethods> animalList = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            animalList.add(generateAnimalWithMethods());
        }
        long startSerialize = System.currentTimeMillis();
        SERIALIZER.serializeWithMethods(animalList, FILE_PATH.toString());
        long endSerialize = System.currentTimeMillis();
        long startDeserialize = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimalList = SERIALIZER.deserializeWithMethods(FILE_PATH.toString());
        long endDeserialize = System.currentTimeMillis();
        assertEquals(animalList, deserializedAnimalList);
        System.out.println("Serialize with methods:" +
                "\nSerialization: " + (endSerialize - startSerialize) +
                "\nDeserialization: " + (endDeserialize - startDeserialize) +
                "\nFile size: " + Files.size(FILE_PATH) + "\n");
    }

    @Test
    public void testSerializeExternalizable() throws IOException {
        List<AnimalExternalizable> animalList = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            animalList.add(generateAnimalExternalizable());
        }
        long startSerialize = System.currentTimeMillis();
        SERIALIZER.serializeWithExternalizable(animalList, FILE_PATH.toString());
        long endSerialize = System.currentTimeMillis();
        long startDeserialize = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedAnimalList = SERIALIZER.deserializeWithExternalizable(FILE_PATH.toString());
        long endDeserialize = System.currentTimeMillis();
        assertEquals(animalList, deserializedAnimalList);
        System.out.println("Serialize externalizable:" +
                "\nSerialization: " + (endSerialize - startSerialize) +
                "\nDeserialization: " + (endDeserialize - startDeserialize) +
                "\nFile size: " + Files.size(FILE_PATH) + "\n");
    }

    @Test
    public void testCustomSerialize() throws IOException {
        List<Animal> animalList = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            animalList.add(generateAnimal());
        }
        long startSerialize = System.currentTimeMillis();
        SERIALIZER.customSerialize(animalList, FILE_PATH.toString());
        long endSerialize = System.currentTimeMillis();
        long startDeserialize = System.currentTimeMillis();
        List<Animal> deserializedAnimalList = SERIALIZER.customDeserialize(FILE_PATH.toString());
        long endDeserialize = System.currentTimeMillis();
        assertEquals(animalList, deserializedAnimalList);
        System.out.println("Custom serialize:" +
                "\nSerialization: " + (endSerialize - startSerialize) +
                "\nDeserialization: " + (endDeserialize - startDeserialize) +
                "\nFile size: " + Files.size(FILE_PATH) + "\n");
    }

    private static Animal generateAnimal() {
        if (rndThrowNull()) {
            return null;
        }
        Animal randomAnimal = new Animal();
        randomAnimal.setPet(generateBoolean());
        randomAnimal.setPredator(generateBoolean());
        randomAnimal.setLegs(generateInt(1, 5));
        randomAnimal.setColor(generateString());
        randomAnimal.setMoveType(generateMoveType());

        AnimalPassport animalPassport = new AnimalPassport();
        animalPassport.setSpecies(generateString());
        animalPassport.setSex(generateSex());
        animalPassport.setName(generateString());
        animalPassport.setAge(generateInt(1, 31));
        animalPassport.setVaccinated(generateBoolean());
        animalPassport.setDescriptionOfAnimal(generateString());

        randomAnimal.setAnimalPassport(animalPassport);
        return randomAnimal;
    }

    private static AnimalWithMethods generateAnimalWithMethods() {
        if (rndThrowNull()) {
            return null;
        }
        AnimalWithMethods randomAnimalWithMethods = new AnimalWithMethods();
        randomAnimalWithMethods.setPet(generateBoolean());
        randomAnimalWithMethods.setPredator(generateBoolean());
        randomAnimalWithMethods.setLegs(generateInt(1, 5));
        randomAnimalWithMethods.setColor(generateString());
        randomAnimalWithMethods.setMoveType(generateMoveType());

        AnimalPassportWithMethods animalPassportWithMethods =
                new AnimalPassportWithMethods();
        animalPassportWithMethods.setSpecies(generateString());
        animalPassportWithMethods.setSex(generateSex());
        animalPassportWithMethods.setName(generateString());
        animalPassportWithMethods.setAge(generateInt(1, 31));
        animalPassportWithMethods.setVaccinated(generateBoolean());
        animalPassportWithMethods.setDescriptionOfAnimal(generateString());

        randomAnimalWithMethods.setAnimalPassportWithMethods(animalPassportWithMethods);
        return randomAnimalWithMethods;
    }

    private static AnimalExternalizable generateAnimalExternalizable() {
        if (rndThrowNull()) {
            return null;
        }
        AnimalExternalizable randomAnimalExternalizable = new AnimalExternalizable();
        randomAnimalExternalizable.setPet(generateBoolean());
        randomAnimalExternalizable.setPredator(generateBoolean());
        randomAnimalExternalizable.setLegs(generateInt(1, 5));
        randomAnimalExternalizable.setColor(generateString());
        randomAnimalExternalizable.setMoveType(generateMoveType());

        AnimalPassportExternalizable animalPassportExternalizable =
                new AnimalPassportExternalizable();
        animalPassportExternalizable.setSpecies(generateString());
        animalPassportExternalizable.setSex(generateSex());
        animalPassportExternalizable.setName(generateString());
        animalPassportExternalizable.setAge(generateInt(1, 31));
        animalPassportExternalizable.setVaccinated(generateBoolean());
        animalPassportExternalizable.setDescriptionOfAnimal(generateString());

        randomAnimalExternalizable.setAnimalPassportExternalizable(animalPassportExternalizable);
        return randomAnimalExternalizable;
    }

    private static boolean generateBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static int generateInt(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }

    private static String generateString() {
        if (rndThrowNull()) {
            return null;
        }
        char[] chars = new char[ThreadLocalRandom.current().nextInt(18) + 2];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (ThreadLocalRandom.current().nextInt('z' - 'a') + 'a');
        }
        return new String(chars);
    }

    private static MoveType generateMoveType() {
        if (rndThrowNull()) {
            return null;
        }
        return MoveType.values()[ThreadLocalRandom.current().nextInt(0, MoveType.values().length)];
    }

    private static Sex generateSex() {
        if (rndThrowNull()) {
            return null;
        }
        return Sex.values()[ThreadLocalRandom.current().nextInt(0, Sex.values().length)];
    }

    private static boolean rndThrowNull() {
        return ThreadLocalRandom.current().nextInt(1, 10) == 5;
    }

}