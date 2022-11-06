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

    private String say;
    private int legs;
    private boolean isWild;
    private boolean isHerbivorous;
    private Gender gender;
    private Сlassification classification;

    public AnimalExternalizable(String say, int legs, boolean isWild, boolean isHerbivorous,
                  Gender gender, Сlassification classification) {
        this.say = say;
        this.legs = legs;
        this.isWild = isWild;
        this.isHerbivorous = isHerbivorous;
        this.gender = gender;
        this.classification = classification;
    }

    public AnimalExternalizable() {

    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public boolean getIsWild() {
        return isWild;
    }

    public void setIsWild(boolean isWild) {
        this.isWild = isWild;
    }

    public boolean getIsHerbivorous() {
        return isHerbivorous;
    }

    public void setIsHerbivorous(boolean isHerbivorous) {
        this.isHerbivorous = isHerbivorous;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Сlassification getClassification() {
        return classification;
    }

    public void setClassification(Сlassification classification) {
        this.classification = classification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalExternalizable animalExternalizable = (AnimalExternalizable) o;
        return say.equals(animalExternalizable.say) &&
                legs == animalExternalizable.legs &&
                isWild == animalExternalizable.isWild &&
                isHerbivorous == animalExternalizable.isHerbivorous &&
                gender.equals(animalExternalizable.gender) &&
                classification.equals(animalExternalizable.classification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(say, legs, isWild, isHerbivorous,
                gender, classification);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "say = " + say +
                ", legs = " + legs +
                ", isWild = " + isWild +
                ", isHerbivorous = " + isHerbivorous +
                ", gender = " + gender +
                ", classification = " + classification +
                '}' + '\n';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        if (say == null) {
            out.writeByte(NULL_VALUE);
        } else {
            out.writeByte(NOT_NULL_VALUE);
            out.writeUTF(say);
        }
        out.writeInt(legs);

        if (gender == null) {
            out.writeByte(NULL_VALUE);
        } else {
            out.writeByte(NOT_NULL_VALUE);
            out.writeUTF(gender.toString());
        }

        if (classification == null) {
            out.writeByte(NULL_VALUE);
        } else {
            out.writeByte(NOT_NULL_VALUE);
            String type = classification.getType();
            if (type == null) {
                out.writeByte(NULL_VALUE);
            } else {
                out.writeByte(NOT_NULL_VALUE);
                out.writeUTF(type);
            }

            String family = classification.getFamily();
            if (family == null) {
                out.writeByte(NULL_VALUE);
            } else {
                out.writeByte(NOT_NULL_VALUE);
                out.writeUTF(family);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        if (in.readByte() == NULL_VALUE) {
            say = null;
        } else {
            say = in.readUTF();
        }

        legs = in.readInt();

        if (in.readByte() == NULL_VALUE) {
            gender = null;
        } else {
            gender = Gender.valueOf(in.readUTF());
        }

        if (in.readByte() == NULL_VALUE) {
            classification = null;
        } else {
            Сlassification classification = new Сlassification();
            if (in.readByte() == NULL_VALUE) {
                classification.setType(null);
            } else {
                classification.setType(in.readUTF());
            }

            if (in.readByte() == NULL_VALUE) {
                classification.setFamily(null);
            } else {
                classification.setFamily(in.readUTF());
            }
            this.classification = classification;
        }
    }
}
