package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    private int age;
    private int HP;

    private String kind;
    private AnimalClass animalClass;
    private Sex sex;

    private List<Part> parts;

    private static final String[] NAMES_OF_KINDS = new String[] {"wolf", "fox", "cat", "squirrel"};

    public Animal(int age, String kind, AnimalClass animalClass, Sex sex, List<Part> parts) {
        this.age = age;
        this.kind = kind;
        this.animalClass = animalClass;
        this.sex = sex;
        this.parts = parts;
        for (Part part : parts) {
            this.HP += part.getHealthPoint();
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public AnimalClass getAnimalClass() {
        return animalClass;
    }

    public void setAnimalClass(AnimalClass animalClass) {
        this.animalClass = animalClass;
    }

    public Sex getSex() {
        return sex;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public static List<Animal> getRandomAnimals(int size) {
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            animals.add(getRandomAnimal());
        }
        return animals;
    }

    public static Animal getRandomAnimal() {
        Random random = new Random();
        return new Animal(
                random.nextInt(10),
                NAMES_OF_KINDS[random.nextInt(NAMES_OF_KINDS.length)],
                AnimalClass.getRandomAnimalClass(),
                Sex.getRandomSex(),
                Part.getRandomParts(4)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age && HP == animal.HP && Objects.equals(kind, animal.kind) && animalClass == animal.animalClass && sex == animal.sex && Objects.equals(parts, animal.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, HP, kind, animalClass, sex, parts);
    }
}
