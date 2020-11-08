package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */

public class Animal implements Serializable {
    private int age;
    private String name;
    private List<String> friendName;
    private AnimalStructure structure;
    private boolean isPredator;
    private Size animalSize;


    public Animal(int age, String name, List<String> friendName, boolean isPredator, Size animalSize, AnimalStructure structure) {
        this.age = age;
        this.name = name;
        this.friendName = friendName;
        this.isPredator = isPredator;
        this.animalSize = animalSize;
        this.structure = structure;
        if(age < 0) {
            throw new IllegalArgumentException();
        }
    }

    public Animal() {

    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public List<String> getFriendName() {
        return friendName;
    }

    public AnimalStructure getStructure() {
        return structure;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFriendName(List<String> friendName) {
        this.friendName = friendName;
    }

    public void setStructure(AnimalStructure structure) {
        this.structure = structure;
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    public void setAnimalSize(Size animalSize) {
        this.animalSize = animalSize;
    }

    public boolean isPredator() {
        return isPredator;
    }

    public Size getAnimalSize() {
        return animalSize;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", friendName=" + friendName +
                ", structure=" + structure +
                ", isPredator=" + isPredator +
                ", animalSize=" + animalSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                isPredator == animal.isPredator &&
                Objects.equals(name, animal.name) &&
                Objects.equals(friendName, animal.friendName) &&
                structure == animal.structure &&
                Objects.equals(animalSize, animal.animalSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, friendName, structure, isPredator, animalSize);
    }


}
