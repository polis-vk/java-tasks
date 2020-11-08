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

    private int age;
    private String name;
    private List<String> friendName;
    private AnimalStructure structure;
    private boolean isPredator;
    private Size animalSize;

    public AnimalExternalizable(int age, String name, List<String> friendName, boolean isPredator, Size animalSize, AnimalStructure structure) {
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

    public boolean isPredator() {
        return isPredator;
    }

    public Size getAnimalSize() {
        return animalSize;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(age);
        out.writeUTF(name);
        out.writeInt(friendName.size());
        for (String s : friendName) {
            out.writeUTF(s);
        }
        out.writeBoolean(isPredator);
        out.writeObject(animalSize);
        out.writeUTF(structure.name());
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
        structure = AnimalStructure.valueOf(in.readUTF());
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
