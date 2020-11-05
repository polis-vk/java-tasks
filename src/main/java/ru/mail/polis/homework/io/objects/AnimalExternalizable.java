package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.Objects;
>>>>>>> Tests and fixes

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {
    private String name;
    private int age;
    private int weight;
    private List<String> todosList; // Важные дела, которые данное животное должно выполнить
    private Species animalKind;
    private ClothesExternalizable clothes;

    public AnimalExternalizable(String name, int age, int weight,
                  List<String> todosList, Species animalKind, ClothesExternalizable clothes) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.todosList = todosList;
        this.animalKind = animalKind;
        this.clothes = clothes;
    }

    public AnimalExternalizable() {

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
    public ClothesExternalizable getClothes() {
        return clothes;
    }

<<<<<<< HEAD
=======
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
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
>>>>>>> Tests and fixes

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeUTF(name);
        objectOutput.writeInt(age);
        objectOutput.writeInt(weight);
        objectOutput.writeObject(todosList);
        objectOutput.writeObject(animalKind);
        clothes.writeExternal(objectOutput);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        name = objectInput.readUTF();
        age = objectInput.readInt();
        weight = objectInput.readInt();
        todosList = (List<String>)objectInput.readObject();
        animalKind = (Species)objectInput.readObject();
        clothes = new ClothesExternalizable();
        clothes.readExternal(objectInput);
    }
}
