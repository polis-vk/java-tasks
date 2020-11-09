package ru.mail.polis.homework.io;

import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.AnimalExternalizable;
import ru.mail.polis.homework.io.objects.AnimalWithMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SerializerTest {
    private static Random randomClass = new Random();



    //Если передан 1 то возвращает родителя, если другое то возвращает сына с вставленными родителями
    public AnimalExternalizable getAnimalExternalizable(int of){
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
    public AnimalWithMethods getAnimalWithMethods(int of){
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
    public Animal getAnimal(int of){
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

    public Animal.Friend getAnimalFriend(){
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

    public AnimalExternalizable.Friend getAnimalExternalizableFriend(){
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

    public AnimalWithMethods.Friend getAnimalWithMethodsFriend(){
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
