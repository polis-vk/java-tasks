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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.junit.Assert.*;

public class SerializerTest {
    private Serializer serializer;

    @Before
    public void setUp() throws IOException {
        serializer = new Serializer();
        Path directory = Paths.get("src", "test", "resources", "serializer");
        Files.createDirectories(directory);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "serializer").toFile());
    }


    @Test
    public void emptyListTest() throws IOException {
        emptyList(serializer::defaultSerialize, serializer::defaultDeserialize);
        emptyList(serializer::customSerialize, serializer::customDeserialize);
    }

    @Test
    public void oneListTest() throws IOException {
        oneList(serializer::defaultSerialize, serializer::defaultDeserialize);
        oneList(serializer::customSerialize, serializer::customDeserialize);
    }

    @Test
    public void hardListTest() throws IOException {
        manyList(serializer::defaultSerialize, serializer::defaultDeserialize);
        manyList(serializer::customSerialize, serializer::customDeserialize);
    }


    private void emptyList(BiConsumer<List<Animal>, String> serialize, Function<String, List<Animal>> deserialize) throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal1.bin");
        List<Animal> animals = new ArrayList<>();
        serialize.accept(animals, file.toString());
        List<Animal> animalsRes = deserialize.apply(file.toString());
        assertTrue(animalsRes.isEmpty());
    }

    private void oneList(BiConsumer<List<Animal>, String> serialize, Function<String, List<Animal>> deserialize) throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "animal2.bin");
        List<Animal> animals = new ArrayList<>();
        List<Habitat> habitats = new ArrayList<Habitat>();
        habitats.add(new Habitat("Russia", 1200, "Cold"));
        habitats.add(new Habitat("Japan", 300, "Warm"));
        animals.add(new Animal("Asd", 10, Species.CAT, habitats));
        animals.add(new Animal("fggbm", 15, Species.DOG, habitats));
        animals.add(new Animal("D", 345, Species.WOLF, habitats));
        animals.add(new Animal("o", 1, Species.FOX, habitats));

        serialize.accept(animals, file.toString());
        List<Animal> animalsRes = deserialize.apply(file.toString());
        assertEquals(animals, animalsRes);
    }

    private void manyList(BiConsumer<List<Animal>, String> serialize, Function<String, List<Animal>> deserialize) throws IOException {
        Path file1 = Paths.get("src", "test", "resources", "serializer", "animal3.bin");
        Path file2 = Paths.get("src", "test", "resources", "serializer", "animal4.bin");
        Path file3 = Paths.get("src", "test", "resources", "serializer", "animal5.bin");
        Path file4 = Paths.get("src", "test", "resources", "serializer", "animal6.bin");


        List<Habitat> habitats = new ArrayList<Habitat>();
        habitats.add(new Habitat("Russia", 1200, "Cold"));
        habitats.add(new Habitat("Japan", 300, "Warm"));
        Animal animal1 = new Animal("Asd", 10, Species.CAT, habitats);
        Animal animal2 = new Animal("fggbm", 15, Species.DOG, habitats);
        Animal animal3 = new Animal("D", 345, Species.WOLF, habitats);
        Animal animal4 = new Animal("o", 1, Species.FOX, habitats);

        List<Animal> animals1 = new ArrayList<>();
        animals1.add(animal1);
        animals1.add(animal2);
        animals1.add(animal3);
        animals1.add(animal4);
        List<Animal> animals2 = new ArrayList<>();
        animals1.add(animal2);
        animals1.add(animal3);
        animals1.add(animal4);
        List<Animal> animals3 = new ArrayList<>();
        animals1.add(animal3);
        animals1.add(animal4);
        List<Animal> animals4 = new ArrayList<>();
        animals1.add(animal4);

        serialize.accept(animals1, file1.toString());
        List<Animal> animalsRes1 = deserialize.apply(file1.toString());
        assertEquals(animals1, animalsRes1);

        serialize.accept(animals2, file2.toString());
        List<Animal> animalsRes2 = deserialize.apply(file2.toString());
        assertEquals(animals2, animalsRes2);

        serialize.accept(animals3, file3.toString());
        List<Animal> animalsRes3 = deserialize.apply(file3.toString());
        assertEquals(animals3, animalsRes3);

        serialize.accept(animals4, file4.toString());
        List<Animal> animalsRes4 = deserialize.apply(file4.toString());
        assertEquals(animals4, animalsRes4);

        assertNotEquals(animals1, animals3);
        assertNotEquals(animalsRes1, animalsRes3);
    }

}
