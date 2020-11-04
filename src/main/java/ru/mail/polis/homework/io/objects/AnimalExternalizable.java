package ru.mail.polis.homework.io.objects;


import lombok.Getter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
@Getter
public class AnimalExternalizable implements Externalizable {

    protected int age;
    protected String name;
    protected Habitat habitat;
    protected List<String> food;
    protected boolean sexIsMale;
    protected double height;
    protected HeartExternalizable heartExternalizable;

    public enum Habitat {
        WATER,
        LAND,
        AIR
    }

    public AnimalExternalizable(int age, String name, Habitat habitat, List<String> food,
                  boolean sexIsMale, double height, HeartExternalizable heartExternalizable) {
        this.age = age;
        this.name = name;
        this.habitat = habitat;
        this.food = food;
        this.sexIsMale = sexIsMale;
        this.height = height;
        this.heartExternalizable = heartExternalizable;
    }

    public AnimalExternalizable() {

    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(age);
        out.writeUTF(name);
        out.writeObject(habitat);
        out.writeObject(food);
        out.writeBoolean(sexIsMale);
        out.writeDouble(height);
        heartExternalizable.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        age = in.readInt();
        name = in.readUTF();
        habitat = (Habitat) in.readObject();
        food = (List<String>) in.readObject();
        sexIsMale = in.readBoolean();
        height = in.readDouble();
        heartExternalizable = new HeartExternalizable();
        heartExternalizable.readExternal(in);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return age == that.age &&
                sexIsMale == that.sexIsMale &&
                Double.compare(that.height, height) == 0 &&
                Objects.equals(name, that.name) &&
                habitat == that.habitat &&
                Objects.equals(food, that.food) &&
                Objects.equals(heartExternalizable, that.heartExternalizable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, habitat, food, sexIsMale, height, heartExternalizable);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", habitat=" + habitat +
                ", food=" + food +
                ", sexIsMale=" + sexIsMale +
                ", height=" + height +
                ", heart=" + heartExternalizable +
                '}';
    }
}
