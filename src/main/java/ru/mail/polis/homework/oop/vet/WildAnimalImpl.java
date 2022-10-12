package ru.mail.polis.homework.oop.vet;

public class WildAnimalImpl extends Animal implements WildAnimal {
    protected String organizationName;
    protected WildAnimalImpl(int legs) {
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
