package ru.mail.polis.homework.objects;

import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.AnimalExternalizable;
import ru.mail.polis.homework.io.objects.AnimalWithMethods;

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

    public ArrayList<AnimalWithMethods> getAnimalWithMethodsList() {

        ArrayList<String> arr = new ArrayList<String>();
        arr.add("Russia");
        arr.add("Finland");
        AnimalWithMethods an = new AnimalWithMethods(arr, "dog", (short) 10, AnimalWithMethods.Gender.FEMALE, true);

        ArrayList<String> arr1 = new ArrayList<String>();
        arr1.add("UK");
        arr1.add("US");
        AnimalWithMethods an1 = new AnimalWithMethods(arr1, "cat", (short) 0, AnimalWithMethods.Gender.MALE, true);

        ArrayList<AnimalWithMethods> childs = new ArrayList<>();
        childs.add(an);
        childs.add(an1);
        ArrayList<String> arr2 = new ArrayList<String>();
        arr2.add("Japan");
        arr2.add("France");
        AnimalWithMethods an2 = new AnimalWithMethods(arr2, "mouse", (short) 12, AnimalWithMethods.Gender.NEUTRAL, false, childs);

        ArrayList<String> arr3 = new ArrayList<String>();
        arr3.add("asd");
        AnimalWithMethods an3 = new AnimalWithMethods(arr, "eagle", (short) 3, AnimalWithMethods.Gender.MAYBE_FEMALE, false);

        ArrayList<String> arr4 = new ArrayList<String>();
        arr4.add("ahaweh");
        AnimalWithMethods an4 = new AnimalWithMethods(arr, "eagle", (short) 32, AnimalWithMethods.Gender.MAYBE_MALE, false);

        ArrayList<String> arr5 = new ArrayList<String>();
        arr5.add("asghrghwh");
        AnimalWithMethods an5 = new AnimalWithMethods(arr, "eshshagle", (short) 63, AnimalWithMethods.Gender.MAYBE_FEMALE, true);

        ArrayList<String> arr6 = new ArrayList<String>();
        arr6.add("fhkrtyegh");
        AnimalWithMethods an6 = new AnimalWithMethods(arr, "shryjtyj", (short) 0, AnimalWithMethods.Gender.NEUTRAL, true);

        ArrayList<String> arr7 = new ArrayList<String>();
        arr7.add("dfgjegfhe");
        AnimalWithMethods an7 = new AnimalWithMethods(arr, "easdhwgle", (short) 1, AnimalWithMethods.Gender.MALE, false);

        ArrayList<String> arr8 = new ArrayList<String>();
        arr8.add("sdfhwrh");
        AnimalWithMethods an8 = new AnimalWithMethods(arr, "sdw", (short) 68, AnimalWithMethods.Gender.MAYBE_FEMALE, true);

        ArrayList<String> arr9 = new ArrayList<String>();
        arr9.add("asdhsdhsd");
        AnimalWithMethods an9 = new AnimalWithMethods(arr, "hsdg", (short) 4, AnimalWithMethods.Gender.FEMALE, false);


        ArrayList<AnimalWithMethods> animals = new ArrayList<>();
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

    public ArrayList<AnimalExternalizable> getAnimalExternalizableList() {

        ArrayList<String> arr = new ArrayList<String>();
        arr.add("Russia");
        arr.add("Finland");
        AnimalExternalizable an = new AnimalExternalizable(arr, "dog", (short) 10, AnimalExternalizable.Gender.FEMALE, true);

        ArrayList<String> arr1 = new ArrayList<String>();
        arr1.add("UK");
        arr1.add("US");
        AnimalExternalizable an1 = new AnimalExternalizable(arr1, "cat", (short) 0, AnimalExternalizable.Gender.MALE, true);

        ArrayList<AnimalExternalizable> childs = new ArrayList<>();
        childs.add(an);
        childs.add(an1);
        ArrayList<String> arr2 = new ArrayList<String>();
        arr2.add("Japan");
        arr2.add("France");
        AnimalExternalizable an2 = new AnimalExternalizable(arr2, "mouse", (short) 12, AnimalExternalizable.Gender.NEUTRAL, false, childs);

        ArrayList<String> arr3 = new ArrayList<String>();
        arr3.add("asd");
        AnimalExternalizable an3 = new AnimalExternalizable(arr, "eagle", (short) 3, AnimalExternalizable.Gender.MAYBE_FEMALE, false);

        ArrayList<String> arr4 = new ArrayList<String>();
        arr4.add("ahaweh");
        AnimalExternalizable an4 = new AnimalExternalizable(arr, "eagle", (short) 32, AnimalExternalizable.Gender.MAYBE_MALE, false);

        ArrayList<String> arr5 = new ArrayList<String>();
        arr5.add("asghrghwh");
        AnimalExternalizable an5 = new AnimalExternalizable(arr, "eshshagle", (short) 63, AnimalExternalizable.Gender.MAYBE_FEMALE, true);

        ArrayList<String> arr6 = new ArrayList<String>();
        arr6.add("fhkrtyegh");
        AnimalExternalizable an6 = new AnimalExternalizable(arr, "shryjtyj", (short) 0, AnimalExternalizable.Gender.NEUTRAL, true);

        ArrayList<String> arr7 = new ArrayList<String>();
        arr7.add("dfgjegfhe");
        AnimalExternalizable an7 = new AnimalExternalizable(arr, "easdhwgle", (short) 1, AnimalExternalizable.Gender.MALE, false);

        ArrayList<String> arr8 = new ArrayList<String>();
        arr8.add("sdfhwrh");
        AnimalExternalizable an8 = new AnimalExternalizable(arr, "sdw", (short) 68, AnimalExternalizable.Gender.MAYBE_FEMALE, true);

        ArrayList<String> arr9 = new ArrayList<String>();
        arr9.add("asdhsdhsd");
        AnimalExternalizable an9 = new AnimalExternalizable(arr, "hsdg", (short) 4, AnimalExternalizable.Gender.FEMALE, false);


        ArrayList<AnimalExternalizable> animals = new ArrayList<>();
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
