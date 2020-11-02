package ru.mail.polis.homework.io.objects;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SerializerTest {

    @Test
    public void defaultSerialize() {
        Animal cat = new Animal("cat", true, Animal.FoodPreferences.CARNIVOROUS, 15);
        Animal bear = new Animal("bear", false, Animal.FoodPreferences.CARNIVOROUS, 15);
        Animal giraffe = new Animal("giraffe", true, Animal.FoodPreferences.HERBIVOROUS, 15);
        Animal dog = new Animal("dog", true, Animal.FoodPreferences.OMNIVOROUS, 15);
        Animal wolf = new Animal("wolf", true, Animal.FoodPreferences.CARNIVOROUS, 15);
        Animal fox = new Animal("fox", true, Animal.FoodPreferences.CARNIVOROUS, 15);
        Animal hippo = new Animal("hippo", false, Animal.FoodPreferences.HERBIVOROUS, 15);
        Animal koala = new Animal("koala", false, Animal.FoodPreferences.HERBIVOROUS, 15);
        Animal horse = new Animal("horse", true, Animal.FoodPreferences.HERBIVOROUS, 15);
        Animal ferret = new Animal("ferret", true, Animal.FoodPreferences.OMNIVOROUS, 15);

        List<Animal> animals = Arrays.asList(cat, bear, giraffe, dog, wolf,
                fox, hippo, koala, horse, ferret);

        String fileName = "D:\\test.txt";

        Serializer serializer = new Serializer();
        try {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                serializer.defaultSerialize(animals, fileName);
            }
            long endTime = System.currentTimeMillis();

            System.out.println("TEST 1");
            System.out.println("Serialization took: " + (endTime - startTime) + " milliseconds");

            startTime = System.currentTimeMillis();
            List<Animal> deserializedAnimals = serializer.defaultDeserialize(fileName);
            endTime = System.currentTimeMillis();

            System.out.println("Deserialization took: " + (endTime - startTime) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length());

            file.delete();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void serializeWithExternalizable() {
        AnimalExternalizable cat = new AnimalExternalizable("cat", true, AnimalExternalizable.FoodPreferences.CARNIVOROUS, 15);
        AnimalExternalizable bear = new AnimalExternalizable("bear", false, AnimalExternalizable.FoodPreferences.CARNIVOROUS, 15);
        AnimalExternalizable giraffe = new AnimalExternalizable("giraffe", true, AnimalExternalizable.FoodPreferences.HERBIVOROUS, 15);
        AnimalExternalizable dog = new AnimalExternalizable("dog", true, AnimalExternalizable.FoodPreferences.OMNIVOROUS, 15);
        AnimalExternalizable wolf = new AnimalExternalizable("wolf", true, AnimalExternalizable.FoodPreferences.CARNIVOROUS, 15);
        AnimalExternalizable fox = new AnimalExternalizable("fox", true, AnimalExternalizable.FoodPreferences.CARNIVOROUS, 15);
        AnimalExternalizable hippo = new AnimalExternalizable("hippo", false, AnimalExternalizable.FoodPreferences.HERBIVOROUS, 15);
        AnimalExternalizable koala = new AnimalExternalizable("koala", false, AnimalExternalizable.FoodPreferences.HERBIVOROUS, 15);
        AnimalExternalizable horse = new AnimalExternalizable("horse", true, AnimalExternalizable.FoodPreferences.HERBIVOROUS, 15);
        AnimalExternalizable ferret = new AnimalExternalizable("ferret", true, AnimalExternalizable.FoodPreferences.OMNIVOROUS, 15);

        List<AnimalExternalizable> animals = Arrays.asList(cat, bear, giraffe, dog, wolf,
                fox, hippo, koala, horse, ferret);

        String fileName = "D:\\test.txt";

        Serializer serializer = new Serializer();
        try {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                serializer.serializeWithExternalizable(animals, fileName);
            }
            long endTime = System.currentTimeMillis();

            System.out.println("TEST 2");
            System.out.println("Serialization took: " + (endTime - startTime) + " milliseconds");

            startTime = System.currentTimeMillis();
            List<AnimalExternalizable> deserializedAnimals = serializer.deserializeWithExternalizable(fileName);
            endTime = System.currentTimeMillis();

            System.out.println("Deserialization took: " + (endTime - startTime) + " milliseconds");

            File file = new File(fileName);
            System.out.println("File size: " + file.length());

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
