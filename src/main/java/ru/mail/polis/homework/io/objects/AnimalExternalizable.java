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
    private static final byte NULL_BYTE = 0;
    private static final byte NOT_NULL_BYTE = 1;

    private boolean isPet;
    private boolean isPredator;
    private int legs;
    private String color;
    private MoveType moveType;

    private AnimalPassportExternalizable animalPassportExternalizable;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(boolean isPet, boolean isPredator, int legs, String color,
                                MoveType moveType, AnimalPassportExternalizable animalPassportExternalizable) {
        this.isPet = isPet;
        this.isPredator = isPredator;
        this.legs = legs;
        this.color = color;
        this.moveType = moveType;
        this.animalPassportExternalizable = animalPassportExternalizable;
    }

    public boolean isPet() {
        return isPet;
    }

    public void setPet(boolean pet) {
        isPet = pet;
    }

    public boolean isPredator() {
        return isPredator;
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public AnimalPassportExternalizable getAnimalPassportExternalizable() {
        return animalPassportExternalizable;
    }

    public void setAnimalPassportExternalizable(AnimalPassportExternalizable animalPassportExternalizable) {
        this.animalPassportExternalizable = animalPassportExternalizable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPet(), isPredator(), getLegs(),
                getColor(), getMoveType(), getAnimalPassportExternalizable());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AnimalExternalizable animalExternalizable = (AnimalExternalizable) obj;
        return isPet() == animalExternalizable.isPet() &&
                isPredator() == animalExternalizable.isPredator() &&
                getLegs() == animalExternalizable.getLegs() &&
                Objects.equals(getColor(), animalExternalizable.getColor()) &&
                getMoveType() == animalExternalizable.getMoveType() &&
                Objects.equals(getAnimalPassportExternalizable(),
                        animalExternalizable.getAnimalPassportExternalizable());
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "pet='" + isPet() + '\'' +
                ", predator=" + isPredator() +
                ", legs=" + getLegs() +
                ", color=" + getColor() +
                ", moveType=" + getMoveType() +
                ", animalPassportExternalizable=" + getAnimalPassportExternalizable() +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        byte booleansAsByte = (byte) (((isPet() ? 1 : 0) * 2) + (isPredator() ? 1 : 0));
        out.writeByte(booleansAsByte);
        out.writeInt(getLegs());
        writeString(getColor(), out);
        writeString(getMoveType().toString(), out);
        if (getAnimalPassportExternalizable() == null) {
            out.writeByte(NULL_BYTE);
        } else {
            out.writeByte(NOT_NULL_BYTE);
            out.writeObject(getAnimalPassportExternalizable());
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        byte booleansAsByte = in.readByte();
        setPet((booleansAsByte & 1) != 0);
        setPredator((booleansAsByte & 2) != 0);
        setLegs(in.readInt());
        setColor(readString(in));
        setMoveType(MoveType.valueOf(readString(in)));
        byte objIsNull = in.readByte();
        if (objIsNull == NULL_BYTE) {
            setAnimalPassportExternalizable(null);
        } else {
            setAnimalPassportExternalizable((AnimalPassportExternalizable) in.readObject());
        }
    }

    static class AnimalPassportExternalizable implements Externalizable {
        private String species;
        private Sex sex;

        private String name;
        private int age;
        private boolean isVaccinated;
        private String descriptionOfAnimal;

        public AnimalPassportExternalizable() {
        }

        public AnimalPassportExternalizable(String name, Sex sex, int age, String species,
                                            boolean isVaccinated, String descriptionOfAnimal) {
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.species = species;
            this.isVaccinated = isVaccinated;
            this.descriptionOfAnimal = descriptionOfAnimal;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Sex getSex() {
            return sex;
        }

        public void setSex(Sex sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public boolean isVaccinated() {
            return isVaccinated;
        }

        public void setVaccinated(boolean vaccinated) {
            isVaccinated = vaccinated;
        }

        public String getDescriptionOfAnimal() {
            return descriptionOfAnimal;
        }

        public void setDescriptionOfAnimal(String descriptionOfAnimal) {
            this.descriptionOfAnimal = descriptionOfAnimal;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getSpecies(), getSex(), getName(), getAge(), isVaccinated(), getDescriptionOfAnimal());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            AnimalPassportExternalizable animalPassportExternalizable = (AnimalPassportExternalizable) obj;
            return Objects.equals(getSpecies(), animalPassportExternalizable.getSpecies()) &&
                    getSex() == animalPassportExternalizable.getSex() &&
                    Objects.equals(getName(), animalPassportExternalizable.getName()) &&
                    getAge() == animalPassportExternalizable.getAge() &&
                    isVaccinated() == animalPassportExternalizable.isVaccinated() &&
                    Objects.equals(getDescriptionOfAnimal(), animalPassportExternalizable.getDescriptionOfAnimal());
        }

        @Override
        public String toString() {
            return "AnimalPassportExternalizable {" +
                    "species='" + getSpecies() + '\'' +
                    ", sex=" + getSex() +
                    ", name=" + getName() +
                    ", age=" + getAge() +
                    ", vaccinated=" + isVaccinated() +
                    ", descriptionOfAnimal=" + getDescriptionOfAnimal() +
                    '}';
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            writeString(getSpecies(), out);
            writeString(getSex().toString(), out);
            writeString(getName(), out);
            out.writeInt(getAge());
            out.writeByte(isVaccinated() ? 1 : 0);
            writeString(getDescriptionOfAnimal(), out);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException {
            setSpecies(readString(in));
            setSex(Sex.valueOf(readString(in)));
            setName(readString(in));
            setAge(in.readInt());
            setVaccinated(in.readByte() == 1);
            setDescriptionOfAnimal(readString(in));
        }
    }

    private static void writeString(String str, ObjectOutput out) throws IOException {
        if (str == null) {
            out.writeByte(NULL_BYTE);
            return;
        }
        out.writeByte(NOT_NULL_BYTE);
        out.writeUTF(str);
    }

    private static String readString(ObjectInput in) throws IOException {
        byte stringIsNull = in.readByte();
        if (stringIsNull == NULL_BYTE) {
            return null;
        }
        return in.readUTF();
    }
}