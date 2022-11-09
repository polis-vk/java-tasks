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
    private static final byte NAME_NULL = 0b1;
    private static final byte IS_WILD = 0b10;
    private static final byte IS_FED = 0b100;
    private static final byte ANIMAL_ABILITY_TYPE_NULL = 0b1000;
    private static final byte ANIMAL_LOCATION_NULL = 0b10000;
    private static final byte ANIMAL_LOCATION_LONGITUDE_NULL = 0b100000;
    private static final byte ANIMAL_LOCATION_LATITUDE_NULL = 0b1000000;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return age == animal.age
                && isWild == animal.isWild
                && isFed == animal.isFed
                && Objects.equals(name, animal.name)
                && animalAbilityType == animal.animalAbilityType
                && Objects.equals(animalLocation, animal.animalLocation);
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
        byte info = 0;
        if (name == null) {
            info |= NAME_NULL;
        }
        if (isWild) {
            info |= IS_WILD;
        }
        if (isFed) {
            info |= IS_FED;
        }
        if (animalAbilityType == null) {
            info |= ANIMAL_ABILITY_TYPE_NULL;
        }
        if (animalLocation == null) {
            info |= ANIMAL_LOCATION_NULL;
        } else {
            if (animalLocation.getLongitude() == null) {
                info |= ANIMAL_LOCATION_LONGITUDE_NULL;
            }
            if (animalLocation.getLatitude() == null) {
                info |= ANIMAL_LOCATION_LATITUDE_NULL;
            }
        }
        out.writeByte(info);
        // Write Location first as it's an object
        if (animalLocation != null) {
            if (animalLocation.getLongitude() != null) {
                out.writeUTF(animalLocation.getLongitude());
            }
            if (animalLocation.getLatitude() != null) {
                out.writeUTF(animalLocation.getLatitude());
            }
        }
        // Write other fields of superclass
        if (name != null) {
            out.writeUTF(name);
        }
        out.writeInt(age);
        if (animalAbilityType != null) {
            out.writeUTF(animalAbilityType.toString());
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        int info = in.readByte();
        // Read Location first
        if ((info & ANIMAL_LOCATION_NULL) == 0) {
            this.animalLocation = new Location();
            if ((info & ANIMAL_LOCATION_LONGITUDE_NULL) == 0) {
                this.animalLocation.setLongitude(in.readUTF());
            }
            if ((info & ANIMAL_LOCATION_LATITUDE_NULL) == 0) {
                this.animalLocation.setLatitude(in.readUTF());
            }
        }
        // Read other fields
        if ((info & NAME_NULL) == 0) {
            this.name = in.readUTF();
        }
        this.age = in.readInt();
        if ((info & IS_WILD) == IS_WILD) {
            this.isWild = true;
        }
        if ((info & IS_FED) == IS_FED) {
            this.isFed = true;
        }
        if ((info & ANIMAL_ABILITY_TYPE_NULL) == 0) {
            this.animalAbilityType = AnimalAbilityType.valueOf(in.readUTF());
        }
    }
}
