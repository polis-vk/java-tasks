package ru.mail.polis.homework.oop.vet;

public class CustomWildAnimal extends CustomAnimal implements WildAnimal {
    public CustomWildAnimal(String type) {
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