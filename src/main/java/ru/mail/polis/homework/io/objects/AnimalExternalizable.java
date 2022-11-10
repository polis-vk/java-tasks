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
        AnimalByte animalByte = new AnimalByte(this);
        out.writeByte(animalByte.writeByte());
        if (animalByte.nameIsNotNull()) {
            out.writeUTF(name);
        }
        out.writeInt(age);
        if (animalByte.animalTypeIsNotNull()) {
            out.writeUTF(animalType.name());
        }
        if (animalByte.populationIsNotNull()) {
            out.writeObject(population);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        AnimalByte animalByte = new AnimalByte(in.readByte());
        if (animalByte.nameIsNotNull()) {
            name = in.readUTF();
        }
        age = in.readInt();
        friendly = animalByte.isFriendly();
        warmBlooded = animalByte.isWarmBlooded();
        if (animalByte.animalTypeIsNotNull()) {
            animalType = AnimalType.valueOf(in.readUTF());
        }
        if (animalByte.populationIsNotNull()) {
            population = (PopulationExternalizable) in.readObject();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return Objects.equals(animal.name, name) && animal.age == age
                && animal.friendly == friendly && animal.warmBlooded == warmBlooded
                && animal.animalType == animalType && Objects.equals(animal.population, population);
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
