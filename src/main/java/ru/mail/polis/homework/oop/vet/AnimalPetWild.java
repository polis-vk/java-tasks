package ru.mail.polis.homework.oop.vet;

public class AnimalPetWild extends AnimalType implements Pet, WildAnimal {

    private String organizationName;

    public AnimalPetWild(String type) {
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
