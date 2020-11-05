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
    private String name;
    private BioTag tag;
    private int age;
    private List<String> habitat;
    private Size size;
    private double weight;
    private boolean canFly;

    enum Size {
        LARGE,
        MEDIUM,
        SMALL
    }

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, BioTag tag, int age, List<String> habitat, Size size, double weight, boolean canFly) {
        this.name = name;
        this.tag = tag;
        this.age = age;
        this.habitat = habitat;
        this.size = size;
        this.weight = weight;
        this.canFly = canFly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BioTag getTag() {
        return tag;
    }

    public void setTag(BioTag tag) {
        this.tag = tag;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getHabitat() {
        return habitat;
    }

    public void setHabitat(List<String> habitat) {
        this.habitat = habitat;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return age == that.age &&
                Double.compare(that.weight, weight) == 0 &&
                canFly == that.canFly &&
                Objects.equals(name, that.name) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(habitat, that.habitat) &&
                size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tag, age, habitat, size, weight, canFly);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", tag=" + tag +
                ", age=" + age +
                ", habitat=" + habitat +
                ", size=" + size +
                ", weight=" + weight +
                ", canFly=" + canFly +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeObject(tag);
        out.writeInt(age);

        List<String> temp = getHabitat();
        out.writeInt(temp.size());
        for (String habitat : temp) {
            out.writeUTF(habitat);
        }

        out.writeObject(size);
        out.writeDouble(weight);
        out.writeBoolean(canFly);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        tag = (BioTag) in.readObject();
        age = in.readInt();

        int length = in.readInt();
        List<String> temp = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            temp.add(in.readUTF());
        }
        habitat = temp;

        size = (Size) in.readObject();
        weight = in.readDouble();
        canFly = in.readBoolean();
    }
}
