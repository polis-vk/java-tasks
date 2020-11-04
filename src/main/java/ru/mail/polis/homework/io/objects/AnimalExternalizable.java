package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {

    protected int age;
    protected String name;
    List<String> friendName;
    protected Animal.AnimalStructure structure;
    protected boolean isPredator;
    protected Size animalSize;

    public AnimalExternalizable(int age, String name, List<String> friendName, boolean isPredator, Size animalSize, Animal.AnimalStructure structure) {
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

    public AnimalExternalizable() {

    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(age);
        out.writeUTF(name);
        out.writeInt(friendName.size());
        for(int i = 0; i < friendName.size(); ++i) {
            out.writeUTF(friendName.get(i));
        }
        out.writeBoolean(isPredator);
        out.writeObject(animalSize);
        out.writeObject(structure);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        age = in.readInt();
        name = in.readUTF();
        int size = in.readInt();
        friendName = new ArrayList<>(size);
        for(int i = 0; i < size; ++i) {
            friendName.add(in.readUTF());
        }
        isPredator = in.readBoolean();
        animalSize = (Size) in.readObject();
        structure = (Animal.AnimalStructure) in.readObject();
        if(age < 0) {
            throw new IllegalArgumentException();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return age == that.age &&
                isPredator == that.isPredator &&
                Objects.equals(name, that.name) &&
                Objects.equals(friendName, that.friendName) &&
                structure == that.structure &&
                Objects.equals(animalSize, that.animalSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, friendName, structure, isPredator, animalSize);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", friendName=" + friendName +
                ", structure=" + structure +
                ", isPredator=" + isPredator +
                ", animalSize=" + animalSize +
                '}';
    }
}
