package ru.mail.polis.homework.oop.vet.type;

import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.WildAnimalImpl;

public class Kangaroo extends WildAnimalImpl {

    public Kangaroo(String organizationName) {
        super(2, organizationName);
    }

    public Kangaroo() {
        this(null);
    }

    @Override
    public String say() {
        return "Shha";
    }

    @Override
    public MoveType moveType() {
        return MoveType.JUMP;
    }

}
