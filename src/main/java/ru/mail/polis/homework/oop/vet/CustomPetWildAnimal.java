package ru.mail.polis.homework.oop.vet;

public class CustomPetWildAnimal extends CustomAnimal implements Pet, WildAnimal {
    public CustomPetWildAnimal(String type) {
        super(type);
    }

    private String organizationName = "";

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}