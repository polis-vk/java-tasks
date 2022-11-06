package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private String name;
    private int age;
    private boolean friendly;
    private boolean warmBlooded;
    private AnimalType animalType;
    private PopulationWithMethods population;

    public AnimalWithMethods(String name, int age, boolean friendly, boolean warmBlooded, AnimalType animalType, PopulationWithMethods population) {
        this.name = name;
        this.age = age;
        this.friendly = friendly;
        this.warmBlooded = warmBlooded;
        this.animalType = animalType;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public boolean isWarmBlooded() {
        return warmBlooded;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public PopulationWithMethods getPopulation() {
        return population;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        writeString(oos, name);
        oos.writeInt(age);
        oos.writeByte((byte) ((isFriendly() ? 1 : 0) << 1 + (isWarmBlooded() ? 1 : 0)));
        oos.writeByte(animalType.getOrdinal());
        oos.writeObject(population);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        name = readString(ois);
        age = ois.readInt();
        friendly = ois.readBoolean();
        warmBlooded = ois.readBoolean();
        animalType = AnimalType.getOrdinal(ois.readByte());
        population = (PopulationWithMethods) ois.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalWithMethods that = (AnimalWithMethods) o;
        return Objects.equals(that.getName(), getName()) && that.getAge() == getAge()
                && that.isFriendly() == isFriendly() && that.isWarmBlooded() == isWarmBlooded()
                && that.getAnimalType() == getAnimalType() && Objects.equals(that.getPopulation(), getPopulation());
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", age=" + age + '\'' +
                ", friendly=" + friendly + '\'' +
                ", warm-blooded=" + warmBlooded + '\'' +
                ", population=" + population + '\'' +
                ", animalType=" + animalType +
                '}';
    }

    private static void writeString(ObjectOutput out, String str) throws IOException {
        if (str == null) {
            out.writeByte(0);
        } else {
            out.writeByte(1);
            out.writeUTF(str);
        }
    }

    private static String readString(ObjectInput in) throws IOException {
        if (in.readByte() == 0) {
            return null;
        }
        return in.readUTF();
    }

}
