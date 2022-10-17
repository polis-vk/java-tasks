package ru.mail.polis.homework.oop.vet;

public abstract class AbstractWildAnimal extends Animal implements WildAnimal {
    protected String organizationName;

    public AbstractWildAnimal(int legs) {
        super(legs);
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
    }
}
