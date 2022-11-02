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
    private String alias;
    private int legsCount;
    private boolean poisonous;
    private boolean wild;
    private OrganizationWithMethods organization;
    private Gender gender;

    public AnimalWithMethods() {
    }

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

    public OrganizationWithMethods getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationWithMethods organization) {
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
        AnimalWithMethods animalObj = (AnimalWithMethods) obj;
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
        out.writeUTF(alias);
        out.writeInt(legsCount);
        out.writeBoolean(poisonous);
        out.writeBoolean(wild);
        out.writeObject(organization);
        out.writeUTF(gender.name());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        alias = in.readUTF();
        legsCount = in.readInt();
        poisonous = in.readBoolean();
        wild = in.readBoolean();
        organization = (OrganizationWithMethods) in.readObject();
        gender = Gender.valueOf(in.readUTF());
    }
}

class OrganizationWithMethods implements Serializable {
    private String name;
    private String country;
    private long licenseNumber;

    public OrganizationWithMethods() {
    }

    public OrganizationWithMethods(String name, String country, int licenseNumber) {
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

        OrganizationWithMethods organizationObj = (OrganizationWithMethods) obj;
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
        return "OrganizationWithMethods{" +
                "name='" + getName() + '\'' +
                ", country='" + getCountry() + '\'' +
                ", licenseNumber=" + getLicenseNumber() +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeUTF(country);
        out.writeLong(licenseNumber);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        country = in.readUTF();
        licenseNumber = in.readLong();
    }
}