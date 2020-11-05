package ru.mail.polis.homework.objects;

import ru.mail.polis.homework.io.objects.Animal;

import java.util.ArrayList;

public class Data {
    public ArrayList<Animal> getAnimalList() {

        ArrayList<String> arr = new ArrayList<String>();
        arr.add("Russia");
        arr.add("Finland");
        Animal an = new Animal(arr, "dog", (short) 10, Animal.Gender.FEMALE, true);

        ArrayList<String> arr1 = new ArrayList<String>();
        arr1.add("UK");
        arr1.add("US");
        Animal an1 = new Animal(arr1, "cat", (short) 0, Animal.Gender.MALE, true);

        ArrayList<Animal> childs = new ArrayList<>();
        childs.add(an);
        childs.add(an1);
        ArrayList<String> arr2 = new ArrayList<String>();
        arr2.add("Japan");
        arr2.add("France");
        Animal an2 = new Animal(arr2, "mouse", (short) 12, Animal.Gender.NEUTRAL, false, childs);

        ArrayList<String> arr3 = new ArrayList<String>();
        arr3.add("asd");
        Animal an3 = new Animal(arr, "eagle", (short) 3, Animal.Gender.MAYBE_FEMALE, false);

        ArrayList<String> arr4 = new ArrayList<String>();
        arr4.add("ahaweh");
        Animal an4 = new Animal(arr, "eagle", (short) 32, Animal.Gender.MAYBE_MALE, false);

        ArrayList<String> arr5 = new ArrayList<String>();
        arr5.add("asghrghwh");
        Animal an5 = new Animal(arr, "eshshagle", (short) 63, Animal.Gender.MAYBE_FEMALE, true);

        ArrayList<String> arr6 = new ArrayList<String>();
        arr6.add("fhkrtyegh");
        Animal an6 = new Animal(arr, "shryjtyj", (short) 0, Animal.Gender.NEUTRAL, true);

        ArrayList<String> arr7 = new ArrayList<String>();
        arr7.add("dfgjegfhe");
        Animal an7 = new Animal(arr, "easdhwgle", (short) 1, Animal.Gender.MALE, false);

        ArrayList<String> arr8 = new ArrayList<String>();
        arr8.add("sdfhwrh");
        Animal an8 = new Animal(arr, "sdw", (short) 68, Animal.Gender.MAYBE_FEMALE, true);

        ArrayList<String> arr9 = new ArrayList<String>();
        arr9.add("asdhsdhsd");
        Animal an9 = new Animal(arr, "hsdg", (short) 4, Animal.Gender.FEMALE, false);


        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(an);
        animals.add(an1);
        animals.add(an2);
        animals.add(an3);
        animals.add(an4);
        animals.add(an5);
        animals.add(an6);
        animals.add(an7);
        animals.add(an8);
        animals.add(an9);
        return animals;
    }

}
