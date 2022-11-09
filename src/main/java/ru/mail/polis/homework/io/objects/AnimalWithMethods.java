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
        out.writeByte(isPet ? 1 : 0);
        out.writeByte(isFly ? 1 : 0);
        if (name == null) {
            out.writeInt(-1);
        } else {
            out.writeInt(1);
            out.writeUTF(name);
        }
        out.writeInt(moveType.ordinal());
        out.writeObject(population);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        countLegs = in.read();
        isPet = in.readByte() == 1;
        isFly = in.readByte() == 1;
        if (in.readInt() == -1) {
            name = null;
        } else {
            name = in.readUTF();
        }
        moveType = MoveType.values()[in.readInt()];
        population = (Population) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return countLegs == animal.getCountLegs() && isFly == animal.isFly()
                && isPet == animal.isPet() && moveType == animal.getMoveType()
                && Objects.equals(name, animal.getName())
                && Objects.equals(population, animal.getPopulation());
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "countLegs=" + countLegs +
                ", name='" + (name == null ? "null" : name) +
                ", isPet=" + isPet +
                ", isFly=" + isFly +
                ", moveType=" + moveType +
                ", population" + (population == null ? "null" : population);
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
