package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import static ru.mail.polis.homework.io.objects.SerializationUtils.*;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private String name;
    private int age;
    private boolean isWild;
    private boolean isFed;
    private AnimalAbilityType animalAbilityType;
    private Location animalLocation;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, int age, boolean isWild, boolean isFed, AnimalAbilityType animalAbilityType, Location animalLocation) {
        this.name = name;
        this.age = age;
        this.isWild = isWild;
        this.isFed = isFed;
        this.animalAbilityType = animalAbilityType;
        this.animalLocation = animalLocation;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isWild() {
        return isWild;
    }

    public boolean isFed() {
        return isFed;
    }

    public AnimalAbilityType getAnimalAbilityType() {
        return animalAbilityType;
    }

    public Location getAnimalLocation() {
        return animalLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return age == animal.age && isWild == animal.isWild && isFed == animal.isFed && Objects.equals(name, animal.name) && animalAbilityType == animal.animalAbilityType && Objects.equals(animalLocation, animal.animalLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isWild, isFed, animalAbilityType, animalLocation);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isWild=" + isWild +
                ", isFed=" + isFed +
                ", animalAbilityType=" + animalAbilityType +
                ", animalLocation=" + animalLocation +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        // Write Location first as it's an object
        if (animalLocation == null) {
            out.writeByte(SerializationUtils.NULL);
        } else {
            out.writeByte(SerializationUtils.NOT_NULL);
            writeUTFOrNull(animalLocation.getLongitude(), out);
            writeUTFOrNull(animalLocation.getLatitude(), out);
        }
        // Write other fields of superclass
        writeUTFOrNull(name, out);
        out.writeInt(age);
        out.writeByte(booleanToByte(isWild));
        out.writeByte(booleanToByte(isFed));
        writeUTFOrNull(enumToString(animalAbilityType), out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        // Read Location first
        if (in.readByte() != NULL) {
            this.animalLocation = new Location(
                    readUTFOrNull(in),
                    readUTFOrNull(in)
            );
        }
        // Read other fields
        this.name = readUTFOrNull(in);
        this.age = in.readInt();
        this.isWild = byteToBoolean(in.readByte());
        this.isFed = byteToBoolean(in.readByte());
        this.animalAbilityType = stringToEnum(readUTFOrNull(in), AnimalAbilityType.class);
    }
}
