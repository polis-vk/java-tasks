package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static final Path DIR = Paths.get("src", "test", "resources", "objects");
    private static final Path DEFAULT_PATH = Paths.get("src", "test", "resources", "objects", "default.txt");
    private static final Path EXTERNALIZABLE_PATH = Paths.get("src", "test", "resources", "objects", "externalizable.txt");
    private static final Path WITH_METHODS_PATH = Paths.get("src", "test", "resources", "objects", "methods.txt");
    private static final Path CUSTOM_PATH = Paths.get("src", "test", "resources", "objects", "custom.txt");
    private static final Serializer SERIALIZER = new Serializer();
    private static final int ANIMALS_COUNT = 500000;


    @Before
    public void setUp() throws IOException {
        Files.createDirectories(DIR);
        Files.createFile(DEFAULT_PATH);
        Files.createFile(WITH_METHODS_PATH);
        Files.createFile(CUSTOM_PATH);
        Files.createFile(EXTERNALIZABLE_PATH);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(DEFAULT_PATH.toFile());
        FileUtils.forceDelete(EXTERNALIZABLE_PATH.toFile());
        FileUtils.forceDelete(CUSTOM_PATH.toFile());
        FileUtils.forceDelete(WITH_METHODS_PATH.toFile());
    }

    @Test
    public void defaultSerializationTest() throws Exception {
        List<Animal> generatedAnimals = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            generatedAnimals.add(generateAnimal());
        }

        long millisBeforeSerialization = System.currentTimeMillis();
        SERIALIZER.defaultSerialize(generatedAnimals, DEFAULT_PATH.toString());
        long millisAfterSerialization = System.currentTimeMillis();

        List<Animal> deserializedAnimals = SERIALIZER.defaultDeserialize(DEFAULT_PATH.toString());
        long millisAfterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(DEFAULT_PATH);
        assertEquals(generatedAnimals, deserializedAnimals);

        System.out.println("Простая (default) сериализация:");
        System.out.println("Размер файла: " + fileSize + " байт");
        System.out.println("Время записи: " + (millisAfterSerialization - millisBeforeSerialization) + " мс");
        System.out.println("Время чтения: " + (millisAfterDeserialization - millisAfterSerialization) + " мс\n");
    }

    @Test
    public void withMethodsSerializationTest() throws Exception {
        List<AnimalWithMethods> generatedAnimals = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            generatedAnimals.add(generateAnimalWithMethods());
        }

        long millisBeforeSerialization = System.currentTimeMillis();
        SERIALIZER.serializeWithMethods(generatedAnimals, DEFAULT_PATH.toString());
        long millisAfterSerialization = System.currentTimeMillis();

        List<AnimalWithMethods> deserializedAnimals = SERIALIZER.deserializeWithMethods(DEFAULT_PATH.toString());
        long millisAfterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(DEFAULT_PATH);
        assertEquals(generatedAnimals, deserializedAnimals);

        System.out.println("Простая ручная (with methods) сериализация:");
        System.out.println("Размер файла: " + fileSize + " байт");
        System.out.println("Время записи: " + (millisAfterSerialization - millisBeforeSerialization) + " мс");
        System.out.println("Время чтения: " + (millisAfterDeserialization - millisAfterSerialization) + " мс\n");
    }

    @Test
    public void withExternalizableSerializationTest() throws Exception {
        List<AnimalExternalizable> generatedAnimals = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            generatedAnimals.add(generateAnimalExternalizable());
        }

        long millisBeforeSerialization = System.currentTimeMillis();
        SERIALIZER.serializeWithExternalizable(generatedAnimals, DEFAULT_PATH.toString());
        long millisAfterSerialization = System.currentTimeMillis();

        List<AnimalExternalizable> deserializedAnimals = SERIALIZER.deserializeWithExternalizable(DEFAULT_PATH.toString());
        long millisAfterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(DEFAULT_PATH);
        assertEquals(generatedAnimals, deserializedAnimals);

        System.out.println("Простая ручная (externalizable) сериализация:");
        System.out.println("Размер файла: " + fileSize + " байт");
        System.out.println("Время записи: " + (millisAfterSerialization - millisBeforeSerialization) + " мс");
        System.out.println("Время чтения: " + (millisAfterDeserialization - millisAfterSerialization) + " мс\n");
    }

    @Test
    public void customSerializationTest() throws Exception {
        List<Animal> generatedAnimals = new ArrayList<>();
        for (int i = 0; i < ANIMALS_COUNT; i++) {
            generatedAnimals.add(generateAnimal());
        }

        long millisBeforeSerialization = System.currentTimeMillis();
        SERIALIZER.customSerialize(generatedAnimals, DEFAULT_PATH.toString());
        long millisAfterSerialization = System.currentTimeMillis();

        List<Animal> deserializedAnimals = SERIALIZER.customDeserialize(DEFAULT_PATH.toString());
        long millisAfterDeserialization = System.currentTimeMillis();

        long fileSize = Files.size(DEFAULT_PATH);
        assertEquals(generatedAnimals, deserializedAnimals);

        System.out.println("Ручная (custom) сериализация:");
        System.out.println("Размер файла: " + fileSize + " байт");
        System.out.println("Время записи: " + (millisAfterSerialization - millisBeforeSerialization) + " мс");
        System.out.println("Время чтения: " + (millisAfterDeserialization - millisAfterSerialization) + " мс\n");
    }

    private static final Random RND = new Random();

    private static Animal generateAnimal() {
        Animal animal = new Animal();
        if (RND.nextBoolean()) {
            animal.setAlias(generateString());
        }
        animal.setLegs(RND.nextInt());
        animal.setWild(RND.nextBoolean());
        animal.setFurry(RND.nextBoolean());
        if (RND.nextBoolean()) {
            animal.setOrganization(generateOrganization());
        }
        MoveType[] values = MoveType.values();
        animal.setMoveType(MoveType.values()[RND.nextInt(values.length)]);
        return animal;
    }

    private static AnimalExternalizable generateAnimalExternalizable() {
        AnimalExternalizable animal = new AnimalExternalizable();
        if (RND.nextBoolean()) {
            animal.setAlias(generateString());
        }
        animal.setLegs(RND.nextInt());
        animal.setWild(RND.nextBoolean());
        animal.setFurry(RND.nextBoolean());
        if (RND.nextBoolean()) {
            animal.setOrganization(generateOrganizationExternalizable());
        }
        MoveType[] values = MoveType.values();
        animal.setMoveType(MoveType.values()[RND.nextInt(values.length)]);
        return animal;
    }

    private static AnimalWithMethods generateAnimalWithMethods() {
        AnimalWithMethods animal = new AnimalWithMethods();
        if (RND.nextBoolean()) {
            animal.setAlias(generateString());
        }
        animal.setLegs(RND.nextInt());
        animal.setWild(RND.nextBoolean());
        animal.setFurry(RND.nextBoolean());
        if (RND.nextBoolean()) {
            animal.setOrganization(generateOrganizationWithMethods());
        }
        MoveType[] values = MoveType.values();
        animal.setMoveType(MoveType.values()[RND.nextInt(values.length)]);
        return animal;
    }

    private static Organization generateOrganization() {
        Organization organization = new Organization();
        organization.setName(generateString());
        if (RND.nextBoolean()) {
            organization.setOwner(generateString());
        }
        organization.setForeign(RND.nextBoolean());
        return organization;
    }

    private static OrganizationExternalizable generateOrganizationExternalizable() {
        OrganizationExternalizable organization = new OrganizationExternalizable();
        organization.setName(generateString());
        if (RND.nextBoolean()) {
            organization.setOwner(generateString());
        }
        organization.setForeign(RND.nextBoolean());
        return organization;
    }

    private static OrganizationWithMethods generateOrganizationWithMethods() {
        OrganizationWithMethods organization = new OrganizationWithMethods();
        organization.setName(generateString());
        if (RND.nextBoolean()) {
            organization.setOwner(generateString());
        }
        organization.setForeign(RND.nextBoolean());
        return organization;
    }


    private static String generateString() {
        char[] chars = new char[RND.nextInt(18) + 2];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (RND.nextInt('z' - '0') + '0');
        }
        return new String(chars);
    }
}
