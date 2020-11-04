package ru.mail.polis.homework.io.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SerializerTest {
    private static final Path TEST_DIRECTORY = Paths.get("test");
    private static final Path DEFAULT_SERIALIZABLE_OUTPUT_FILE = TEST_DIRECTORY.resolve("defaultSerializable.out");

    private static final List<String> CREATURES_NAMES = List.of("Dog", "Cat", "Human", "Dolphin", "Whale", "Parrot",
            "Frog", "Camel", "Bear", "Turtle", "Scorpion", "Snake", "Tiger", "Crocodile", "Spider", "Ant", "Bee",
            "Eagle", "Ostrich");

    private static final List<String> FOOD_NAMES = List.of("Bones", "Milk", "Fish", "Raw meat", "Seeds", "Nuts",
            "Bread", "Dog food", "Cat food", "Chocolate", "Eggs", "Insects", "Worms", "Mice", "Honey");

    private static final double ANIMAL_MAX_WEIGHT = 150_000;

    private List<Animal> animals;

    @Before
    public void setUp() {

    }

    private static Animal generateRandomAnimal(Random random) {
        Animal.Builder builder = new Animal.Builder(getRandomEnum(Animal.Group.class, random),
                getRandomListEntry(CREATURES_NAMES, random), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(),
                getRandomEnum(Animal.Behavior.MovementType.class, random));
        if (random.nextBoolean()) {
            builder.withColor(random.nextInt());
        }
        if (random.nextBoolean()) {
            builder.withWeight(random.nextDouble() * ANIMAL_MAX_WEIGHT);
        }

        List<Animal.HabitatEnvironment> environments
                = getRandomSubset(Arrays.asList(Animal.HabitatEnvironment.values()), random, 1);
        builder.withHabitatEnvironments(environments.toArray(new Animal.HabitatEnvironment[0]));

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

    @After
    public void cleanUp() {

    }

    @Test
    public void defaultSerializeTest() {

    }

    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(generateRandomAnimal(random) + "\n");
        System.out.println(generateRandomAnimal(random) + "\n");
        System.out.println(generateRandomAnimal(random) + "\n");
    }
}
