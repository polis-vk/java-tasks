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
    private boolean isAggressive;
    private boolean isInvertebrate;
    private AnimalType animalType;
    private GeneralInformationExternalizable information;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, int age, boolean isAggressive, boolean isInvertebrate, AnimalType animalType, GeneralInformationExternalizable information) {
        this.name = name;
        this.age = age;
        this.isAggressive = isAggressive;
        this.isInvertebrate = isInvertebrate;
        this.animalType = animalType;
        this.information = information;
    }

    public GeneralInformationExternalizable getInformation() {
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
        AnimalExternalizable anotherAnimal = (AnimalExternalizable) o;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(convertValueToString(name));
        out.writeInt(age);
        out.writeBoolean(isAggressive);
        out.writeBoolean(isInvertebrate);
        if (animalType == null) {
            out.writeBoolean(false);
        }
        else {
            out.writeBoolean(true);
            out.writeUTF(String.valueOf(animalType));
        }
        out.writeObject(information);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        String nameFromInput = in.readUTF();
        name = nameFromInput.equals("null") ? null : nameFromInput;
        age = in.readInt();
        isAggressive = in.readBoolean();
        isInvertebrate = in.readBoolean();
        if (in.readBoolean()) {
            animalType = AnimalType.valueOf(in.readUTF());
        }
        else {
            animalType = null;
        }
        information = (GeneralInformationExternalizable) in.readObject();
    }

    private static String convertValueToString(String value) {
        return value == null ? "null" : value;
    }
}
