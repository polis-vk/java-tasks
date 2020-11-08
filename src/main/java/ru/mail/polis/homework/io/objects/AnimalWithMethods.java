package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    private final String name;
    private final int age;
    private final int weight;
    private final List<String> todosList; // Важные дела, которые данное животное должно выполнить
    private final Species species;
    private final Clothes clothes;

    public AnimalWithMethods(String name, int age, int weight,
                             List<String> todosList, Species species, Clothes clothes) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.todosList = todosList;
        this.species = species;
        this.clothes = clothes;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getWeight() {
        return weight;
    }
    public List<String> getTodosList() {
        return todosList;
    }
    public Species getSpecies() {
        return species;
    }
    public Clothes getClothes() {
        return clothes;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        //something more if we want
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        //something more if we want
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return age == that.age &&
                weight == that.weight &&
                name.equals(that.name) &&
                todosList.equals(that.todosList) &&
                species == that.species &&
                clothes.equals(that.clothes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, todosList, species, clothes);
    }
}
