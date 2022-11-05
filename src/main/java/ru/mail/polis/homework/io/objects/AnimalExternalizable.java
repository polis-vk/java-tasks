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
    private static final byte POISONOUS_BIT_MASK = 0b1;
    private static final byte WILD_BIT_MASK = 0b10;
    private static final byte NULLABLE_BYTE = 0;
    private static final byte NOT_NULLABLE_BYTE = 1;
    private String alias;
    private int legsCount;
    private boolean poisonous;
    private boolean wild;
    private OrganizationExternalizable organization;
    private Gender gender;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String alias, int legsCount, boolean poisonous,
                                boolean wild, OrganizationExternalizable organization, Gender gender) {
        this.alias = alias;
        this.legsCount = legsCount;
        this.poisonous = poisonous;
        this.wild = wild;
        this.organization = organization;
        this.gender = gender;
    }

    public String getAlias() {
        return alias;
    }

    public int getLegsCount() {
        return legsCount;
    }

    public boolean isPoisonous() {
        return poisonous;
    }

    public boolean isWild() {
        return wild;
    }

    public OrganizationExternalizable getOrganization() {
        return organization;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AnimalExternalizable animalObj = (AnimalExternalizable) obj;
        return Objects.equals(alias, animalObj.getAlias()) &&
               legsCount == animalObj.getLegsCount() &&
               poisonous == animalObj.isPoisonous() &&
               wild == animalObj.isWild() &&
               Objects.equals(organization, animalObj.getOrganization()) &&
               Objects.equals(gender, animalObj.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlias(),
                            getLegsCount(),
                            isPoisonous(),
                            isWild(),
                            getOrganization(),
                            getGender());
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "alias='" + getAlias() + '\'' +
                ", legsCount=" + getLegsCount() +
                ", poisonous=" + isPoisonous() +
                ", wild=" + isWild() +
                ", organization=" + getOrganization() +
                ", gender=" + getGender() +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        if (alias == null) {
            out.writeByte(NULLABLE_BYTE);
        } else {
            out.writeByte(NOT_NULLABLE_BYTE);
            out.writeUTF(alias);
        }
        out.writeInt(legsCount);
        out.writeByte(getBooleansMask());
        if (organization == null) {
            out.writeByte(NULLABLE_BYTE);
        } else {
            out.writeByte(NOT_NULLABLE_BYTE);
            out.writeObject(organization);
        }
        if (gender == null) {
            out.writeByte(NULLABLE_BYTE);
        } else {
            out.writeByte(NOT_NULLABLE_BYTE);
            out.writeUTF(gender.name());
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        if (in.readByte() != NULLABLE_BYTE) {
            alias = in.readUTF();
        }
        legsCount = in.readInt();
        setBooleansFromMask(in.readByte());
        if (in.readByte() != NULLABLE_BYTE) {
            organization = (OrganizationExternalizable) in.readObject();
        }
        if (in.readByte() != NULLABLE_BYTE) {
            gender = Gender.valueOf(in.readUTF());
        }
    }

    private byte getBooleansMask() {
        byte mask = 0;
        if (poisonous) {
            mask |= POISONOUS_BIT_MASK;
        }
        if (wild) {
            mask |= WILD_BIT_MASK;
        }
        return mask;
    }

    private void setBooleansFromMask(byte mask) {
        poisonous = (mask & POISONOUS_BIT_MASK) != 0;
        wild = (mask & WILD_BIT_MASK) != 0;
    }
}

class OrganizationExternalizable implements Externalizable {
    private static final byte NULLABLE_BYTE = 0;
    private static final byte NOT_NULLABLE_BYTE = 1;
    private String name;
    private String country;
    private long licenseNumber;

    public OrganizationExternalizable() {
    }

    public OrganizationExternalizable(String name, String country, int licenseNumber) {
        this.name = name;
        this.country = country;
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public long getLicenseNumber() {
        return licenseNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OrganizationExternalizable organizationObj = (OrganizationExternalizable) obj;
        return Objects.equals(name, organizationObj.getName()) &&
               Objects.equals(country, organizationObj.getCountry()) &&
               licenseNumber == organizationObj.getLicenseNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCountry(), getLicenseNumber());
    }

    @Override
    public String toString() {
        return "OrganizationExternalizable{" +
                "name='" + getName() + '\'' +
                ", country='" + getCountry() + '\'' +
                ", licenseNumber=" + getLicenseNumber() +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        if (name == null) {
            out.writeByte(NULLABLE_BYTE);
        } else {
            out.writeByte(NOT_NULLABLE_BYTE);
            out.writeUTF(name);
        }
        if (country == null) {
            out.writeByte(NULLABLE_BYTE);
        } else {
            out.writeByte(NOT_NULLABLE_BYTE);
            out.writeUTF(country);
        }
        out.writeLong(licenseNumber);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        if (in.readByte() != NULLABLE_BYTE) {
            name = in.readUTF();
        }
        if (in.readByte() != NULLABLE_BYTE) {
            country = in.readUTF();
        }
        licenseNumber = in.readLong();
    }
}
