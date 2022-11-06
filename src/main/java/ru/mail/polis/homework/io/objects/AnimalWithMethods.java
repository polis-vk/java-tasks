package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import ru.mail.polis.homework.oop.vet.MoveType;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    int countLegs;
    boolean isPet;
    boolean isFly;
    String name;
    MoveType moveType;
    Population population;

    public AnimalWithMethods(int countLegs, boolean isPet, boolean isFly, String name, MoveType moveType, Population population) {
        this.countLegs = countLegs;
        this.isPet = isPet;
        this.isFly = isFly;
        this.name = name;
        this.moveType = moveType;
        this.population = population;
    }

    public AnimalWithMethods() {

    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.write(countLegs);
        out.writeBoolean(isPet);
        out.writeBoolean(isFly);
        out.writeObject(name);
        out.writeObject(moveType);
        out.writeObject(population);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
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
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return countLegs == animal.countLegs && isFly == animal.isFly
                && isPet == animal.isPet && moveType == animal.moveType
                && Objects.equals(name, animal.name)
                && Objects.equals(population, animal.population);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
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
