package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private static final byte POISONOUS_BIT = 0b1;
    private static final byte WILD_BIT = 0b10;
    private static final byte ALIAS_NULLABLE_BIT = 0b100;
    private static final byte GENDER_NULLABLE_BIT = 0b1000;

    private String alias;
    private int legsCount;
    private boolean poisonous;
    private boolean wild;
    private OrganizationWithMethods organization;
    private Gender gender;

    public AnimalWithMethods(String alias, int legsCount, boolean poisonous,
                             boolean wild, OrganizationWithMethods organization, Gender gender) {
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

    public OrganizationWithMethods getOrganization() {
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
        AnimalWithMethods animalObj = (AnimalWithMethods) obj;
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
        return "AnimalWithMethods{" +
                "alias='" + getAlias() + '\'' +
                ", legsCount=" + getLegsCount() +
                ", poisonous=" + isPoisonous() +
                ", wild=" + isWild() +
                ", organization=" + getOrganization() +
                ", gender=" + getGender() +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeByte(getMetaByte());
        out.writeInt(legsCount);
        if (alias != null) {
            out.writeUTF(alias);
        }
        if (gender != null) {
            out.writeUTF(gender.name());
        }
        out.writeObject(organization);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        byte metaByte = in.readByte();
        setBooleansFromMetaByte(metaByte);
        legsCount = in.readInt();
        if ((ALIAS_NULLABLE_BIT & metaByte) == 0) {
            alias = in.readUTF();
        }
        if ((GENDER_NULLABLE_BIT & metaByte) == 0) {
            gender = Gender.valueOf(in.readUTF());
        }
        organization = (OrganizationWithMethods) in.readObject();
    }

    private void setBooleansFromMetaByte(byte metaByte) {
        poisonous = (metaByte & POISONOUS_BIT) != 0;
        wild = (metaByte & WILD_BIT) != 0;
    }

    private byte getMetaByte() {
        byte metaByte = 0;
        if (poisonous) {
            metaByte |= POISONOUS_BIT;
        }
        if (wild) {
            metaByte |= WILD_BIT;
        }
        if (alias == null) {
            metaByte |= ALIAS_NULLABLE_BIT;
        }
        if (gender == null) {
            metaByte |= GENDER_NULLABLE_BIT;
        }
        return metaByte;
    }
}

class OrganizationWithMethods implements Serializable {
    private static final byte NAME_NULLABLE_BIT = 0b1;
    private static final byte COUNTRY_NULLABLE_BIT = 0b10;
    private String name;
    private String country;
    private long licenseNumber;

    public OrganizationWithMethods(String name, String country, long licenseNumber) {
        this.name = name;
        this.country = country;
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        OrganizationWithMethods organizationObj = (OrganizationWithMethods) obj;
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
        return "OrganizationWithMethods{" +
                "name='" + getName() + '\'' +
                ", country='" + getCountry() + '\'' +
                ", licenseNumber=" + getLicenseNumber() +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeByte(getMetaByte());
        if (name != null) {
            out.writeUTF(name);
        }
        if (country != null) {
            out.writeUTF(country);
        }
        out.writeLong(licenseNumber);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        byte metaByte = in.readByte();
        if ((metaByte & NAME_NULLABLE_BIT) == 0) {
            name = in.readUTF();
        }
        if ((metaByte & COUNTRY_NULLABLE_BIT) == 0) {
            country = in.readUTF();
        }
        licenseNumber = in.readLong();
    }

    private byte getMetaByte() {
        byte metaByte = 0;
        if (name == null) {
            metaByte |= NAME_NULLABLE_BIT;
        }
        if (country == null) {
            metaByte |= COUNTRY_NULLABLE_BIT;
        }
        return metaByte;
    }
}
