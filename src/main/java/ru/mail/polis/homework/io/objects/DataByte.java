package ru.mail.polis.homework.io.objects;

import java.util.BitSet;

import static ru.mail.polis.homework.io.objects.DataByte.BitType.*;

public class DataByte {

    enum BitType {

        IS_COMMERCIAL,
        IS_HAVE_CLAWS,
        IS_DOMESTIC,
        IS_TITLE_NOT_NULL,
        IS_ORG_NOT_NULL,
        IS_TYPE_NOT_NULL,
        IS_NAME_NOT_NULL,
        IS_ANIMAL_NOT_NULL
    }

    private final BitSet bitSet;

    public DataByte(byte curByte) {
        bitSet = BitSet.valueOf(new byte[]{curByte});
    }

    public DataByte(AnimalWithMethods animal) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});

        if (animal != null) {
            bitSet.set(IS_ANIMAL_NOT_NULL.ordinal());
            if (animal.getName() != null) {
                bitSet.set(IS_NAME_NOT_NULL.ordinal());
            }
            if (animal.isDomestic()) {
                bitSet.set(IS_DOMESTIC.ordinal());
            }
            if (animal.isHaveClaws()) {
                bitSet.set(IS_HAVE_CLAWS.ordinal());
            }
            if (animal.getAnimalTypeWithMethods() != null) {
                bitSet.set(IS_TYPE_NOT_NULL.ordinal());
            }
            OrganizationWithMethods org = animal.getOrganizationWithMethods();
            if (org != null) {
                bitSet.set(IS_ORG_NOT_NULL.ordinal());
                if (org.isCommercial()) {
                    bitSet.set(IS_COMMERCIAL.ordinal());
                }
                if (org.getTitle() != null) {
                    bitSet.set(IS_TITLE_NOT_NULL.ordinal());
                }
            }
        }
    }

    public DataByte(AnimalExternalizable animal) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});

        if (animal != null) {
            bitSet.set(IS_ANIMAL_NOT_NULL.ordinal());
            if (animal.getName() != null) {
                bitSet.set(IS_NAME_NOT_NULL.ordinal());
            }
            if (animal.isDomestic()) {
                bitSet.set(IS_DOMESTIC.ordinal());
            }
            if (animal.isHaveClaws()) {
                bitSet.set(IS_HAVE_CLAWS.ordinal());
            }
            if (animal.getAnimalTypeExternalizable() != null) {
                bitSet.set(IS_TYPE_NOT_NULL.ordinal());
            }
            OrganizationExternalizable org = animal.getOrganizationExternalizable();
            if (org != null) {
                bitSet.set(IS_ORG_NOT_NULL.ordinal());
                if (org.isCommercial()) {
                    bitSet.set(IS_COMMERCIAL.ordinal());
                }
                if (org.getTitle() != null) {
                    bitSet.set(IS_TITLE_NOT_NULL.ordinal());
                }
            }
        }
    }

    public DataByte(Animal animal) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});

        if (animal != null) {
            bitSet.set(IS_ANIMAL_NOT_NULL.ordinal());
            if (animal.getName() != null) {
                bitSet.set(IS_NAME_NOT_NULL.ordinal());
            }
            if (animal.isDomestic()) {
                bitSet.set(IS_DOMESTIC.ordinal());
            }
            if (animal.isHaveClaws()) {
                bitSet.set(IS_HAVE_CLAWS.ordinal());
            }
            if (animal.getAnimalType() != null) {
                bitSet.set(IS_TYPE_NOT_NULL.ordinal());
            }
            Organization org = animal.getOrganization();
            if (org != null) {
                bitSet.set(IS_ORG_NOT_NULL.ordinal());
                if (org.isCommercial()) {
                    bitSet.set(IS_COMMERCIAL.ordinal());
                }
                if (org.getTitle() != null) {
                    bitSet.set(IS_TITLE_NOT_NULL.ordinal());
                }
            }
        }
    }

    public byte getByte() {
        byte[] result = bitSet.toByteArray();
        if (result.length == 0) {
            return (byte) 0;
        }
        return result[0];
    }

    public boolean isCommercial() {
        return bitSet.get(IS_COMMERCIAL.ordinal());
    }

    public boolean isHaveClaws() {
        return bitSet.get(IS_HAVE_CLAWS.ordinal());
    }

    public boolean isDomestic() {
        return bitSet.get(IS_DOMESTIC.ordinal());
    }

    public boolean isTitleNotNull() {
        return bitSet.get(IS_TITLE_NOT_NULL.ordinal());
    }

    public boolean isOrgNotNull() {
        return bitSet.get(IS_ORG_NOT_NULL.ordinal());
    }

    public boolean isTypeNotNull() {
        return bitSet.get(IS_TYPE_NOT_NULL.ordinal());
    }

    public boolean isNameNotNull() {
        return bitSet.get(IS_NAME_NOT_NULL.ordinal());
    }

    public boolean isAnimalNotNull() {
        return bitSet.get(IS_ANIMAL_NOT_NULL.ordinal());
    }

}
