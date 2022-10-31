package ru.mail.polis.homework.oop.vet.types;

public enum AnimalParamsType {
    CAT(4, MoveType.RUN, "Mow-Mow", FreedomType.PET),
    DOG(4, MoveType.RUN, "Wow-wow", FreedomType.PET),
    KANGAROO(2, MoveType.JUMP, "Shha", FreedomType.WILD),
    PIGEON(2, MoveType.FLY, "curls-curls", FreedomType.ALL),
    COW(4, MoveType.RUN, "Moo-Moo", FreedomType.PET),
    SHARK(0, MoveType.SWIM, "Сlack Сlack", FreedomType.WILD),
    SNAKE(0, MoveType.CRAWL, "Shhhh", FreedomType.ALL);

    private final int legs;
    private final MoveType moveType;
    private final String voice;
    private final FreedomType freedomType;

    AnimalParamsType(int legs, MoveType moveType, String voice, FreedomType freedomType) {
        this.legs = legs;
        this.moveType = moveType;
        this.voice = voice;
        this.freedomType = freedomType;
    }

    public int getLegs() {
        return legs;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public String getVoice() {
        return voice;
    }

    public FreedomType getFreedomType() {
        return freedomType;
    }
}
