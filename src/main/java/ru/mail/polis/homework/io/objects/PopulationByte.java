package ru.mail.polis.homework.io.objects;

import java.util.BitSet;

public class PopulationByte {

    BitSet bitSet;

    public PopulationByte(byte b) {
        this.bitSet = BitSet.valueOf(new byte[]{b});
    }

    public enum PopulationNullObjects {
        POPULATION_IS_NULL,
        NAME_IS_NOT_NULL
    }

    public PopulationByte(Population population) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});
        if (population != null) {
            bitSet.set(PopulationNullObjects.POPULATION_IS_NULL.ordinal());
            if (population.getName() != null) {
                bitSet.set(PopulationNullObjects.NAME_IS_NOT_NULL.ordinal());
            }
        }
    }

    public PopulationByte(PopulationExternalizable population) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});
        if (population != null) {
            bitSet.set(PopulationNullObjects.POPULATION_IS_NULL.ordinal());
            if (population.getName() != null) {
                bitSet.set(PopulationNullObjects.NAME_IS_NOT_NULL.ordinal());
            }
        }
    }

    public PopulationByte(PopulationWithMethods population) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});
        if (population != null) {
            bitSet.set(PopulationNullObjects.POPULATION_IS_NULL.ordinal());
            if (population.getName() != null) {
                bitSet.set(PopulationNullObjects.NAME_IS_NOT_NULL.ordinal());
            }
        }
    }

    public boolean populationIsNull() {
        return !bitSet.get(PopulationNullObjects.POPULATION_IS_NULL.ordinal());
    }

    public boolean nameIsNotNull() {
        return bitSet.get(PopulationNullObjects.NAME_IS_NOT_NULL.ordinal());
    }

    public byte writeByte() {
        byte[] bytes = bitSet.toByteArray();
        return bitSet.toByteArray().length != 0 ? bytes[0] : 0;
    }
}
