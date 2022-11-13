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
        out.writeByte(isPet ? 1 : 0);
        out.writeByte(isFly ? 1 : 0);
        if (name == null) {
            out.writeInt(-1);
        } else {
            out.writeInt(1);
            out.writeUTF(name);
        }
        out.writeUTF(moveType.toString());
        out.writeObject(population);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        countLegs = in.read();
        isPet = in.readByte() == 1;
        isFly = in.readByte() == 1;
        if (in.readInt() == -1) {
            name = null;
        } else {
            name = in.readUTF();
        }
        String moveTypeStr = in.readUTF();
        for (int i = 0; i < MoveType.values().length; i++) {
            if(moveTypeStr.equals(MoveType.values()[i].toString())){
                moveType = MoveType.values()[i];
                break;
            }
        }
        population = (Population) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return countLegs == animal.getCountLegs() && isFly == animal.isFly()
                && isPet == animal.isPet() && moveType == animal.getMoveType()
                && Objects.equals(name, animal.getName())
                && Objects.equals(population, animal.getPopulation());
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "countLegs=" + countLegs +
                ", name='" + (name == null ? "null" : name) +
                ", isPet=" + isPet +
                ", isFly=" + isFly +
                ", moveType=" + moveType +
                ", population=" + (population == null ? "null" : population);
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
