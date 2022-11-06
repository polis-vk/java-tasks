package ru.mail.polis.homework.io.objects;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.mail.polis.homework.io.objects.Animal.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;


public class SerializerTest {
    private static final int NUMBER_ANIMALS = 5;
    private static final int CHANCE_NULL = 25;
    private static final Path test_file = Paths.get("src", "test", "resources", "objects", "test.txt");
    private static final String[] possibleStreets = new String[]{"Pushkina St", "Bolshevikov St", "Pobedy St", null};
    private static final String[] possibleNumbers = new String[]{"89531112233", "89533332255", "89531412442", null};
    private static final String[] possibleNames = new String[]{"Lucky", "Leo", "Max", "Duke", null};

    private static final Serializer serializer = new Serializer();
    private static final List<Animal> generatedAnimals = new ArrayList<>();

    @BeforeClass
    public static void generateData() {
        for (int i = 0; i < NUMBER_ANIMALS; i++) {
            boolean isNull = ThreadLocalRandom.current().nextInt(1, 101) <= CHANCE_NULL;
            Animal animal = (isNull ? null : generateRandomAnimal());
            generatedAnimals.add(animal);
        }
    }

    @Before
    public void cleanBeforeTest() throws IOException {
        Files.createDirectory(test_file.getParent());
        Files.deleteIfExists(test_file);
    }

    @AfterClass
    public static void cleanAfterAllTests() throws IOException {
        Files.deleteIfExists(test_file);
    }

    @Test
    public void testDefaultSerialize() {
        serializer.defaultSerialize(generatedAnimals, test_file.toString());
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(test_file.toString());
        assertEquals(generatedAnimals, deserializedAnimals);
    }

    private static Animal generateRandomAnimal() {
        boolean isHappy = ThreadLocalRandom.current().nextBoolean();
        boolean isAngry = ThreadLocalRandom.current().nextBoolean();
        int legs;
        String name = possibleNames[ThreadLocalRandom.current().nextInt(possibleNames.length)];
        MoveType moveType;
        Address address = generateRandomAddress();

        switch (ThreadLocalRandom.current().nextInt(4)) {
            case 1:
                legs = 4;
                moveType = MoveType.RUN;
                break;
            case 2:
                legs = 0;
                moveType = MoveType.CRAWL;
                break;
            case 3:
                legs = 2;
                moveType = MoveType.FLY;
                break;
            case 0:
            default:
                legs = 4;
                moveType = null;
                break;
        }

        return new Animal(isHappy, isAngry, legs, name, moveType, address);
    }

    private static Address generateRandomAddress() {
        int house = ThreadLocalRandom.current().nextInt(1, 51);
        String street = possibleStreets[ThreadLocalRandom.current().nextInt(possibleStreets.length)];
        String phoneNumber = possibleNumbers[ThreadLocalRandom.current().nextInt(possibleNumbers.length)];
        return new Address(street, house, phoneNumber);
    }
}
