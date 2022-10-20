package ru.mail.polis.homework.oop.vet;

public class MyWildAnimal extends MyAnimal implements WildAnimal {
    public MyWildAnimal(String type) {
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