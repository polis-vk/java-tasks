package ru.mail.polis.homework.io.objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SerializerTest {
    private static List<Animal> animals = new ArrayList<>();
    private static List<AnimalExternalizable> animalsExternalizable = new ArrayList<>();
    private static List<AnimalWithMethods> animalsWithMethods = new ArrayList<>();
    private static Random random = new Random();
    private static Serializer serializer = new Serializer();

    private static final int countOfAnimals = 50000;

    @BeforeAll
    static void initFields() {
        for (int i = 0; i < countOfAnimals; i++) {
            int randomAnimalTypeIndex = random.nextInt(Animal.AnimalTypeClass.values().length);
            String randomNickname = randomString(random.nextInt(10));
            int randomWeight = random.nextInt(200);
            int randomAge = random.nextInt(50);
            List<Integer> randomListSizes = new ArrayList<>();
            for (int j = 0; j < random.nextInt(10); j++) {
                randomListSizes.add(random.nextInt(20));
            }
            int randomMainColorIndex = random.nextInt(Animal.Color.values().length);
            int randomEyeColorIndex = random.nextInt(Animal.Color.values().length);
            int randomNumberOfLimbs = random.nextInt(10);
            int randomSexIndex = random.nextInt(Animal.Sex.values().length);
            String randomHabitatPlaceName = randomString(random.nextInt(10));
            int randomHabitatAverageTemperature = random.nextInt(500);
            int randomHabitatHeightAboveSeaLevel = random.nextInt(10000);

            animals.add(new Animal(
                    Animal.AnimalTypeClass.values()[randomAnimalTypeIndex],
                    randomNickname,
                    randomWeight,
                    randomAge,
                    randomListSizes,
                    Animal.Color.values()[randomMainColorIndex],
                    Animal.Color.values()[randomEyeColorIndex],
                    randomNumberOfLimbs,
                    Animal.Sex.values()[randomSexIndex],
                    new Animal.Habitat(
                            randomHabitatPlaceName,
                            randomHabitatAverageTemperature,
                            randomHabitatHeightAboveSeaLevel
                    )
            ));

            animalsExternalizable.add(new AnimalExternalizable(
                    AnimalExternalizable.AnimalTypeClass.values()[randomAnimalTypeIndex],
                    randomNickname,
                    randomWeight,
                    randomAge,
                    randomListSizes,
                    AnimalExternalizable.Color.values()[randomMainColorIndex],
                    AnimalExternalizable.Color.values()[randomEyeColorIndex],
                    randomNumberOfLimbs,
                    AnimalExternalizable.Sex.values()[randomSexIndex],
                    new AnimalExternalizable.Habitat(
                            randomHabitatPlaceName,
                            randomHabitatAverageTemperature,
                            randomHabitatHeightAboveSeaLevel
                    )
            ));

            animalsWithMethods.add(new AnimalWithMethods(
                    AnimalWithMethods.AnimalTypeClass.values()[randomAnimalTypeIndex],
                    randomNickname,
                    randomWeight,
                    randomAge,
                    randomListSizes,
                    AnimalWithMethods.Color.values()[randomMainColorIndex],
                    AnimalWithMethods.Color.values()[randomEyeColorIndex],
                    randomNumberOfLimbs,
                    AnimalWithMethods.Sex.values()[randomSexIndex],
                    new AnimalWithMethods.Habitat(
                            randomHabitatPlaceName,
                            randomHabitatAverageTemperature,
                            randomHabitatHeightAboveSeaLevel
                    )
            ));
        }
    }

    private static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int c = random.nextInt(74) + 48;
            sb.append((char) c);
        }
        return sb.toString();
    }

    @Test
    @Order(0)
    void defaultSerialize() throws IOException {
        long beginTime = System.currentTimeMillis();
        serializer.defaultSerialize(animals, "defaultTest.bin");
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("File defaultTest.bin (%d BYTE) was CREATED in %d mls",
                Files.size(Path.of("defaultTest.bin")),
                endTime - beginTime));
    }

    @Test
    @Order(1)
    void defaultDeserialize() throws IOException, ClassNotFoundException {
        long beginTime = System.currentTimeMillis();
        List<Animal> deserializeList = serializer.defaultDeserialize("defaultTest.bin");
        long endTime = System.currentTimeMillis();
        assertEquals(deserializeList, animals);
        System.out.println(String.format("File defaultTest.bin (%d BYTE) was DESERIALIZED in %d mls\n",
                Files.size(Path.of("defaultTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("defaultTest.bin"));
    }

    @Test
    @Order(2)
    void serializeWithMethods() throws IOException {
        long beginTime = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, "withMethodsTest.bin");
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("File withMethodsTest.bin (%d BYTE) was CREATED in %d mls",
                Files.size(Path.of("withMethodsTest.bin")),
                endTime - beginTime));
    }

    @Test
    @Order(3)
    void deserializeWithMethods() throws IOException {
        long beginTime = System.currentTimeMillis();
        List<AnimalWithMethods> deserializeList = serializer.deserializeWithMethods("withMethodsTest.bin");
        long endTime = System.currentTimeMillis();
        assertEquals(deserializeList, animalsWithMethods);
        System.out.println(String.format("File withMethodsTest.bin (%d BYTE) was DESERIALIZED in %d mls\n",
                Files.size(Path.of("withMethodsTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("withMethodsTest.bin"));
    }

    @Test
    @Order(4)
    void serializeWithExternalizable() throws IOException {
        long beginTime = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, "withExternalizableTest.bin");
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("File withExternalizableTest.bin (%d BYTE) was CREATED in %d mls",
                Files.size(Path.of("withExternalizableTest.bin")),
                endTime - beginTime));
    }

    @Test
    @Order(5)
    void deserializeWithExternalizable() throws IOException {
        long beginTime = System.currentTimeMillis();
        List<AnimalExternalizable> deserializeList =
                serializer.deserializeWithExternalizable("withExternalizableTest.bin");
        long endTime = System.currentTimeMillis();
        assertEquals(deserializeList, animalsExternalizable);
        System.out.println(
                String.format("File withExternalizableTest.bin (%d BYTE) was DESERIALIZED in %d mls\n",
                Files.size(Path.of("withExternalizableTest.bin")),
                endTime - beginTime)
        );
        Files.delete(Path.of("withExternalizableTest.bin"));
    }

    @Test
    @Order(6)
    void customSerialize() throws IOException {
        long beginTime = System.currentTimeMillis();
        serializer.customSerialize(animals, "customTest.bin");
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("File customTest.bin (%d BYTE) was CREATED in %d mls",
                Files.size(Path.of("customTest.bin")),
                endTime - beginTime));
    }

    @Test
    @Order(7)
    void customDeserialize() throws IOException {
        long beginTime = System.currentTimeMillis();
        List<Animal> deserializeList =
                serializer.customDeserialize("customTest.bin");
        long endTime = System.currentTimeMillis();
        assertEquals(deserializeList, animals);
        System.out.println(String.format("File customTest.bin (%d BYTE) was DESERIALIZED in %d mls\n",
                Files.size(Path.of("customTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("customTest.bin"));
    }
}