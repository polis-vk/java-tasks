package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.AbstractAnimal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;
import ru.mail.polis.homework.oop.vet.WildAnimal;

public class Snake extends AbstractAnimal implements Pet, WildAnimal {
    private String organizationName;

    public Snake() {
        super(0);
    }

    @Override
    public String say() {
        return "Shhhh";
    }

    @Override
    public MoveType moveType() {
        return MoveType.CRAWL;
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
