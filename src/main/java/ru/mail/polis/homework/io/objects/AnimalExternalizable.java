package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

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
    private Population population;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, int age, boolean friendly, boolean warmBlooded, AnimalType animalType, Population population) {
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

    public Population getPopulation() {
        return population;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWarmBlooded(boolean warmBlooded) {
        this.warmBlooded = warmBlooded;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeBoolean(friendly);
        out.writeBoolean(warmBlooded);
        out.writeUTF(animalType.toString());
        out.writeObject(population);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        friendly = in.readBoolean();
        warmBlooded = in.readBoolean();
        animalType = AnimalType.valueOf(in.readUTF());
        population = (Population) in.readObject();
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
        return that.getName().equals(getName()) && that.getAge() == getAge()
                && that.isFriendly() == isFriendly() && that.isWarmBlooded() == isWarmBlooded()
                && that.getAnimalType() == getAnimalType() && that.getPopulation() == getPopulation();
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
}
