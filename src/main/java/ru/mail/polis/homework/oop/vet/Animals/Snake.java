package ru.mail.polis.homework.oop.vet.Animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;
import ru.mail.polis.homework.oop.vet.WildAnimal;

public class Snake extends Animal implements Pet, WildAnimal {

    private String organizationName;

    public Snake(int legs) {
        super(legs);
    }

    @Override
    public MoveType moveType() {
        return MoveType.CRAWL;
    }

    public String say() {
        return "Shhhh";
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
