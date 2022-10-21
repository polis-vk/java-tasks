package ru.mail.polis.homework.oop.vet;

public class AnimalWild extends AnimalType implements WildAnimal {

    private String organizationName;

    public AnimalWild(String type) {
        super(type);
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }
}
