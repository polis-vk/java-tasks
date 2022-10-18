package ru.mail.polis.homework.oop.vet;

public abstract class AnyWildAnimal extends Animal implements WildAnimal{

    private String nameOfVet;

    public AnyWildAnimal(int legs) {
        super(legs);
    }

    @Override
    public String getOrganizationName() {
        return nameOfVet;
    }

    @Override
    public void setOrganizationName(String nameOfVet) {
        this.nameOfVet = nameOfVet;
    }
}
