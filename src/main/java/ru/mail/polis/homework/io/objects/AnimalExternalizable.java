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

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getLegsCount() {
        return legsCount;
    }

    public void setLegsCount(int legsCount) {
        this.legsCount = legsCount;
    }

    public boolean isPoisonous() {
        return poisonous;
    }

    public void setPoisonous(boolean poisonous) {
        this.poisonous = poisonous;
    }

    public boolean isWild() {
        return wild;
    }

    public void setWild(boolean wild) {
        this.wild = wild;
    }

    public OrganizationExternalizable getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationExternalizable organization) {
        this.organization = organization;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
        return getAlias().equals(animalObj.getAlias()) &&
                getLegsCount() == animalObj.getLegsCount() &&
                isPoisonous() == animalObj.isPoisonous() &&
                isWild() == animalObj.isWild() &&
                organization.equals(animalObj.getOrganization()) &&
                getGender() == animalObj.getGender();
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
        out.writeUTF(alias);
        out.writeInt(legsCount);
        out.writeBoolean(poisonous);
        out.writeBoolean(wild);
        out.writeObject(organization);
        out.writeUTF(gender.name());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        alias = in.readUTF();
        legsCount = in.readInt();
        poisonous = in.readBoolean();
        wild = in.readBoolean();
        organization = (OrganizationExternalizable) in.readObject();
        gender = Gender.valueOf(in.readUTF());
    }
}

class OrganizationExternalizable implements Externalizable {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(long licenseNumber) {
        this.licenseNumber = licenseNumber;
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
        return getName().equals(organizationObj.getName()) &&
                getCountry().equals(organizationObj.getCountry()) &&
                getLicenseNumber() == organizationObj.getLicenseNumber();
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
        out.writeUTF(name);
        out.writeUTF(country);
        out.writeLong(licenseNumber);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        country = in.readUTF();
        licenseNumber = in.readLong();
    }
}