package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final Path DIRECTORY = Paths.get("src", "test", "resources", "objects");
    private static final Path DEFAULT = Paths.get("src", "test", "resources", "objects", "default.txt");
    private static final Path EXTERNALIZABLE = Paths.get("src", "test", "resources", "objects", "externalizable.txt");
    private static final Path WITH_METHODS = Paths.get("src", "test", "resources", "objects", "withMethods.txt");
    private static final Path CUSTOM = Paths.get("src", "test", "resources", "objects", "custom.txt");

    private static final Serializer SERIALIZER = new Serializer();
    private static final int ANIMALS_COUNT = 1_000_000;


    @Before
    public void setUp() throws IOException {
        Files.createDirectories(DIRECTORY);
        Files.createFile(DEFAULT);
        Files.createFile(WITH_METHODS);
        Files.createFile(CUSTOM);
        Files.createFile(EXTERNALIZABLE);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(DIRECTORY.toFile());
    }

    @Test
    public void defaultSerializationTest() throws Exception {
        List<Animal> animals = IntStream.range(0, ANIMALS_COUNT)
                .mapToObj(i -> generateAnimal())
                .collect(Collectors.toList());
        long beforeSerialization = System.currentTimeMillis();
        SERIALIZER.defaultSerialize(animals, DEFAULT.toString());
        long afterSerialization = System.currentTimeMillis();
        List<Animal> deserializedAnimals = SERIALIZER.defaultDeserialize(DEFAULT.toString());
        long size = Files.size(DEFAULT);
        long afterDeserialization = System.currentTimeMillis();
        assertEquals(animals, deserializedAnimals);
        message("defaultSerialization", beforeSerialization, afterSerialization, size, afterDeserialization);
    }

    @Test
    public void withMethodsSerializationTest() throws Exception {
        List<AnimalWithMethods> animalsWithMethods = IntStream.range(0, ANIMALS_COUNT)
                .mapToObj(i -> generateAnimalWithMethods())
                .collect(Collectors.toList());
        long beforeSerialization = System.currentTimeMillis();
        SERIALIZER.serializeWithMethods(animalsWithMethods, DEFAULT.toString());
        long afterSerialization = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimalsWithMethods = SERIALIZER.deserializeWithMethods(DEFAULT.toString());
        long afterDeserialization = System.currentTimeMillis();
        long size = Files.size(DEFAULT);
        assertEquals(animalsWithMethods, deserializedAnimalsWithMethods);
        message("withMethodsSerialization", beforeSerialization, afterSerialization, size, afterDeserialization);
    }

    @Test
    public void externalizableSerializationTest() throws Exception {
        List<AnimalExternalizable> animalsExternalizable = IntStream.range(0, ANIMALS_COUNT)
                .mapToObj(i -> generateAnimalExternalizable())
                .collect(Collectors.toList());
        long beforeSerialization = System.currentTimeMillis();
        SERIALIZER.serializeWithExternalizable(animalsExternalizable, DEFAULT.toString());
        long afterSerialization = System.currentTimeMillis();
        List<AnimalExternalizable> deserializeAnimalsExternalizable = SERIALIZER.deserializeWithExternalizable(DEFAULT.toString());
        long afterDeserialization = System.currentTimeMillis();
        long size = Files.size(DEFAULT);
        assertEquals(animalsExternalizable, deserializeAnimalsExternalizable);
        message("externalizableSerialization", beforeSerialization, afterSerialization, size, afterDeserialization);
    }

    @Test
    public void customSerializationTest() throws Exception {
        List<Animal> generatedAnimals = IntStream.range(0, ANIMALS_COUNT).mapToObj(i -> generateAnimal()).collect(Collectors.toList());
        long beforeSerialization = System.currentTimeMillis();
        SERIALIZER.customSerialize(generatedAnimals, DEFAULT.toString());
        long afterSerialization = System.currentTimeMillis();
        List<Animal> deserializedAnimals = SERIALIZER.customDeserialize(DEFAULT.toString());
        long afterDeserialization = System.currentTimeMillis();
        long size = Files.size(DEFAULT);
        assertEquals(generatedAnimals, deserializedAnimals);
        message("customSerialization", beforeSerialization, afterSerialization, size, afterDeserialization);
    }

    private static final Random RND = new Random();

    private static Animal generateAnimal() {
        if (!RND.nextBoolean()) {
            return null;
        }
        Animal animal = new Animal();
        if (RND.nextBoolean()) {
            animal.setName(generateString());
        }
        if (RND.nextBoolean()) {
            animal.setAnimalType(AnimalType.values()[RND.nextInt(AnimalType.values().length)]);
        }
        animal.setCountLegs(RND.nextInt());
        animal.setDomesticated(RND.nextBoolean());
        animal.setHerbivore(RND.nextBoolean());
        if (RND.nextBoolean()) {
            animal.setOwner(generateOrganization());
        }
        return animal;
    }

    private static Owner generateOrganization() {
        Owner owner = new Owner();
        owner.setName(generateString());
        owner.setOrganization(RND.nextBoolean());
        return owner;
    }

    private static AnimalExternalizable generateAnimalExternalizable() {
        AnimalExternalizable animal = new AnimalExternalizable();
        if (RND.nextBoolean()) {
            animal.setName(generateString());
        }
        if (RND.nextBoolean()) {
            animal.setAnimalType(AnimalType.values()[RND.nextInt(AnimalType.values().length)]);
        }
        animal.setCountLegs(RND.nextInt());
        animal.setDomesticated(RND.nextBoolean());
        animal.setHerbivore(RND.nextBoolean());
        if (RND.nextBoolean()) {
            animal.setOwnerExternalizable(generateOwnerExternalizable());
        }
        return animal;
    }

    private static OwnerExternalizable generateOwnerExternalizable() {
        OwnerExternalizable ownerExternalizable = new OwnerExternalizable();
        ownerExternalizable.setName(generateString());
        ownerExternalizable.setOrganization(RND.nextBoolean());
        return ownerExternalizable;
    }

    private static AnimalWithMethods generateAnimalWithMethods() {
        AnimalWithMethods animal = new AnimalWithMethods();
        if (RND.nextBoolean()) {
            animal.setName(generateString());
        }
        if (RND.nextBoolean()) {
            animal.setAnimalType(AnimalType.values()[RND.nextInt(AnimalType.values().length)]);
        }
        animal.setCountLegs(RND.nextInt());
        animal.setDomesticated(RND.nextBoolean());
        animal.setHerbivore(RND.nextBoolean());
        if (RND.nextBoolean()) {
            animal.setOwnerWithMethods(generateOwnerWithMethods());
        }
        return animal;
    }

    private static OwnerWithMethods generateOwnerWithMethods() {
        OwnerWithMethods ownerWithMethods = new OwnerWithMethods();
        ownerWithMethods.setName(generateString());
        ownerWithMethods.setOrganization(RND.nextBoolean());
        return ownerWithMethods;
    }

    private static String generateString() {
        char[] chars = new char[RND.nextInt(100)];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (RND.nextInt('z' - '0') + '0');
        }
        return new String(chars);
    }

    private static void message(String serialization, long beforeSerialization, long afterSerialization, long size, long AfterDeserialization) {
        System.out.println(serialization);
        System.out.println("file size: " + size + " bytes");
        System.out.println("it took " + (afterSerialization - beforeSerialization) / 100 + "s to serialize");
        System.out.println("it took " + (AfterDeserialization - afterSerialization) / 100 + "s to deserialize");
        System.out.println();
    }
}