package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private String name;
    private int age;
    private boolean isWild;
    private boolean isAquaticAnimal;
    private AnimalType animalType;
    private LocalDate dateOfBirth;
    private Owner owner;

    public AnimalExternalizable() {}

    public AnimalExternalizable(String name, int age, boolean isWild, boolean isAquaticAnimal, AnimalType animalType, LocalDate dateOfBirth) {
        this.name = name;
        this.age = age;
        this.animalType = animalType;
        this.isWild = isWild;
        this.isAquaticAnimal = isAquaticAnimal;
        this.dateOfBirth = dateOfBirth;
        this.owner = null;
    }

    public String getName() {
        return name;
    }

    public boolean isWild() {
        return isWild;
    }

    public int getAge() {
        return age;
    }

    public boolean isAquaticAnimal() {
        return isAquaticAnimal;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return age == animal.age && isWild == animal.isWild && isAquaticAnimal == animal.isAquaticAnimal
                && Objects.equals(name, animal.name) && animalType == animal.animalType
                && Objects.equals(dateOfBirth, animal.dateOfBirth) && Objects.equals(owner, animal.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isWild, isAquaticAnimal, animalType, dateOfBirth, owner);
    }

    @Override
    public void writeExternal(ObjectOutput in) throws IOException {
        in.writeBoolean(isWild);
        in.writeBoolean(isAquaticAnimal);

        if (name == null) {
            in.writeByte(0);
        } else {
            in.writeByte(1);
            in.writeUTF(name);
        }

        byte indexOfType = (byte) Arrays.stream(AnimalType.values()).collect(Collectors.toList()).indexOf(animalType);
        in.writeByte(indexOfType);

        if (dateOfBirth == null) {
            in.writeByte(0);
        } else {
            in.writeByte(1);
            in.writeInt(dateOfBirth.getYear());
            in.writeInt(dateOfBirth.getMonthValue());
            in.writeInt(dateOfBirth.getDayOfMonth());
        }

        in.writeInt(age);

        if (owner == null) {
            in.writeByte(0);
        } else {
            in.writeByte(1);
            in.writeUTF(owner.getSurname());
            in.writeUTF(owner.getName());
            in.writeInt(owner.getAge());
        }
    }

    @Override
    public void readExternal(ObjectInput out) throws IOException, ClassNotFoundException {
        isWild = out.readBoolean();
        isAquaticAnimal = out.readBoolean();

        byte nameHere = out.readByte();
        if (nameHere != 0) {
            name = out.readUTF();
        }

        animalType = AnimalType.values()[out.readByte()];

        byte dateHere = out.readByte();
        if (dateHere != 0) {
            dateOfBirth = LocalDate.of(out.readInt(), out.readInt(), out.readInt());
        }

        age = out.readInt();

        byte ownerHere = out.readByte();
        if (ownerHere != 0) {
            owner = new Owner(out.readUTF(), out.readUTF(), out.readInt());
        }
    }
}
