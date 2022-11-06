package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import ru.mail.polis.homework.oop.vet.MoveType;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    int countLegs;
    boolean isPet;
    boolean isFly;
    String name;
    MoveType moveType;
    Population population;

    public AnimalExternalizable(int countLegs, boolean isPet, boolean isFly, String name, MoveType moveType, Population population) {
        this.countLegs = countLegs;
        this.isPet = isPet;
        this.isFly = isFly;
        this.name = name;
        this.moveType = moveType;
        this.population = population;
    }

    public AnimalExternalizable() {

    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.write(countLegs);
        out.writeBoolean(isPet);
        out.writeBoolean(isFly);
        out.writeObject(name);
        out.writeObject(moveType);
        out.writeObject(population);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        countLegs = in.read();
        isPet = in.readBoolean();
        isFly = in.readBoolean();
        name = (String) in.readObject();
        moveType = (MoveType) in.readObject();
        population = (Population) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return countLegs == animal.countLegs && isFly == animal.isFly
                && isPet == animal.isPet && moveType == animal.moveType
                && Objects.equals(name, animal.name)
                && Objects.equals(population, animal.population);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "countLegs=" + countLegs +
                ", name='" + name +
                ", isPet=" + isPet +
                ", isFly=" + isFly +
                ", moveType=" + moveType +
                ", population.mainland=" + population.mainland +
                ", population.size=" + population.size + "}";
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(countLegs, name, isFly, isPet, moveType);
        result = 31 * result + Objects.hashCode(population);
        return result;
    }

    public int getCountLegs() {
        return countLegs;
    }

    public void setCountLegs(int countLegs) {
        this.countLegs = countLegs;
    }

    public boolean isPet() {
        return isPet;
    }

    public void setPet(boolean pet) {
        isPet = pet;
    }

    public boolean isFly() {
        return isFly;
    }

    public void setFly(boolean fly) {
        isFly = fly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }
}
