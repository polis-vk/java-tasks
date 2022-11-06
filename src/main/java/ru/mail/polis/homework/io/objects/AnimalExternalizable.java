package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private String name;
    private int age;
    private boolean friendly;
    private boolean warmBlooded;
    private AnimalType animalType;
    private PopulationExternalizable population;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, int age, boolean friendly, boolean warmBlooded, AnimalType animalType, PopulationExternalizable population) {
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

    public PopulationExternalizable getPopulation() {
        return population;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        writeString(out, name);
        out.writeInt(age);
        out.writeByte((byte) ((isFriendly() ? 1 : 0) << 1 + (isWarmBlooded() ? 1 : 0)));
        out.writeByte(animalType.getOrdinal());
        out.writeObject(population);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = readString(in);
        age = in.readInt();
        friendly = in.readBoolean();
        warmBlooded = in.readBoolean();
        animalType = AnimalType.getOrdinal(in.readByte());
        population = (PopulationExternalizable) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalExternalizable that = (AnimalExternalizable) o;
        return Objects.equals(that.getName(), getName()) && that.getAge() == getAge()
                && that.isFriendly() == isFriendly() && that.isWarmBlooded() == isWarmBlooded()
                && that.getAnimalType() == getAnimalType() && Objects.equals(that.getPopulation(), getPopulation());
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
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
