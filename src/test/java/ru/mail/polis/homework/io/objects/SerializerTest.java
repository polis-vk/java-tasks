package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SerializerTest {

    private List<Animal> listAnimalBefore1;
    private List<Animal> listAnimalAfter1;

    private List<Animal> listAnimalBefore2;
    private List<Animal> listAnimalAfter2;

    private Serializer serializer = new Serializer();

    public static List<Animal> makeList() {
        AnimalOwner animalOwner1 = new AnimalOwner("Petr", "1234567890");
        AnimalOwner animalOwner2 = new AnimalOwner("Vladimir", "+75555555");
        AnimalOwner animalOwner3 = new AnimalOwner("Maria", "893333121");
        List<String> listFood1 = new ArrayList(Arrays.asList("potato"));
        List<String> listFood2 = new ArrayList(Arrays.asList("potato","carrot"));
        List<String> listFood3 = new ArrayList(Arrays.asList("meat","milk"));
        List<Animal> list = new ArrayList();
        list.add(new Animal("animal1", AnimalType.CAT, animalOwner1, listFood1));
        list.add(new Animal("animal2", AnimalType.BIRD, animalOwner1, listFood3));
        list.add(new Animal("animal3", AnimalType.DOG, animalOwner2, listFood2));
        list.add(new Animal("animal4", AnimalType.DOG, animalOwner3, listFood2));
        list.add(new Animal("animal5", AnimalType.CAT, animalOwner2, listFood1));
        return list;
    }

    public static List<Animal> makeList2() {
        AnimalOwner animalOwner1 = new AnimalOwner("Fedor", "88005553535");
        AnimalOwner animalOwner2 = new AnimalOwner("Vladimir", "+75555555");
        AnimalOwner animalOwner3 = new AnimalOwner("Elena", "893333121");
        List<String> listFood1 = new ArrayList(Arrays.asList("juice"));
        List<String> listFood2 = new ArrayList(Arrays.asList("tomato","carrot"));
        List<String> listFood3 = new ArrayList(Arrays.asList("cheese","milk"));
        List<Animal> list = new ArrayList();
        list.add(new Animal("animal6", AnimalType.CAT, animalOwner1, listFood1));
        list.add(new Animal("animal7", AnimalType.DOG, animalOwner1, listFood1));
        list.add(new Animal("animal8", AnimalType.BIRD, animalOwner2, listFood3));
        list.add(new Animal("animal9", AnimalType.CAT, animalOwner2, listFood3));
        list.add(new Animal("animal10", AnimalType.DOG, animalOwner3, listFood2));
        return list;
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "amimals").toFile());
    }

    @Test
    public void defaultSerializeOnlyList() {
        listAnimalBefore1 = makeList();
        serializer.defaultSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(serializer.defaultDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        assertEquals(listAnimalBefore1, listAnimalAfter1);
    }

    @Test
    public void defaultSerializeEmptyList() {
        listAnimalBefore1 = new ArrayList<>();
        serializer.defaultSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(serializer.defaultDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        assertEquals(listAnimalBefore1.isEmpty(), listAnimalAfter1.isEmpty());
    }

    @Test
    public void defaultSerializeManyList() {
        listAnimalBefore1 = makeList();
        listAnimalBefore2 = makeList2();
        serializer.defaultSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(serializer.defaultDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        serializer.defaultSerialize(listAnimalBefore2, Paths.get("src", "test", "resources", "amimals", "animals2.bin").toString());
        listAnimalAfter2 = new ArrayList<>(serializer.defaultDeserialize(Paths.get("src", "test", "resources", "amimals", "animals2.bin").toString()));

        assertEquals(listAnimalBefore1, listAnimalAfter1);
        assertEquals(listAnimalBefore2, listAnimalAfter2);
        assertNotEquals(listAnimalBefore1, listAnimalAfter2);

    }


    @Test
    public void customSerializeOnlyList() {
        listAnimalBefore1 = makeList();
        serializer.customSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(serializer.customDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        assertEquals(listAnimalBefore1, listAnimalAfter1);

    }

    @Test
    public void customSerializeEmptyList() {
        listAnimalBefore1 = new ArrayList<>();
        serializer.customSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(serializer.customDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        assertEquals(listAnimalBefore1.isEmpty(), listAnimalAfter1.isEmpty());
    }


    @Test
    public void customSerializeManyList() {
        listAnimalBefore1 = makeList();
        listAnimalBefore2 = makeList2();
        serializer.customSerialize(listAnimalBefore1, Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString());
        listAnimalAfter1 = new ArrayList<>(serializer.customDeserialize(Paths.get("src", "test", "resources", "amimals", "animals1.bin").toString()));

        serializer.customSerialize(listAnimalBefore2, Paths.get("src", "test", "resources", "amimals", "animals2.bin").toString());
        listAnimalAfter2 = new ArrayList<>(serializer.customDeserialize(Paths.get("src", "test", "resources", "amimals", "animals2.bin").toString()));

        assertEquals(listAnimalBefore1, listAnimalAfter1);
        assertEquals(listAnimalBefore2, listAnimalAfter2);
        assertNotEquals(listAnimalBefore1, listAnimalAfter2);

    }
}