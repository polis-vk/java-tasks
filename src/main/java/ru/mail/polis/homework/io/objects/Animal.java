package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 тугрик
 */
public class Animal implements Serializable {
    private String alias;
    private int legsCount;
    private boolean poisonous;
    private boolean wild;
    private Organization organization;
    private Gender gender;

    public Animal(String alias, int legsCount, boolean poisonous,
                  boolean wild, Organization organization, Gender gender) {
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
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
        Animal animalObj = (Animal) obj;
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
        return "Animal{" +
                "alias='" + alias + '\'' +
                ", legsCount=" + legsCount +
                ", poisonous=" + poisonous +
                ", wild=" + wild +
                ", organization=" + organization +
                ", gender=" + gender +
                '}';
    }
}

class Organization implements Serializable {
    private String name;
    private String country;
    private long licenseNumber;

    public Organization(String name, String country, long licenseNumber) {
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
        Organization organizationObj = (Organization) obj;
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
        return "Organization{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", licenseNumber=" + licenseNumber +
                '}';
    }
}
