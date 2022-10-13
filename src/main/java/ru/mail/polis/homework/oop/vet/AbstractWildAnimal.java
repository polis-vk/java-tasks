package ru.mail.polis.homework.oop.vet;

public abstract class AbstractWildAnimal extends Animal implements WildAnimal {
    protected String organizationName;
    protected AbstractWildAnimal(int legs) {
        super(legs);
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
