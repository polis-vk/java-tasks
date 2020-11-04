package ru.mail.polis.homework.io.objects;


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
public class AnimalExternalizable implements Externalizable {

    protected int age;
    protected String name;
    protected Habitat habitat;
    protected List<String> food;
    protected boolean sexIsMale;
    protected double height;
    protected Heart heart;

    public enum Habitat {
        WATER,
        LAND,
        AIR
    }
    //ru.mail.polis.homework.io.objects.AnimalExternalizable<AnimalExternalizable{age=-1009076293, name='1884428859', habitat=LAND, food=[leaves, mushroom], sexIsMale=true, height=56.718519401064384, heart=Heart{isAlive=true}}>
    //ru.mail.polis.homework.io.objects.AnimalExternalizable<AnimalExternalizable{age=-1009076293, name='1884428859', habitat=LAND, food=[leaves, mushroom], sexIsMale=true, height=56.718519401064384, heart=Heart{isAlive=true}}>

    public AnimalExternalizable(int age, String name, Habitat habitat, List<String> food,
                  boolean gender, double height, Heart heart) {
        this.age = age;
        this.name = name;
        this.habitat = habitat;
        this.food = food;
        this.sexIsMale = gender;
        this.height = height;
        this.heart = heart;
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
        out.writeObject(heart);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        age = in.readInt();
        name = in.readUTF();
        habitat = (Habitat) in.readObject();
        food = (List<String>) in.readObject();
        sexIsMale = in.readBoolean();
        height = in.readDouble();
        heart = (Heart) in.readObject();
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
                Objects.equals(heart, that.heart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, habitat, food, sexIsMale, height, heart);
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
                ", heart=" + heart +
                '}';
    }
}
