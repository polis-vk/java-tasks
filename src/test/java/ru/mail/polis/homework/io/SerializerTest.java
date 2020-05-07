package ru.mail.polis.homework.io;

import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.Breed;
import ru.mail.polis.homework.io.objects.Cattery;
import ru.mail.polis.homework.io.objects.Serializer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SerializerTest {

    @Test
    public void emptyListDefaultTest() {
        Path filePath = Paths.get("src", "test", "resources", "serializer.txt");
        List<Animal> animalList = Collections.emptyList();

        Serializer serializer = new Serializer();
        serializer.defaultSerialize(animalList, filePath.toString());

        List<Animal> risenAnimals = serializer.defaultDeserialize(filePath.toString());

        Assert.assertTrue(risenAnimals.isEmpty());
    }

    @Test
    public void emptyListCustomTest() {
        Path filePath = Paths.get("src", "test", "resources", "serializer.txt");
        List<Animal> animalList = Collections.emptyList();

        Serializer serializer = new Serializer();
        serializer.customSerialize(animalList, filePath.toString());

        List<Animal> risenAnimals = serializer.customDeserialize(filePath.toString());

        Assert.assertTrue(risenAnimals.isEmpty());
    }

    @Test
    public void OneListDefaultTest() {
        Path filePath = Paths.get("src", "test", "resources", "serializer.txt");
        List<Animal> animalList = new ArrayList<>();
        animalList.add(new Animal("Masyanya", true, Breed.SPHYNX,
                new Cattery("St.Petersburg Cats Inc.", "Nevsky ave")));
        animalList.add(new Animal("Kira", true, Breed.BRITISH_SHORTHAIR,
                new Cattery("Cat Asylum", "Super secret address")));

        Serializer serializer = new Serializer();
        serializer.defaultSerialize(animalList, filePath.toString());

        List<Animal> risenAnimals = serializer.defaultDeserialize(filePath.toString());

        Assert.assertEquals(animalList, risenAnimals);

    }

    @Test
    public void OneListCustomTest() {
        Path filePath = Paths.get("src", "test", "resources", "serializer.txt");
        List<Animal> animalList = new ArrayList<>();
        animalList.add(new Animal("Masyanya", true, Breed.SPHYNX,
                new Cattery("St.Petersburg Cats Inc.", "Nevsky ave")));
        animalList.add(new Animal("Kira", true, Breed.BRITISH_SHORTHAIR,
                new Cattery("Cat Asylum", "Super secret address")));

        Serializer serializer = new Serializer();
        serializer.customSerialize(animalList, filePath.toString());

        List<Animal> risenAnimals = serializer.customDeserialize(filePath.toString());

        Assert.assertEquals(animalList, risenAnimals);
    }

    @Test
    public void SeveralListsDefaultTest() {
        Path file1Path = Paths.get("src", "test", "resources", "cats1.txt");
        Path file2Path = Paths.get("src", "test", "resources", "cats2.txt");

        List<Animal> cats1List = new ArrayList<>();
        List<Animal> cats2List = new ArrayList<>();

        cats1List.add(new Animal("Applejack", false, Breed.SPHYNX,
                new Cattery("St.Petersburg Cats Inc.", "Nevsky ave")));
        cats1List.add(new Animal("Fluttershy", false, Breed.BRITISH_SHORTHAIR,
                new Cattery("Cat Asylum", "Super secret address")));
        cats1List.add(new Animal("Barsik", false, Breed.MAINE_COON,
                new Cattery("Barsik's Shelter", "Basement")));

        cats2List.add(new Animal("Rainbow Dash", false, Breed.SPHYNX,
                new Cattery("MyLittleKitty", "KittenLand")));
        cats2List.add(new Animal("Rarity", false, Breed.BRITISH_SHORTHAIR,
                new Cattery("Equestrian cats", "Elsweyr")));
        cats2List.add(new Animal("Arthur", false, Breed.MAINE_COON,
                new Cattery("Catterlot", "Cats Empire")));

        Serializer serializer = new Serializer();
        serializer.defaultSerialize(cats1List, file1Path.toString());
        serializer.defaultSerialize(cats2List, file2Path.toString());

        List<Animal> cats1DeserializedList = serializer.defaultDeserialize(file1Path.toString());
        List<Animal> cats2DeserializedList = serializer.defaultDeserialize(file2Path.toString());

        Assert.assertEquals(cats1List, cats1DeserializedList);
        Assert.assertEquals(cats2List, cats2DeserializedList);

        Assert.assertNotEquals(cats1List, cats2DeserializedList);
        Assert.assertNotEquals(cats2List, cats1DeserializedList);

    }
}
