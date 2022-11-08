package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final Path PATH_TO_DIR = Paths.get("src", "test", "resources", "objects");
    private static final Path PATH_TO_FILE = Paths.get("src", "test", "resources", "objects", "test1.txt");

    private static final Serializer SERIALIZER = new Serializer();
    private static final List<Animal> listOfAnimalsWithDefaultSerializer = new ArrayList<>();
    private static final List<AnimalWithMethods> listOfAnimalsWithMethods = new ArrayList<>();
    private static final List<AnimalExternalizable> listOfAnimalsWithExternalizer = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        Files.createDirectories(PATH_TO_DIR);
        Files.createFile(PATH_TO_FILE);

        final int amountOfAnimalsInList = 100_000;
        for (int i = 0; i < amountOfAnimalsInList; i++) {
            listOfAnimalsWithDefaultSerializer.add(AnimalGeneration.generateDefaultAnimal());
            listOfAnimalsWithExternalizer.add(AnimalGeneration.generateExternalizableAnimal());
            listOfAnimalsWithMethods.add(AnimalGeneration.generateAnimalWithMethods());
        }
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get("src", "test", "resources", "objects", "test1.txt"));
    }

    @Test
    public void testDefaultSerializer() throws IOException {
        long timerStart = System.currentTimeMillis();
        SERIALIZER.defaultSerialize(listOfAnimalsWithDefaultSerializer, PATH_TO_FILE.toString());
        long serializationTime = System.currentTimeMillis() - timerStart;

        timerStart = System.currentTimeMillis();
        List<Animal> deserializedAnimalsList = SERIALIZER.defaultDeserialize(PATH_TO_FILE.toString());
        long deserializationTime = System.currentTimeMillis() - timerStart;
        long fileSize = Files.size(PATH_TO_FILE);
        assertEquals(listOfAnimalsWithDefaultSerializer, deserializedAnimalsList);
        System.out.print("Размер файла: " + fileSize + "байт");
        System.out.print("Время записи:  " + serializationTime + "мс");
        System.out.print("Время чтения: " + deserializationTime + "мс");
    }

    @Test
    public void testSerializerWithMethods() throws Exception {
        long timerStart = System.currentTimeMillis();
        SERIALIZER.serializeWithMethods(listOfAnimalsWithMethods, PATH_TO_FILE.toString());
        long serializationTime = System.currentTimeMillis() - timerStart;

        timerStart = System.currentTimeMillis();
        List<AnimalWithMethods> resultOfDeserialization = SERIALIZER.deserializeWithMethods(PATH_TO_FILE.toString());
        long deserializationTime = System.currentTimeMillis() - timerStart;
        long fileSize = Files.size(PATH_TO_FILE);
        assertEquals(listOfAnimalsWithMethods, resultOfDeserialization);
        System.out.print("Размер файла: " + fileSize + "байт");
        System.out.print("Время записи:  " + serializationTime + "мс");
        System.out.print("Время чтения: " + deserializationTime + "мс");
    }

    @Test
    public void testExternalizerWithMethods() throws Exception {
        long timerStart = System.currentTimeMillis();
        SERIALIZER.serializeWithExternalizable(listOfAnimalsWithExternalizer, PATH_TO_FILE.toString());
        long serializationTime = System.currentTimeMillis() - timerStart;

        timerStart = System.currentTimeMillis();
        List<AnimalExternalizable> resultOfDeserialization = SERIALIZER.deserializeWithExternalizable(PATH_TO_FILE.toString());
        long deserializationTime = System.currentTimeMillis() - timerStart;
        long fileSize = Files.size(PATH_TO_FILE);
        assertEquals(listOfAnimalsWithExternalizer, resultOfDeserialization);
        System.out.print("Размер файла: " + fileSize + "байт");
        System.out.print("Время записи:  " + serializationTime + "мс");
        System.out.print("Время чтения: " + deserializationTime + "мс");
    }

    private static class AnimalGeneration {
        private static final String[] ARRAY_WITH_NAMES = {"Jojo", "Pen", "Bella", "Poppy", "Alfie", "Charlie", "Lola", "Teddy", "Oscar", null};
        private static final String[] ARRAY_WITH_HABITATS = {"FOREST", "DESERT", "WATER", "CITY", "VILLAGE", "ANTARCTICA"};
        private static final String[] ARRAY_WITH_ANIMALS_TYPES = {"CAT", "COW", "DOG", "KANGAROO", "PIGEON", "SHARK", "SNAKE"};
        private static final Random RANDOM = new Random();
        private static final long MAX_POPULATION_SIZE = 1000000L;
        private static final int MAX_AGES = 20;

        public static Animal generateDefaultAnimal() {
            if (generatedElementShouldBeNull()) {
                return null;
            }
            String currentName = generateName();
            int currentAges = generateAges();
            boolean isCurrentAnimalIsAggressive = RANDOM.nextBoolean();
            boolean isCurrentAnimalIsInvertebrate = RANDOM.nextBoolean();
            AnimalType currentAnimalType = generateAnimalType();
            Habitat currentAnimalHabitat = generateHabitatOfAnimal();
            long currentPopulationSize = generatePopulationSize();
            boolean isListedInTheRedBook = RANDOM.nextBoolean();
            boolean isDangerous = RANDOM.nextBoolean();
            GeneralInformation currentInformation = new GeneralInformation(currentAnimalHabitat, currentPopulationSize, isListedInTheRedBook, isDangerous);
            return new Animal(currentName, currentAges, isCurrentAnimalIsAggressive, isCurrentAnimalIsInvertebrate, currentAnimalType, currentInformation);
        }

        public static AnimalWithMethods generateAnimalWithMethods() {
            if (generatedElementShouldBeNull()) {
                return null;
            }
            String currentName = generateName();
            int currentAges = generateAges();
            boolean isCurrentAnimalIsAggressive = RANDOM.nextBoolean();
            boolean isCurrentAnimalIsInvertebrate = RANDOM.nextBoolean();
            AnimalType currentAnimalType = generateAnimalType();
            Habitat currentAnimalHabitat = generateHabitatOfAnimal();
            long currentPopulationSize = generatePopulationSize();
            boolean isListedInTheRedBook = RANDOM.nextBoolean();
            boolean isDangerous = RANDOM.nextBoolean();
            GeneralInformationWithMethods currentInformation = new GeneralInformationWithMethods(currentAnimalHabitat, currentPopulationSize, isListedInTheRedBook, isDangerous);
            return new AnimalWithMethods(currentName, currentAges, isCurrentAnimalIsAggressive, isCurrentAnimalIsInvertebrate, currentAnimalType, currentInformation);
        }

        public static AnimalExternalizable generateExternalizableAnimal() {
            if (generatedElementShouldBeNull()) {
                return null;
            }
            String currentName = generateName();
            int currentAges = generateAges();
            boolean isCurrentAnimalIsAggressive = RANDOM.nextBoolean();
            boolean isCurrentAnimalIsInvertebrate = RANDOM.nextBoolean();
            AnimalType currentAnimalType = generateAnimalType();
            Habitat currentAnimalHabitat = generateHabitatOfAnimal();
            long currentPopulationSize = generatePopulationSize();
            boolean isListedInTheRedBook = RANDOM.nextBoolean();
            boolean isDangerous = RANDOM.nextBoolean();
            GeneralInformationExternalizable currentInformation = new GeneralInformationExternalizable(currentAnimalHabitat, currentPopulationSize, isListedInTheRedBook, isDangerous);
            return new AnimalExternalizable(currentName, currentAges, isCurrentAnimalIsAggressive, isCurrentAnimalIsInvertebrate, currentAnimalType, currentInformation);
        }

        private static AnimalType generateAnimalType() {
            if (generatedElementShouldBeNull()) {
                return null;
            }
            return AnimalType.valueOf(ARRAY_WITH_ANIMALS_TYPES[RANDOM.nextInt(ARRAY_WITH_ANIMALS_TYPES.length)]);
        }

        private static Habitat generateHabitatOfAnimal() {
            if (generatedElementShouldBeNull()) {
                return null;
            }
            return Habitat.valueOf(ARRAY_WITH_HABITATS[RANDOM.nextInt(ARRAY_WITH_HABITATS.length)]);
        }

        private static long generatePopulationSize() {
            return Math.abs(RANDOM.nextLong() % MAX_POPULATION_SIZE) + 10;
        }

        private static String generateName() {
            return ARRAY_WITH_NAMES[RANDOM.nextInt(ARRAY_WITH_NAMES.length)];
        }

        private static int generateAges() {
            return RANDOM.nextInt(MAX_AGES) + 1;
        }

        private static boolean generatedElementShouldBeNull() {
            return RANDOM.nextInt(6) == 0;
        }
    }
}
