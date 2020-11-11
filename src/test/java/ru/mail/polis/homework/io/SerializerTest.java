package ru.mail.polis.homework.io;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.AnimalExternalizable;
import ru.mail.polis.homework.io.objects.AnimalWithMethods;
import ru.mail.polis.homework.io.objects.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SerializerTest {
    private static final int COUNT_OBJECT = 10000;
    private static final Random randomClass = new Random();
    private static final String path = "./src/test/resources/file.src";
    private static final Serializer serializer = new Serializer();

    @Before
    public void deleteBefore(){
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void defaultSerializeTest(){
        try {
            Path p = Paths.get(path);
            List<Animal> animals = new ArrayList<>(COUNT_OBJECT);
            for (int i = 0; i < COUNT_OBJECT; i++) {
                animals.add(getAnimal(0));
            }
            long T1 = System.currentTimeMillis();
            serializer.defaultSerialize(animals, path);
            long T2 = System.currentTimeMillis();
            List<Animal> answer = serializer.defaultDeserialize(path);
            long T3 = System.currentTimeMillis();

            long size = Files.size(p);
            System.out.println("Default write: " + (T2 - T1));
            System.out.println("Default read: " + (T3 - T2));
            System.out.println("Size file: " + size/1024 );

            Assert.assertEquals(animals, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeWithMethodsTest(){
        try {
            Path p = Paths.get(path);
            List<AnimalWithMethods> animals = new ArrayList<>(COUNT_OBJECT);
            for (int i = 0; i < COUNT_OBJECT; i++) {
                animals.add(getAnimalWithMethods(0));
            }
            long T1 = System.currentTimeMillis();
            serializer.serializeWithMethods(animals, path);
            long T2 = System.currentTimeMillis();
            List<AnimalWithMethods> answer = serializer.deserializeWithMethods(path);
            long T3 = System.currentTimeMillis();

            long size = Files.size(p);
            System.out.println("Serialize with methods write: " + (T2 - T1));
            System.out.println("Serialize with methods read: " + (T3 - T2));
            System.out.println("Size file: " + size/1024 + "\n");

            Assert.assertEquals(animals, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializeExternalizableTest(){
        try {
            Path p = Paths.get(path);
            List<AnimalExternalizable> animals = new ArrayList<>(COUNT_OBJECT);
            for (int i = 0; i < COUNT_OBJECT; i++) {
                animals.add(getAnimalExternalizable(0));
            }
            long T1 = System.currentTimeMillis();
            serializer.serializeWithExternalizable(animals, path);
            long T2 = System.currentTimeMillis();
            List<AnimalExternalizable> answer = serializer.deserializeWithExternalizable(path);
            long T3 = System.currentTimeMillis();

            long size = Files.size(p);
            System.out.println("Serialize with externalizable write: " + (T2 - T1));
            System.out.println("Serialize with externalizable read: " + (T3 - T2));
            System.out.println("Size file: " + size/1024 + "\n");

            Assert.assertEquals(animals, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void delete(){
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Если передан 1 то возвращает родителя, если другое то возвращает сына с вставленными родителями
    private AnimalExternalizable getAnimalExternalizable(int of){
        int pick = randomClass.nextInt(AnimalExternalizable.Color.values().length);
        AnimalExternalizable.Color color = AnimalExternalizable.Color.values()[pick];

        int age = randomClass.nextInt();

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String name = sb.toString();

        List<AnimalExternalizable.Friend> friends = new ArrayList<>(3);
        friends.add(getAnimalExternalizableFriend());
        friends.add(getAnimalExternalizableFriend());
        friends.add(getAnimalExternalizableFriend());

        double strong = randomClass.nextDouble();
        if(of == 1) {
            return new AnimalExternalizable(age, name, color, friends, null, null, strong);
        }
        return new AnimalExternalizable(age, name, color, friends, getAnimalExternalizable(1), getAnimalExternalizable(1), strong);
    }

    //Если передан 1 то возвращает родителя, если другое то возвращает сына с вставленными родителями
    private AnimalWithMethods getAnimalWithMethods(int of){
        int pick = randomClass.nextInt(AnimalWithMethods.Color.values().length);
        AnimalWithMethods.Color color = AnimalWithMethods.Color.values()[pick];

        int age = randomClass.nextInt();

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String name = sb.toString();

        List<AnimalWithMethods.Friend> friends = new ArrayList<>(3);
        friends.add(getAnimalWithMethodsFriend());
        friends.add(getAnimalWithMethodsFriend());
        friends.add(getAnimalWithMethodsFriend());

        double strong = randomClass.nextDouble();
        if(of == 1) {
            return new AnimalWithMethods(age, name, color, friends, null, null, strong);
        }
        return new AnimalWithMethods(age, name, color, friends, getAnimalWithMethods(1), getAnimalWithMethods(1), strong);
    }

    //Если передан 1 то возвращает родителя, если другое то возвращает сына с вставленными родителями
    private Animal getAnimal(int of){
        int pick = randomClass.nextInt(Animal.Color.values().length);
        Animal.Color color = Animal.Color.values()[pick];

        int age = randomClass.nextInt();

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String name = sb.toString();

        List<Animal.Friend> friends = new ArrayList<>(3);
        friends.add(getAnimalFriend());
        friends.add(getAnimalFriend());
        friends.add(getAnimalFriend());

        double strong = randomClass.nextDouble();
        if(of == 1) {
            return new Animal(age, name, color, friends, null, null, strong);
        }
        return new Animal(age, name, color, friends, getAnimal(1), getAnimal(1), strong);
    }

    private Animal.Friend getAnimalFriend(){
        int pick = randomClass.nextInt(Animal.Color.values().length);
        Animal.Color color = Animal.Color.values()[pick];

        int age = randomClass.nextInt();

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();

        return new Animal.Friend(output, age, color);
    }

    private AnimalExternalizable.Friend getAnimalExternalizableFriend(){
        int pick = randomClass.nextInt(AnimalExternalizable.Color.values().length);
        AnimalExternalizable.Color color = AnimalExternalizable.Color.values()[pick];

        int age = randomClass.nextInt();

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();

        return new AnimalExternalizable.Friend(output, age, color);
    }

    private AnimalWithMethods.Friend getAnimalWithMethodsFriend(){
        int pick = randomClass.nextInt(AnimalWithMethods.Color.values().length);
        AnimalWithMethods.Color color = AnimalWithMethods.Color.values()[pick];

        int age = randomClass.nextInt();

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();

        return new AnimalWithMethods.Friend(output, age, color);
    }

}
