package ru.mail.polis.homework.oop.vet;

public class AnimalType extends Animal {

    private final String type;

    public AnimalType(String type) {
        super(countLegs(type));
        this.type = type;
    }

    @Override
    public String say() {
        switch (type) {
            case ("cat"):
                return "Mow-Mow";
            case ("dog"):
                return "Wow-wow";
            case ("kangaroo"):
                return "Shha";
            case ("pigeon"):
                return "curls-curls";
            case ("cow"):
                return "Moo-Moo";
            case ("shark"):
                return "Clack Clack";
        }
        return "Shhhh"; // snake
    }

    @Override
    public MoveType moveType() {
        if (type.equals("cat") || type.equals("dog") || type.equals("cow")) {
            return MoveType.RUN;
        }
        if (type.equals("kangaroo")) {
            return MoveType.JUMP;
        }
        if (type.equals("pigeon")) {
        return MoveType.FLY;
        }
        if (type.equals("shark")) {
            return MoveType.SWIM;
        }
        return MoveType.CRAWL; // snake
    }

    private static int countLegs(String type) {
        if (type.equals("cat") || type.equals("dog") || type.equals("cow")) {
            return 4;
        }
        if (type.equals("kangaroo") || type.equals("pigeon")) {
            return 2;
        }
        return 0; // snake && shark
    }
}
