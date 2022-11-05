package ru.mail.polis.homework.io.objects;

import java.util.BitSet;

import static ru.mail.polis.homework.io.objects.OrganizationDataByte.OrganizationBitType.*;

public class OrganizationDataByte {

    enum OrganizationBitType {

        IS_COMMERCIAL,
        IS_TITLE_NOT_NULL,
        IS_ORG_NOT_NULL,
    }

    private final BitSet bitSet;

    public OrganizationDataByte(byte curByte) {
        bitSet = BitSet.valueOf(new byte[]{curByte});
    }

    public OrganizationDataByte(OrganizationWithMethods organization) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});

        if (organization != null) {
            bitSet.set(IS_ORG_NOT_NULL.ordinal());
            if (organization.getTitle() != null) {
                bitSet.set(IS_TITLE_NOT_NULL.ordinal());
            }
            if (organization.isCommercial()) {
                bitSet.set(IS_COMMERCIAL.ordinal());
            }
        }
    }

    public OrganizationDataByte(OrganizationExternalizable organization) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});

        if (organization != null) {
            bitSet.set(IS_ORG_NOT_NULL.ordinal());
            if (organization.getTitle() != null) {
                bitSet.set(IS_TITLE_NOT_NULL.ordinal());
            }
            if (organization.isCommercial()) {
                bitSet.set(IS_COMMERCIAL.ordinal());
            }
        }
    }


    public OrganizationDataByte(Organization organization) {
        bitSet = BitSet.valueOf(new byte[]{(byte) 0});

        if (organization != null) {
            bitSet.set(IS_ORG_NOT_NULL.ordinal());
            if (organization.getTitle() != null) {
                bitSet.set(IS_TITLE_NOT_NULL.ordinal());
            }
            if (organization.isCommercial()) {
                bitSet.set(IS_COMMERCIAL.ordinal());
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

    public boolean isTitleNotNull() {
        return bitSet.get(IS_TITLE_NOT_NULL.ordinal());
    }

    public boolean isOrgNotNull() {
        return bitSet.get(IS_ORG_NOT_NULL.ordinal());
    }
}
