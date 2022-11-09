package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private String name;
    private int countOfLegs;
    private AnimalMoveType moveType;

    private AnimalHabitat location;
    private boolean isWild;
    private boolean isAnimal;

    public AnimalWithMethods(String name, int countOfLegs, AnimalMoveType moveType, AnimalHabitat location, boolean isWild, boolean isAnimal) {
        this.name = name;
        this.countOfLegs = countOfLegs;
        this.moveType = moveType;
        this.location = location;
        this.isWild = isWild;
        this.isAnimal = isAnimal;
    }

    public String getName() {
        return name;
    }

    public int getCountOfLegs() {
        return countOfLegs;
    }

    public AnimalMoveType getMoveType() {
        return moveType;
    }

    public AnimalHabitat getLocation() {
        return location;
    }

    public boolean isWild() {
        return isWild;
    }

    public boolean isAnimal() {
        return isAnimal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountOfLegs(int countOfLegs) {
        this.countOfLegs = countOfLegs;
    }

    public void setMoveType(AnimalMoveType moveType) {
        this.moveType = moveType;
    }

    public void setLocation(AnimalHabitat location) {
        this.location = location;
    }

    public void setWild(boolean wild) {
        isWild = wild;
    }

    public void setAnimal(boolean animal) {
        isAnimal = animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalWithMethods)) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return getCountOfLegs() == that.getCountOfLegs() && isWild() == that.isWild()
                && isAnimal() == that.isAnimal() && Objects.equals(getName(), that.getName())
                && getMoveType() == that.getMoveType() && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCountOfLegs(), getMoveType(), getLocation(), isWild(), isAnimal());
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", countOfLegs=" + countOfLegs +
                ", moveType=" + moveType +
                ", location=" + location +
                ", isWild=" + isWild +
                ", isAnimal=" + isAnimal +
                '}';
    }

    private void writeObject(ObjectOutputStream objectOutput) throws IOException {
        SerializerUtils.writeOrNull(objectOutput, name);
        objectOutput.writeInt(countOfLegs);
        if (location == null) {
            objectOutput.writeByte(SerializerUtils.NULL);
        } else {
            objectOutput.writeByte(SerializerUtils.NOT_NULL);
            SerializerUtils.writeOrNull(objectOutput, location.getLocation());
        }
        SerializerUtils.writeOrNull(objectOutput, SerializerUtils.enumToString(moveType));
        objectOutput.writeByte(SerializerUtils.booleanToByte(isWild));
        objectOutput.writeByte(SerializerUtils.booleanToByte(isAnimal));
    }

    private void readObject(ObjectInputStream objectInput) throws IOException {
        this.name = SerializerUtils.readOrNull(objectInput);
        this.countOfLegs = objectInput.readInt();
        if (objectInput.readByte() != SerializerUtils.NULL) {
            this.location = new AnimalHabitat(SerializerUtils.readOrNull(objectInput));
        }
        this.moveType = SerializerUtils.stringToEnum(SerializerUtils.readOrNull(objectInput), AnimalMoveType.class);
        this.isWild = SerializerUtils.byteToBoolean(objectInput.readByte());
        this.isAnimal = SerializerUtils.byteToBoolean(objectInput.readByte());
    }
}
