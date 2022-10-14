package ru.mail.polis.homework.oop.vet;

public abstract class AbstractWildAnimal extends AbstractAnimal implements WildAnimal {
    protected String organizationName;

    protected AbstractWildAnimal(int legs) {
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
