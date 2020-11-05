package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods {
    private String name;
    private int age;
    private int weight;
    private List<String> todosList; // Важные дела, которые данное животное должно выполнить
    private Species animalKind;
    private Clothes clothes;

    public AnimalWithMethods(){ }

    public AnimalWithMethods(String name, int age, int weight,
                  List<String> todosList, Species animalKind, Clothes clothes) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.todosList = todosList;
        this.animalKind = animalKind;
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
    public Species getAnimalKind() {
        return animalKind;
    }
    public Clothes getClothes() {
        return clothes;
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
                animalKind == that.animalKind &&
                clothes.equals(that.clothes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, todosList, animalKind, clothes);
    }

    public void myWriteObjects(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(name);
        objectOutputStream.writeInt(age);
        objectOutputStream.writeInt(weight);
        objectOutputStream.writeObject(todosList);
        objectOutputStream.writeObject(animalKind);
        clothes.myWriteObject(objectOutputStream);
    }

    public void myReadObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        name = objectInputStream.readUTF();
        age = objectInputStream.readInt();
        weight = objectInputStream.readInt();
        todosList = (List<String>)objectInputStream.readObject();
        animalKind = (Species)objectInputStream.readObject();
        clothes = new Clothes();
        clothes.myReadObject(objectInputStream);
    }
}
