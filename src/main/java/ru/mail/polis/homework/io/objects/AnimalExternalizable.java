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
    private static final byte POISONOUS_BIT = 0b1;
    private static final byte WILD_BIT = 0b10;
    private static final byte ALIAS_NULLABLE_BIT = 0b100;
    private static final byte GENDER_NULLABLE_BIT = 0b1000;
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
        return Objects.equals(alias, animalObj.alias) &&
               legsCount == animalObj.legsCount &&
               poisonous == animalObj.poisonous &&
               wild == animalObj.wild &&
               Objects.equals(organization, animalObj.organization) &&
               Objects.equals(gender, animalObj.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias,
                            legsCount,
                            poisonous,
                            wild,
                            organization,
                            gender);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "alias='" + alias + '\'' +
                ", legsCount=" + legsCount +
                ", poisonous=" + poisonous +
                ", wild=" + wild +
                ", organization=" + organization +
                ", gender=" + gender +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
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

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        byte metaByte = in.readByte();
        setBooleansFromMetaByte(metaByte);
        legsCount = in.readInt();
        if ((ALIAS_NULLABLE_BIT & metaByte) == 0) {
            alias = in.readUTF();
        }
        if ((GENDER_NULLABLE_BIT & metaByte) == 0) {
            gender = Gender.valueOf(in.readUTF());
        }
        organization = (OrganizationExternalizable) in.readObject();
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

class OrganizationExternalizable implements Externalizable {
    private static final byte NAME_NULLABLE_BIT = 0b1;
    private static final byte COUNTRY_NULLABLE_BIT = 0b10;

    private String name;
    private String country;
    private long licenseNumber;

    public OrganizationExternalizable() {
    }

    public OrganizationExternalizable(String name, String country, long licenseNumber) {
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
        return Objects.equals(name, organizationObj.name) &&
               Objects.equals(country, organizationObj.country) &&
               licenseNumber == organizationObj.licenseNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, licenseNumber);
    }

    @Override
    public String toString() {
        return "OrganizationExternalizable{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", licenseNumber=" + licenseNumber +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(getMetaByte());
        if (name != null) {
            out.writeUTF(name);
        }
        if (country != null) {
            out.writeUTF(country);
        }
        out.writeLong(licenseNumber);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
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
