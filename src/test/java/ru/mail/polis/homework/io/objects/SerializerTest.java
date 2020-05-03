package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SerializerTest {

    private List<Animal> listAnimalBefore1;
    private List<Animal> listAnimalAfter1;

    private List<Animal> listAnimalBefore2;
    private List<Animal> listAnimalAfter2;


    public static List<Animal> makeList() {
        AnimalOwner animalOwner1 = new AnimalOwner("Petr", "1234567890");
        AnimalOwner animalOwner2 = new AnimalOwner("Vladimir", "+75555555");
        AnimalOwner animalOwner3 = new AnimalOwner("Maria", "893333121");
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("animal1", AnimalType.CAT, animalOwner1));
        list.add(new Animal("animal2", AnimalType.BIRD, animalOwner1));
        list.add(new Animal("animal3", AnimalType.DOG, animalOwner2));
        list.add(new Animal("animal4", AnimalType.DOG, animalOwner3));
        list.add(new Animal("animal5", AnimalType.CAT, animalOwner2));
        return list;
    }

    public static List<Animal> makeList2() {
        AnimalOwner animalOwner1 = new AnimalOwner("Fedor", "88005553535");
        AnimalOwner animalOwner2 = new AnimalOwner("Vladimir", "+75555555");
        AnimalOwner animalOwner3 = new AnimalOwner("Elena", "893333121");
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("animal6", AnimalType.CAT, animalOwner1));
        list.add(new Animal("animal7", AnimalType.DOG, animalOwner1));
        list.add(new Animal("animal8", AnimalType.BIRD, animalOwner2));
        list.add(new Animal("animal9", AnimalType.CAT, animalOwner2));
        list.add(new Animal("animal10", AnimalType.DOG, animalOwner3));
        return list;
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "amimals").toFile());
    }

    @Test
    public void defaultSerializeOnlyList() throws IOException, ClassNotFoundException {
        listAnimalBefore1 = makeList();
        Serializer.defaultSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(Serializer.defaultDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));
        for (int i = 0; i < 5; i++) {
            assertEquals(listAnimalBefore1.get(i).getName(), listAnimalAfter1.get(i).getName());
            assertEquals(listAnimalBefore1.get(i).getType(), listAnimalAfter1.get(i).getType());
            assertEquals(listAnimalBefore1.get(i).getOwner().getName(), listAnimalAfter1.get(i).getOwner().getName());
            assertEquals(listAnimalBefore1.get(i).getOwner().getPhoneNumber(), listAnimalAfter1.get(i).getOwner().getPhoneNumber());
        }

    }

    @Test
    public void defaultSerializeEmptyList() throws IOException, ClassNotFoundException {
        listAnimalBefore1 = new ArrayList<>();
        Serializer.defaultSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(Serializer.defaultDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        assertEquals(listAnimalBefore1.isEmpty(), listAnimalAfter1.isEmpty());
    }

    @Test
    public void defaultSerializeManyList() throws IOException, ClassNotFoundException {
        listAnimalBefore1 = makeList();
        listAnimalBefore2 = makeList2();
        Serializer.defaultSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(Serializer.defaultDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        Serializer.defaultSerialize(listAnimalBefore2, Paths.get("src", "test", "resources", "amimals", "animals2.bin").toString());
        listAnimalAfter2 = new ArrayList<>(Serializer.defaultDeserialize(Paths.get("src", "test", "resources", "amimals", "animals2.bin").toString()));

        for (int i = 0; i < 5; i++) {
            assertEquals(listAnimalBefore1.get(i).getName(), listAnimalAfter1.get(i).getName());
            assertEquals(listAnimalBefore1.get(i).getType(), listAnimalAfter1.get(i).getType());
            assertEquals(listAnimalBefore1.get(i).getOwner().getName(), listAnimalAfter1.get(i).getOwner().getName());
            assertEquals(listAnimalBefore1.get(i).getOwner().getPhoneNumber(), listAnimalAfter1.get(i).getOwner().getPhoneNumber());
        }

        for (int i = 0; i < 5; i++) {
            assertEquals(listAnimalBefore2.get(i).getName(), listAnimalAfter2.get(i).getName());
            assertEquals(listAnimalBefore2.get(i).getType(), listAnimalAfter2.get(i).getType());
            assertEquals(listAnimalBefore2.get(i).getOwner().getName(), listAnimalAfter2.get(i).getOwner().getName());
            assertEquals(listAnimalBefore2.get(i).getOwner().getPhoneNumber(), listAnimalAfter2.get(i).getOwner().getPhoneNumber());
        }

        for (int i = 0; i < 5; i++) {
            assertNotEquals(listAnimalBefore1.get(i).getName(), listAnimalAfter2.get(i).getName());
        }
    }


    @Test
    public void customSerializeOnlyList() throws IOException {
        listAnimalBefore1 = makeList();
        Serializer.customSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(Serializer.customDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));
        for (int i = 0; i < 5; i++) {
            assertEquals(listAnimalBefore1.get(i).getName(), listAnimalAfter1.get(i).getName());
            assertEquals(listAnimalBefore1.get(i).getType(), listAnimalAfter1.get(i).getType());
            assertEquals(listAnimalBefore1.get(i).getOwner().getName(), listAnimalAfter1.get(i).getOwner().getName());
            assertEquals(listAnimalBefore1.get(i).getOwner().getPhoneNumber(), listAnimalAfter1.get(i).getOwner().getPhoneNumber());
        }

    }

    @Test
    public void customSerializeEmptyList() throws IOException {
        listAnimalBefore1 = new ArrayList<>();
        Serializer.customSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(Serializer.customDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        assertEquals(listAnimalBefore1.isEmpty(), listAnimalAfter1.isEmpty());
    }


    @Test
    public void customSerializeManyList() throws IOException, ClassNotFoundException {
        listAnimalBefore1 = makeList();
        listAnimalBefore2 = makeList2();
        Serializer.customSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(Serializer.customDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        Serializer.customSerialize(listAnimalBefore2, Paths.get("src", "test", "resources", "amimals", "animals2.bin").toString());
        listAnimalAfter2 = new ArrayList<>(Serializer.customDeserialize(Paths.get("src", "test", "resources", "amimals", "animals2.bin").toString()));

        for (int i = 0; i < 5; i++) {
            assertEquals(listAnimalBefore1.get(i).getName(), listAnimalAfter1.get(i).getName());
            assertEquals(listAnimalBefore1.get(i).getType(), listAnimalAfter1.get(i).getType());
            assertEquals(listAnimalBefore1.get(i).getOwner().getName(), listAnimalAfter1.get(i).getOwner().getName());
            assertEquals(listAnimalBefore1.get(i).getOwner().getPhoneNumber(), listAnimalAfter1.get(i).getOwner().getPhoneNumber());
        }

        for (int i = 0; i < 5; i++) {
            assertEquals(listAnimalBefore2.get(i).getName(), listAnimalAfter2.get(i).getName());
            assertEquals(listAnimalBefore2.get(i).getType(), listAnimalAfter2.get(i).getType());
            assertEquals(listAnimalBefore2.get(i).getOwner().getName(), listAnimalAfter2.get(i).getOwner().getName());
            assertEquals(listAnimalBefore2.get(i).getOwner().getPhoneNumber(), listAnimalAfter2.get(i).getOwner().getPhoneNumber());
        }

        for (int i = 0; i < 5; i++) {
            assertNotEquals(listAnimalBefore1.get(i).getName(), listAnimalAfter2.get(i).getName());
        }
    }
}