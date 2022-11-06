package ru.mail.polis.homework.io.objects;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SerializerTest {
    private static final Serializer SERIALIZER = new Serializer();
    private static final int ANIMALS_COUNT = 500000;
    private final static String FILE_PATH = "./src/test/resources/object/serializeTest.bin";

    @Before
    public void setUp() throws Exception {
        Files.createDirectories(Paths.get("src", "test", "resources", "objects"));
        Files.createFile(Paths.get(FILE_PATH));
    }

    @After
    public void tearDown() throws Exception {
//        FileUtils.forceDelete(Paths.get(FILE_PATH).toFile());
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }

    @Test
    public void testDefaultSerialize() {
//        List<Animal> animalList = new ArrayList<>();
//        for (int i = 0; i < ANIMALS_COUNT; i++) {
//            animalList.add(generateAnimal());
//        }
//        long startSerialize = System.currentTimeMillis();
//        SERIALIZER.defaultSerialize(animalList, FILE_PATH);
//        long endSerialize = System.currentTimeMillis();
//        long startDeserialize = System.currentTimeMillis();
//        SERIALIZER.customDeserialize(FILE_PATH);
//        long endDeserialize = System.currentTimeMillis();
//        System.out.println("Default Serialize: \n" +
//                "Serialization: " + (endSerialize - startSerialize) +
//                "\n Deserialization: " + (endDeserialize - startDeserialize));
    }

    @Test
    public void testSerializeWithMethods() {
    }

    @Test
    public void testSerializeExternalizable() {
    }

    @Test
    public void testCustomSerialize() {
    }

    private static Animal generateAnimal() {
        Animal randomAnimal = new Animal();
        randomAnimal.setPet(generateBoolean());
        randomAnimal.setPredator(generateBoolean());
        randomAnimal.setLegs(generateInt(1, 5));
        randomAnimal.setColor(generateString());
        randomAnimal.setMoveType(generateMoveType());

        Animal.AnimalPassport animalPassport = new Animal.AnimalPassport();
        animalPassport.setSpecies(generateString());
        animalPassport.setSex(generateSex());
        animalPassport.setName(generateString());
        animalPassport.setAge(generateInt(1, 31));
        animalPassport.setVaccinated(generateBoolean());
        animalPassport.setDescriptionOfAnimal(generateString());

        randomAnimal.setAnimalPassport(animalPassport);
        return randomAnimal;
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