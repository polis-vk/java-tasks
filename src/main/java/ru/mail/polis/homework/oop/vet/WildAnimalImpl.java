package ru.mail.polis.homework.oop.vet;

public abstract class WildAnimalImpl extends Animal implements WildAnimal {

    private String organizationName;

    public WildAnimalImpl(int legs, String organizationName) {
        super(legs);
        this.organizationName = organizationName;
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
