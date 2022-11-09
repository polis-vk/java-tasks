package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private static final byte nameBit = 0b1000;
    private static final byte aggressiveBit = 0b0100;
    private static final byte invertebrateBit = 0b0010;
    private static final byte animalTypeBit = 0b0001;

    private String name;
    private int age;
    private boolean isAggressive;
    private boolean isInvertebrate;
    private AnimalType animalType;
    private GeneralInformationWithMethods information;

    public AnimalWithMethods(String name, int age, boolean isAggressive, boolean isInvertebrate, AnimalType animalType, GeneralInformationWithMethods information) {
        this.name = name;
        this.age = age;
        this.isAggressive = isAggressive;
        this.isInvertebrate = isInvertebrate;
        this.animalType = animalType;
        this.information = information;
    }

    public GeneralInformationWithMethods getInformation() {
        return information;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public boolean isAggressive() {
        return isAggressive;
    }

    public boolean isInvertebrate() {
        return isInvertebrate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalWithMethods anotherAnimal = (AnimalWithMethods) o;
        return Objects.equals(name, anotherAnimal.getName())
                && age == anotherAnimal.getAge()
                && isAggressive == anotherAnimal.isAggressive()
                && isInvertebrate == anotherAnimal.isInvertebrate()
                && Objects.equals(animalType, anotherAnimal.getAnimalType())
                && information.equals(anotherAnimal.getInformation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isAggressive, isInvertebrate, animalType, information);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name:" + name +
                ", age:" + age +
                ", isAggressive:" + isAggressive +
                ", isInvertebrate:" + isInvertebrate +
                ", animalType:" + animalType +
                ", information:" + information + '}';
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(getByteFromData());
        if (name != null) {
            out.writeUTF(name);
        }
        if (animalType != null) {
            out.writeUTF(String.valueOf(animalType));
        }
        out.writeInt(age);
        out.writeObject(information);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        byte byteDataFromInput = in.readByte();
        if ((byteDataFromInput & nameBit) != 0) {
            name = in.readUTF();
        } else {
            name = null;
        }
        isAggressive = (byteDataFromInput & aggressiveBit) != 0;
        isInvertebrate = (byteDataFromInput & invertebrateBit) != 0;
        if ((byteDataFromInput & animalTypeBit) != 0) {
            animalType = AnimalType.valueOf(in.readUTF());
        } else {
            animalType = null;
        }
        age = in.readInt();
        information = (GeneralInformationWithMethods) in.readObject();
    }

    private byte getByteFromData() {
        return (byte) (getBooleanData() | getNullableElements());
    }

    private byte getBooleanData() {
        byte result = 0;
        if (isAggressive) {
            result |= aggressiveBit;
        }
        if (isInvertebrate) {
            result |= invertebrateBit;
        }
        return result;
    }

    private byte getNullableElements() {
        byte result = 0;
        if (name != null) {
            result |= nameBit;
        }
        if (animalType != null) {
            result |= animalTypeBit;
        }
        return result;
    }
}
