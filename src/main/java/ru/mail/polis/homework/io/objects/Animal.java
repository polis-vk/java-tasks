package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.*;


/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private String name = "unknown";
    private int age;
    private Habitat habitat = new Habitat();
    private AnimalKind kind = AnimalKind.UNKNOWN;
    private List<String> familyMembersNames = new ArrayList<>();
    private Gender gender = Gender.UNKNOWN;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public void setKind(AnimalKind kind) {
        this.kind = kind;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public AnimalKind getKind() {
        return kind;
    }

    public Gender getGender() {
        return gender;
    }

    public List<String> getFamilyMembersNames() {
        return familyMembersNames;
    }

    public void setFamilyMembersNames(List<String> family) {
        this.familyMembersNames = family;
    }

    public void addFamilyMembersNames(String... memberName) {
        Animal.this.familyMembersNames.addAll(Arrays.asList(memberName));
    }

    public Animal getRandomAnimal(Random random) {
        setAge(random.nextInt(199));
        setGender(Gender.getRandom(random));
        setHabitat(new Habitat(ClimateZone.getRandom(random), Mainland.getRandom(random)));
        setKind(AnimalKind.getRandom(random));
        setName(Utils.getRandomString(random, 10));
        for (int i = 0; i < random.nextInt(20); i++) {
            addFamilyMembersNames(Utils.getRandomString(random, 10));
        }
        return Animal.this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Objects.equals(name, animal.name) &&
                Objects.equals(habitat, animal.habitat) &&
                kind == animal.kind &&
                Objects.equals(familyMembersNames, animal.familyMembersNames) &&
                gender == animal.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, habitat, kind, familyMembersNames, gender);
    }
}
