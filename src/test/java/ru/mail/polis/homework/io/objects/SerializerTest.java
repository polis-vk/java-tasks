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
    private static final int NUMBER_ANIMALS = 100;
    private static final int CHANCE_NULL = 25;
    private static final Path test_file = Paths.get("src", "test", "resources", "objects", "test.txt");
    private static final String[] possibleStreets = new String[]{"Pushkina St", "Bolshevikov St", "Pobedy St", null};
    private static final String[] possibleNumbers = new String[]{"89531112233", "89533332255", "89531412442", null};
    private static final String[] possibleNames = new String[]{"Lucky", "Leo", "Max", "Duke", null};

    private static final Serializer serializer = new Serializer();
    private static final List<Animal> generatedDefaultAnimals = new ArrayList<>();
    private static final List<AnimalWithMethods> generatedAnimalsWithMethods = new ArrayList<>();
    private static final List<AnimalExternalizable> generatedAnimalsExternalizable = new ArrayList<>();

    @BeforeClass
    public static void generateData() throws IOException {
        if (Files.notExists(test_file.getParent())) {
            Files.createDirectory(test_file.getParent());
        }

        for (int i = 0; i < NUMBER_ANIMALS; i++) {
            boolean isNull = ThreadLocalRandom.current().nextInt(1, 101) <= CHANCE_NULL;
            Animal animal = (isNull ? null : generateRandomAnimal());
            generatedDefaultAnimals.add(animal);
        }

        for (Animal animal : generatedDefaultAnimals) {
            if (animal == null) {
                generatedAnimalsWithMethods.add(null);
            } else {
                MoveType moveType = animal.getMoveType();

                AnimalWithMethods.MoveType animalWithMethodsMoveType;
                if (moveType == null) {
                    animalWithMethodsMoveType = null;
                } else {
                    animalWithMethodsMoveType = AnimalWithMethods.MoveType.valueOf(moveType.name());
                }

                generatedAnimalsWithMethods.add(new AnimalWithMethods(
                        animal.isHappy(), animal.isAngry(), animal.getLegs(), copyStr(animal.getName()),
                        animalWithMethodsMoveType, new AnimalWithMethods.AddressWithMethods(
                            copyStr(animal.getHomeAddress().getStreet()),
                            animal.getHomeAddress().getHouse(),
                            copyStr(animal.getHomeAddress().getPhoneNumber())
                        )
                ));
            }
        }
    }

    @Before
    public void cleanBeforeTest() throws IOException {
        Files.deleteIfExists(test_file);
    }

    @AfterClass
    public static void cleanAfterAllTests() throws IOException {
        Files.deleteIfExists(test_file);
    }

    @Test
    public void testDefaultSerialize() {
        serializer.defaultSerialize(generatedDefaultAnimals, test_file.toString());
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(test_file.toString());
        assertEquals(generatedDefaultAnimals, deserializedAnimals);
    }

    @Test
    public void testSerializeWithMethods() {
        serializer.serializeWithMethods(generatedAnimalsWithMethods, test_file.toString());
        List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods(test_file.toString());
        assertEquals(generatedAnimalsWithMethods, deserializedAnimals);
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

    private static String copyStr(String str) {
        if (str == null) {
            return null;
        }
        return new String(str.toCharArray());
    }
}
