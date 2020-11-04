package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final Path TEST_DIRECTORY = Paths.get("testResources");
    private static final Path DEFAULT_SERIALIZE_OUTPUT_FILE = TEST_DIRECTORY.resolve("defaultSerialize.out");
    private static final Path SERIALIZE_WITH_METHODS_OUTPUT_FILE = TEST_DIRECTORY.resolve("serializeWithMethods.out");
    private static final Path SERIALIZE_WITH_EXTERNALIZABLE_OUTPUT_FILE = TEST_DIRECTORY.resolve("serializeWithExternalizable.out");
    private static final Path CUSTOM_SERIALIZE_OUTPUT_FILE = TEST_DIRECTORY.resolve("customSerialize.out");

    private static final List<String> CREATURES_NAMES = List.of("Dog", "Cat", "Human", "Dolphin", "Whale", "Parrot",
            "Frog", "Camel", "Bear", "Turtle", "Scorpion", "Snake", "Tiger", "Crocodile", "Spider", "Ant", "Bee",
            "Eagle", "Ostrich");

    private static final List<String> FOOD_NAMES = List.of("Bones", "Milk", "Fish", "Raw meat", "Seeds", "Nuts",
            "Bread", "Dog food", "Cat food", "Chocolate", "Eggs", "Insects", "Worms", "Mice", "Honey");

    private static final double ANIMAL_MAX_WEIGHT = 150_000;
    private static final int ANIMAL_MAX_AGE = 100;

    private List<Animal> animals;
    private List<AnimalWithMethods> animalsWithMethods;
    private List<AnimalExternalizable> animalsExternalizable;
    private Serializer serializer;

    @Before
    public void setUp() throws IOException {
        Random random = new Random();
        animals = IntStream.range(0, 10)
                .mapToObj(i -> generateRandomAnimal(random))
                .collect(Collectors.toList());

        animalsWithMethods = IntStream.range(0, 10)
                .mapToObj(i -> generateRandomAnimal(random))
                .map(SerializerTest::animalToAnimalWithMethods)
                .collect(Collectors.toList());

        animalsExternalizable = IntStream.range(0, 10)
                .mapToObj(i -> generateRandomAnimal(random))
                .map(SerializerTest::animalToAnimalExternalizable)
                .collect(Collectors.toList());

        if (Files.exists(TEST_DIRECTORY)) {
            FileUtils.deleteDirectory(TEST_DIRECTORY.toFile());
        }
        Files.createDirectories(TEST_DIRECTORY);
        Files.createFile(DEFAULT_SERIALIZE_OUTPUT_FILE);
        Files.createFile(SERIALIZE_WITH_METHODS_OUTPUT_FILE);
        Files.createFile(SERIALIZE_WITH_EXTERNALIZABLE_OUTPUT_FILE);
        Files.createFile(CUSTOM_SERIALIZE_OUTPUT_FILE);

        serializer = new Serializer();
    }

    @After
    public void cleanUp() throws IOException {
        FileUtils.deleteDirectory(TEST_DIRECTORY.toFile());
    }

    @Test
    public void defaultSerializeTest() throws IOException, ClassNotFoundException {
        String filePath = DEFAULT_SERIALIZE_OUTPUT_FILE.toAbsolutePath().toString();
        serializer.defaultSerialize(animals, filePath);
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(filePath);
        assertEquals(animals, deserializedAnimals);
    }

    @Test
    public void serializeWithMethodsTest() throws IOException, ClassNotFoundException {
        String filePath = SERIALIZE_WITH_METHODS_OUTPUT_FILE.toAbsolutePath().toString();
        serializer.serializeWithMethods(animalsWithMethods, filePath);
        List<AnimalWithMethods> deserializedAnimalsWithMethods = serializer.deserializeWithMethods(filePath);
        assertEquals(animalsWithMethods, deserializedAnimalsWithMethods);
    }

    @Test
    public void serializeWithExternalizable() throws IOException, ClassNotFoundException {
        String filePath = SERIALIZE_WITH_EXTERNALIZABLE_OUTPUT_FILE.toAbsolutePath().toString();
        serializer.serializeWithExternalizable(animalsExternalizable, filePath);
        List<AnimalExternalizable> deserializedAnimalsExternalizable = serializer.deserializeWithExternalizable(filePath);
        assertEquals(animalsExternalizable, deserializedAnimalsExternalizable);
    }

    @Test
    public void customSerialize() throws IOException {
        String filePath = CUSTOM_SERIALIZE_OUTPUT_FILE.toAbsolutePath().toString();
        serializer.customSerialize(animals, filePath);
        List<Animal> deserializedAnimals = serializer.customDeserialize(filePath);
        assertEquals(animals, deserializedAnimals);
    }

    private static Animal generateRandomAnimal(Random random) {
        Animal.Builder builder = new Animal.Builder(getRandomEnum(AnimalGroup.class, random),
                getRandomListEntry(CREATURES_NAMES, random), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(),
                getRandomEnum(AnimalMovementType.class, random));

        if (random.nextBoolean()) {
            builder.withColor(random.nextInt());
        }
        if (random.nextBoolean()) {
            builder.withWeight(random.nextDouble() * ANIMAL_MAX_WEIGHT);
        }
        if (random.nextBoolean()) {
            builder.withAge(random.nextInt(ANIMAL_MAX_AGE + 1));
        }

        List<HabitatEnvironment> environments
                = getRandomSubset(Arrays.asList(HabitatEnvironment.values()), random, 1);
        builder.withHabitatEnvironments(environments.toArray(new HabitatEnvironment[0]));

        List<String> friends = getRandomSubset(CREATURES_NAMES, random);
        builder.withFriends(friends.toArray(new String[0]));

        List<String> enemies = getRandomSubset(CREATURES_NAMES, random);
        builder.withEnemies(enemies.toArray(new String[0]));

        List<String> favouriteFood = getRandomSubset(FOOD_NAMES, random);
        builder.withFavouriteFood(favouriteFood.toArray(new String[0]));

        return builder.build();
    }

    private static <T extends Enum<T>> T getRandomEnum(Class<T> enumClass, Random random) {
        int enumSize = enumClass.getEnumConstants().length;
        return enumClass.getEnumConstants()[random.nextInt(enumSize)];
    }

    private static <T> T getRandomListEntry(List<T> sourceList, Random random) {
        return sourceList.get(random.nextInt(sourceList.size()));
    }

    private static <T> List<T> getRandomSubset(List<T> sourceList, Random random, int minSize) {
        assert minSize >= 0 && minSize <= sourceList.size();

        // in this particular case enums are small, so Theta(enum.size) solution is OK
        List<T> shuffledList = new ArrayList<>(sourceList);
        Collections.shuffle(shuffledList);
        return shuffledList.subList(0, minSize + random.nextInt(shuffledList.size() - minSize));
    }

    private static <T> List<T> getRandomSubset(List<T> sourceList, Random random) {
        return getRandomSubset(sourceList, random, 0);
    }

    private static AnimalWithMethods animalToAnimalWithMethods(Animal animal) {
        Animal.Behavior behavior = animal.getBehavior();
        return new AnimalWithMethods.Builder(animal.getGroup(), animal.getName(), animal.isWarmBlooded(),
                behavior.canBeTamed(), behavior.isPredator(), behavior.getMovementType())
                .withAge(animal.getAge())
                .withColor(animal.getColor())
                .withWeight(animal.getWeight())
                .withHabitatEnvironments(animal.habitatEnvironments().toArray(new HabitatEnvironment[0]))
                .withFriends(behavior.friends().toArray(new String[0]))
                .withEnemies(behavior.enemies().toArray(new String[0]))
                .withFavouriteFood(behavior.favouriteFoodList().toArray(new String[0]))
                .build();
    }

    private static AnimalExternalizable animalToAnimalExternalizable(Animal animal) {
        Animal.Behavior behavior = animal.getBehavior();
        return new AnimalExternalizable.Builder(animal.getGroup(), animal.getName(), animal.isWarmBlooded(),
                behavior.canBeTamed(), behavior.isPredator(), behavior.getMovementType())
                .withAge(animal.getAge())
                .withColor(animal.getColor())
                .withWeight(animal.getWeight())
                .withHabitatEnvironments(animal.habitatEnvironments().toArray(new HabitatEnvironment[0]))
                .withFriends(behavior.friends().toArray(new String[0]))
                .withEnemies(behavior.enemies().toArray(new String[0]))
                .withFavouriteFood(behavior.favouriteFoodList().toArray(new String[0]))
                .build();
    }
}
