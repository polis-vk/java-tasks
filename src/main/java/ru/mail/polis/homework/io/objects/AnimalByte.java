package ru.mail.polis.homework.io.objects;

import java.util.BitSet;

public class AnimalByte {

    BitSet bitSet;

    public AnimalByte(byte b) {
        this.bitSet = BitSet.valueOf(new byte[]{b});
    }

    public enum AnimalNullObjects {
        ANIMAL_IS_NULL,
        NAME_IS_NOT_NULL,
        IS_FRIENDLY,
        IS_WARM_BLOODED,
        ANIMAL_TYPE_IS_NOT_NULL,
        POPULATION_IS_NOT_NULL
    }

    public AnimalByte(Animal animal) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});
        if (animal != null) {
            bitSet.set(AnimalNullObjects.ANIMAL_IS_NULL.ordinal());
            if (animal.getName() != null) {
                bitSet.set(AnimalNullObjects.NAME_IS_NOT_NULL.ordinal());
            }
            if (animal.isFriendly()) {
                bitSet.set(AnimalNullObjects.IS_FRIENDLY.ordinal());
            }
            if (animal.isWarmBlooded()) {
                bitSet.set(AnimalNullObjects.IS_WARM_BLOODED.ordinal());
            }
            if (animal.getAnimalType() != null) {
                bitSet.set(AnimalNullObjects.ANIMAL_TYPE_IS_NOT_NULL.ordinal());
            }
            if (animal.getPopulation() != null) {
                bitSet.set(AnimalNullObjects.POPULATION_IS_NOT_NULL.ordinal());
            }
        }
    }

    public AnimalByte(AnimalExternalizable animal) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});
        if (animal != null) {
            bitSet.set(AnimalNullObjects.ANIMAL_IS_NULL.ordinal());
            if (animal.getName() != null) {
                bitSet.set(AnimalNullObjects.NAME_IS_NOT_NULL.ordinal());
            }
            if (animal.isFriendly()) {
                bitSet.set(AnimalNullObjects.IS_FRIENDLY.ordinal());
            }
            if (animal.isWarmBlooded()) {
                bitSet.set(AnimalNullObjects.IS_WARM_BLOODED.ordinal());
            }
            if (animal.getAnimalType() != null) {
                bitSet.set(AnimalNullObjects.ANIMAL_TYPE_IS_NOT_NULL.ordinal());
            }
            if (animal.getPopulation() != null) {
                bitSet.set(AnimalNullObjects.POPULATION_IS_NOT_NULL.ordinal());
            }
        }
    }

    public AnimalByte(AnimalWithMethods animal) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});
        if (animal != null) {
            bitSet.set(AnimalNullObjects.ANIMAL_IS_NULL.ordinal());
            if (animal.getName() != null) {
                bitSet.set(AnimalNullObjects.NAME_IS_NOT_NULL.ordinal());
            }
            if (animal.isFriendly()) {
                bitSet.set(AnimalNullObjects.IS_FRIENDLY.ordinal());
            }
            if (animal.isWarmBlooded()) {
                bitSet.set(AnimalNullObjects.IS_WARM_BLOODED.ordinal());
            }
            if (animal.getAnimalType() != null) {
                bitSet.set(AnimalNullObjects.ANIMAL_TYPE_IS_NOT_NULL.ordinal());
            }
            if (animal.getPopulation() != null) {
                bitSet.set(AnimalNullObjects.POPULATION_IS_NOT_NULL.ordinal());
            }
        }
    }

    public boolean animalIsNull() {
        return !bitSet.get(AnimalNullObjects.ANIMAL_IS_NULL.ordinal());
    }

    public boolean nameIsNotNull() {
        return bitSet.get(AnimalNullObjects.NAME_IS_NOT_NULL.ordinal());
    }

    public boolean isFriendly() {
        return bitSet.get(AnimalNullObjects.IS_FRIENDLY.ordinal());
    }

    public boolean isWarmBlooded() {
        return bitSet.get(AnimalNullObjects.IS_WARM_BLOODED.ordinal());
    }

    public boolean animalTypeIsNotNull() {
        return bitSet.get(AnimalNullObjects.ANIMAL_TYPE_IS_NOT_NULL.ordinal());
    }

    public boolean populationIsNotNull() {
        return bitSet.get(AnimalNullObjects.POPULATION_IS_NOT_NULL.ordinal());
    }

    public byte writeByte() {
        byte[] bytes = bitSet.toByteArray();
        return bitSet.toByteArray().length != 0 ? bytes[0] : 0;
    }
}
