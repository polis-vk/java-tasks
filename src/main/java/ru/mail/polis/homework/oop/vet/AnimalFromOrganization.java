package ru.mail.polis.homework.oop.vet;

public class AnimalFromOrganization extends Animal implements WildAnimal {
    private String organizationName;

    public AnimalFromOrganization(int legs) {
        super(legs);
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
