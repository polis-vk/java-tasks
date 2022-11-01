package ru.mail.polis.homework.oop.vet;

public class CustomAnimal extends Animal {
    private final String type;

    public CustomAnimal(String type) {
        super(countLegs(type));
        this.type = type;
    }

    @Override
    public String say() {
        return generatePhrase(this.type);
    }

    @Override
    public MoveType moveType() {
        return getMoveType(this.type);
    }

    private static int countLegs(String type) {
        int legCount;
        if ("cat".equals(type) || "dog".equals(type) || "cow".equals(type)) {
            legCount = 4;
        } else if ("kangaroo".equals(type) || "pigeon".equals(type)) {
            legCount = 2;
        } else {
            legCount = 0;
        }
        return legCount;
    }

    private String generatePhrase(String type) {
        String phrase = "";
        switch (type) {
            case ("cat"):
                phrase = "Mow-Mow";
                break;
            case ("dog"):
                phrase = "Wow-wow";
                break;
            case ("kangaroo"):
                phrase = "Shha";
                break;
            case ("pigeon"):
                phrase = "curls-curls";
                break;
            case ("cow"):
                phrase = "Moo-Moo";
                break;
            case ("shark"):
                phrase = "Сlack Сlack";
                break;
            case ("snake"):
                phrase = "Shhhh";
        }
        return phrase;
    }

    private MoveType getMoveType(String type) {
        MoveType moveType;
        if ("cat".equals(type) || "dog".equals(type) || "cow".equals(type)) {
            moveType = MoveType.RUN;
        } else if ("kangaroo".equals(type)) {
            moveType = MoveType.JUMP;
        } else if ("shark".equals(type)) {
            moveType = MoveType.SWIM;
        } else if ("snake".equals(type)) {
            moveType = MoveType.CRAWL;
        } else {
            moveType = MoveType.FLY;
        }
        return moveType;
    }
}