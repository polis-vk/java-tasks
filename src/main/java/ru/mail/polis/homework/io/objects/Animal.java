package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private String name;
    private int age;
    private Gender gender;
    private Type type;
    private Place home;
    private List<Ability> abilities;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(ru.mail.polis.homework.io.objects.Gender gender) {
        this.gender = gender;
    }

    public void setType(ru.mail.polis.homework.io.objects.Type type) {
        this.type = type;
    }

    public void setHome(Place home) {
        this.home = home;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ru.mail.polis.homework.io.objects.Gender getGender() {
        return gender;
    }

    public ru.mail.polis.homework.io.objects.Type getType() {
        return type;
    }

    public Place getHome() {
        return home;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public static List<Animal> randomListOfAnimals(int size) {
        Random random = new Random(System.currentTimeMillis());
        List<Animal> res = new ArrayList<>();
        String[] names = {"Коля", "Миша", "Марат", "Жучка", "Шарик", "Муся"};
        String[] countryNames = {"Россия", "Украина", "Эквадор", "Германия", "США"};
        for (int i = 0; i < size; i++) {
            Animal animal = new Animal();
            animal.setName(names[random.nextInt(names.length)]);
            animal.setAge(random.nextInt(100));
            animal.setGender(ru.mail.polis.homework.io.objects.Gender.values()[random.nextInt(ru.mail.polis.homework.io.objects.Gender.values().length)]);
            animal.setType(ru.mail.polis.homework.io.objects.Type.values()[random.nextInt(ru.mail.polis.homework.io.objects.Type.values().length)]);
            animal.setHome(
                    new Place(countryNames[random.nextInt(countryNames.length)],
                            random.nextDouble(),
                            random.nextDouble())
            );
            animal.setAbilities(Ability.randomListOfAbilities());
            res.add(animal);
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Objects.equals(name, animal.name) &&
                gender == animal.gender &&
                type == animal.type &&
                Objects.equals(home, animal.home) &&
                Objects.equals(abilities, animal.abilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gender, type, home, abilities);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", type=" + type +
                ", home=" + home +
                ", abilities=" + abilities +
                '}';
    }
}
