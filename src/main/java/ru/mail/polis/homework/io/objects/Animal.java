package ru.mail.polis.homework.io.objects;


/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и
 * некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 тугрик
 */
public class Animal {

    private String name;
    private boolean isDomestic;
    private boolean haveClaws;
    private int legsCount;
    private AnimalType animalType;
    private Organization organization;

    public Animal() {
    }

    public Animal(String name, boolean isDomestic, boolean haveClaws, int legsCount, AnimalType animalType,
                  Organization organization) {
        this.name = name;
        this.isDomestic = isDomestic;
        this.haveClaws = haveClaws;
        this.legsCount = legsCount;
        this.animalType = animalType;
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDomestic() {
        return isDomestic;
    }

    public void setDomestic(boolean domestic) {
        isDomestic = domestic;
    }

    public boolean isHaveClaws() {
        return haveClaws;
    }

    public void setHaveClaws(boolean haveClaws) {
        this.haveClaws = haveClaws;
    }

    public int getLegsCount() {
        return legsCount;
    }

    public void setLegsCount(int legsCount) {
        this.legsCount = legsCount;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}

enum AnimalType {

    FISH,
    BIRD,
    MAMMAL,
    REPTILE,
    AMPHIBIAN,
    INVERTEBRATE
}

class Organization {

    private String title;
    private boolean isCommercial;
    private int animalsCount;

    public Organization() {
    }

    public Organization(String title, boolean isCommercial, int animalsCount) {
        this.title = title;
        this.isCommercial = isCommercial;
        this.animalsCount = animalsCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCommercial() {
        return isCommercial;
    }

    public void setCommercial(boolean commercial) {
        isCommercial = commercial;
    }

    public int getAnimalsCount() {
        return animalsCount;
    }

    public void setAnimalsCount(int animalsCount) {
        this.animalsCount = animalsCount;
    }
}
