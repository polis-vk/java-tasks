package ru.mail.polis.homework.oop.vet;

import java.util.*;

public class Main {
    public static Animal[] toArray(List<Animal> list)
    {
        int index = 0;
        Animal[] animals = new Animal[list.size()];
        for(var a : list)
        {
            animals[index++] = a;
        }
        return animals;
    }

    public static void main(String[] args)
    {
        List<Animal> list = new ArrayList<>();
        list.add(GeneratorAnimal.generateAnimal("cat"));
        list.add(GeneratorAnimal.generateAnimal("dog"));
        list.add(GeneratorAnimal.generateAnimal("cow"));
        list.add(GeneratorAnimal.generateAnimal("kangaroo"));
        list.add(GeneratorAnimal.generateAnimal("pigeon"));
        list.add(GeneratorAnimal.generateAnimal("shark"));
        list.add(GeneratorAnimal.generateAnimal("snake"));
        list.add(GeneratorAnimal.generateAnimal("dragon"));
        Animal[] arr = Main.toArray(list);
        for(var a : arr) System.out.println(a.say());
        System.out.println();
        Arrays.sort(arr, Comparator.comparing(Animal::say, (first, second) -> first.length()-second.length()));
        for(var a : arr) System.out.println(a.say());
    }

}
