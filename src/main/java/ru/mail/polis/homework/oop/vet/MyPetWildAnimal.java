package ru.mail.polis.homework.oop.vet;

public class MyPetWildAnimal extends MyAnimal implements Pet, WildAnimal {
    public MyPetWildAnimal(String type) {
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