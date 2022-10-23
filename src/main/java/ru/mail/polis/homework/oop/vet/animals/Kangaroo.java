package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.WildAnimal;

public class Kangaroo extends Animal implements WildAnimal {
    private String organizationName;

    public Kangaroo(int legs) {
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

    @Override
    public String say() {
        return "Shha";
    }

    @Override
    public MoveType moveType() {
        return MoveType.JUMP;
    }

    @Override
    public int getLegs() {
        return super.getLegs();
    }
}
