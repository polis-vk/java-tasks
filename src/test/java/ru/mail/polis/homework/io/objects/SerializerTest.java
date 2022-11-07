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
    private static final int NUMBER_ANIMALS = 1_000_000;
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
                generatedAnimalsExternalizable.add(null);
            } else {
                MoveType moveType = animal.getMoveType();
                AnimalWithMethods.MoveType animalWithMethodsMoveType;
                AnimalExternalizable.MoveType animalExternalizableMoveType;
                if (moveType == null) {
                    animalWithMethodsMoveType = null;
                    animalExternalizableMoveType = null;
                } else {
                    animalWithMethodsMoveType = AnimalWithMethods.MoveType.valueOf(moveType.name());
                    animalExternalizableMoveType = AnimalExternalizable.MoveType.valueOf(moveType.name());
                }

                Address address = animal.getHomeAddress();
                AnimalWithMethods.AddressWithMethods addressWithMethods;
                AnimalExternalizable.AddressExternalizable addressExternalizable;
                if (address == null) {
                    addressWithMethods = null;
                    addressExternalizable = null;
                } else {
                    addressWithMethods = new AnimalWithMethods.AddressWithMethods(
                            copyStr(animal.getHomeAddress().getStreet()),
                            animal.getHomeAddress().getHouse(),
                            copyStr(animal.getHomeAddress().getPhoneNumber())
                    );
                    addressExternalizable = new AnimalExternalizable.AddressExternalizable(
                            copyStr(animal.getHomeAddress().getStreet()),
                            animal.getHomeAddress().getHouse(),
                            copyStr(animal.getHomeAddress().getPhoneNumber())
                    );
                }


                generatedAnimalsWithMethods.add(new AnimalWithMethods(
                        animal.isHappy(), animal.isAngry(), animal.getLegs(), copyStr(animal.getName()),
                        animalWithMethodsMoveType, addressWithMethods
                ));
                generatedAnimalsExternalizable.add(new AnimalExternalizable(
                        animal.isHappy(), animal.isAngry(), animal.getLegs(), copyStr(animal.getName()),
                        animalExternalizableMoveType, addressExternalizable
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
        long timerStart = System.currentTimeMillis();
        serializer.defaultSerialize(generatedDefaultAnimals, test_file.toString());
        long serializeTime = System.currentTimeMillis() - timerStart;

        timerStart = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(test_file.toString());
        long deserializeTime = System.currentTimeMillis() - timerStart;

        assertEquals(generatedDefaultAnimals, deserializedAnimals);
        System.out.println("DefaultSerialize | fileSize: " + getFileSize() +
                " serializeTime: " + serializeTime + " deserializeTime: " + deserializeTime);
        System.out.println(getFileSize() + " | " + serializeTime + " | " + deserializeTime);
    }

    @Test
    public void testSerializeWithMethods() {
        long timerStart = System.currentTimeMillis();
        serializer.serializeWithMethods(generatedAnimalsWithMethods, test_file.toString());
        long serializeTime = System.currentTimeMillis() - timerStart;

        timerStart = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimals = serializer.deserializeWithMethods(test_file.toString());
        long deserializeTime = System.currentTimeMillis() - timerStart;

        assertEquals(generatedAnimalsWithMethods, deserializedAnimals);
        System.out.println("SerializeWithMethods | fileSize: " + getFileSize() +
                " serializeTime: " + serializeTime + " deserializeTime: " + deserializeTime);
        System.out.println(getFileSize() + " | " + serializeTime + " | " + deserializeTime);
    }

    @Test
    public void testSerializeExternalizable() {
        long timerStart = System.currentTimeMillis();
        serializer.serializeWithExternalizable(generatedAnimalsExternalizable, test_file.toString());
        long serializeTime = System.currentTimeMillis() - timerStart;

        timerStart = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable(test_file.toString());
        long deserializeTime = System.currentTimeMillis() - timerStart;

        assertEquals(generatedAnimalsExternalizable, deserializedAnimals);
        System.out.println("SerializeWithExternalizable | fileSize: " + getFileSize() +
                " serializeTime: " + serializeTime + " deserializeTime: " + deserializeTime);
        System.out.println(getFileSize() + " | " + serializeTime + " | " + deserializeTime);
    }

    @Test
    public void testSerializeCustom() {
        long timerStart = System.currentTimeMillis();
        serializer.customSerialize(generatedAnimalsWithMethods, test_file.toString());
        long serializeTime = System.currentTimeMillis() - timerStart;

        timerStart = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimals = serializer.customDeserialize(test_file.toString());
        long deserializeTime = System.currentTimeMillis() - timerStart;

        assertEquals(generatedAnimalsWithMethods, deserializedAnimals);
        System.out.println("CustomSerialize | fileSize: " + getFileSize() +
                " serializeTime: " + serializeTime + " deserializeTime: " + deserializeTime);
        System.out.println(getFileSize() + " | " + serializeTime + " | " + deserializeTime);
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
        if (ThreadLocalRandom.current().nextInt(1, 101) <= CHANCE_NULL) {
            return null;
        }

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

    private static int getFileSize() {
        try {
            return (int) (Files.size(test_file) / 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
