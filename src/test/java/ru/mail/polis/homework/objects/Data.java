package ru.mail.polis.homework.objects;

import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.AnimalExternalizable;
import ru.mail.polis.homework.io.objects.AnimalWithMethods;
import ru.mail.polis.homework.io.objects.Gender;

import java.util.ArrayList;
import java.util.Random;

public class Data {
    private final Random r = new Random();
    private final int stringSize = 10;
    private int N;

    Data(int N) {
        this.N = N;
    }

    private ArrayList<String> getRandomHabitat() {
        ArrayList<String> retList = new ArrayList<String>();
        retList.add(getRandomString(stringSize));
        return retList;
    }

    private String getRandomString(int size) {
        return r.ints('a', 'z' + 1)
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private short getRandomAge(short min, short max) {
        return (short) r.ints(min, (max + 1))
                .findFirst()
                .getAsInt();
    }

    private Gender getRandomGender() {
        return Gender.values()[r.nextInt(Gender.values().length)];
    }

    private boolean getRandomRealExistence() {
        return r.nextBoolean();
    }

    public ArrayList<Animal> getAnimalList() {
        ArrayList<Animal> animals = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            animals.add(new Animal(getRandomHabitat(), getRandomString(stringSize), getRandomAge((short) 0, (short) 300), getRandomGender(), getRandomRealExistence()));
        }
        return animals;
    }

    public ArrayList<AnimalWithMethods> getAnimalWithMethodsList() {
        ArrayList<AnimalWithMethods> animals = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            animals.add(new AnimalWithMethods(getRandomHabitat(), getRandomString(stringSize), getRandomAge((short) 0, (short) 300), getRandomGender(), getRandomRealExistence()));
        }
        return animals;
    }

    public ArrayList<AnimalExternalizable> getAnimalExternalizableList() {
        ArrayList<AnimalExternalizable> animals = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            animals.add(new AnimalExternalizable(getRandomHabitat(), getRandomString(stringSize), getRandomAge((short) 0, (short) 300), getRandomGender(), getRandomRealExistence()));
        }
        return animals;
    }

}
