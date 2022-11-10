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
    private static final byte NULL_VALUE = 0;
    private static final byte NOT_NULL_VALUE = 1;
    private static final byte TRUE = 1;
    private static final byte FALSE = 0;
    private String name;
    private int age;
    private double weight;
    private boolean alive;
    private AnimalType type;
    private boolean isPet;
    private ResidencePlace residencePlace;

    public AnimalExternalizable(String name, int age, double weight, boolean alive, AnimalType type, boolean isPet, ResidencePlace residencePlace) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.alive = alive;
        this.type = type;
        this.isPet = isPet;
        this.residencePlace = residencePlace;
    }

    public AnimalExternalizable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public ResidencePlace getResidencePlace() {
        return residencePlace;
    }

    public void setResidencePlace(ResidencePlace placeOfResidence) {
        this.residencePlace = placeOfResidence;
    }

    @Override
    public void writeExternal(ObjectOutput output) throws IOException {
        if (name == null) {
            output.writeByte(NULL_VALUE);
        } else {
            output.writeByte(NOT_NULL_VALUE);
            output.writeUTF(name);
        }
        output.writeInt(age);
        output.writeDouble(weight);
        if(alive){
            output.writeByte(TRUE);
        }else{
            output.writeByte(FALSE);
        }
        if (type == null) {
            output.writeByte(NULL_VALUE);
        } else {
            output.writeByte(NOT_NULL_VALUE);
            output.writeUTF(type.toString());
        }
        if(isPet){
            output.writeByte(TRUE);
        }else{
            output.writeByte(FALSE);
        }
        if (residencePlace == null) {
            output.writeByte(NULL_VALUE);
        } else {
            output.writeByte(NOT_NULL_VALUE);
            String country = residencePlace.getCountry();
            if (country == null) {
                output.writeByte(NULL_VALUE);
            } else {
                output.writeByte(NOT_NULL_VALUE);
                output.writeUTF(country);
            }
            String terrain = residencePlace.getTerrain();
            if (terrain == null) {
                output.writeByte(NULL_VALUE);
            } else {
                output.writeByte(NOT_NULL_VALUE);
                output.writeUTF(terrain);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput input) throws IOException {
        if (input.readByte() != NULL_VALUE) {
            name = input.readUTF();
        }
        age = input.readInt();
        weight = input.readDouble();
        if(input.readByte() == TRUE){
            alive = true;
        }else{
            alive = false;
        }
        if (input.readByte() == NULL_VALUE) {
            type = null;
        } else {
            type = AnimalType.valueOf(input.readUTF());
        }
        if(input.readByte() == TRUE){
            isPet = true;
        }else{
            isPet = false;
        }
        if (input.readByte() == NULL_VALUE) {
            residencePlace = null;
        } else {
            ResidencePlace residencePlace = new ResidencePlace();
            if (input.readByte() == NULL_VALUE) {
                residencePlace.setCountry(null);
            } else {
                residencePlace.setCountry(input.readUTF());
            }
            if (input.readByte() == NULL_VALUE) {
                residencePlace.setTerrain(null);
            } else {
                residencePlace.setTerrain(input.readUTF());
            }
            this.residencePlace = residencePlace;
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
        AnimalExternalizable that = (AnimalExternalizable) o;
        return age == that.age
                && Double.compare(that.weight, weight) == 0
                && alive == that.alive && isPet == that.isPet
                && Objects.equals(name, that.name)
                && type == that.type
                && Objects.equals(residencePlace, that.residencePlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, alive, type, isPet, residencePlace);
    }
}
