package ru.mail.polis.homework.oop.vet.type;

import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.WildAnimalImpl;

public class Shark extends WildAnimalImpl {

    public Shark(String organizationName) {
        super(0, organizationName);
    }

    public Shark() {
        this(null);
    }

    @Override
    public String say() {
        return "Clack Clack";
    }

    @Override
    public MoveType moveType() {
        return MoveType.SWIM;
    }

}
