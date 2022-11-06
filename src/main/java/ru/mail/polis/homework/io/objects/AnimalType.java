package ru.mail.polis.homework.io.objects;

public enum AnimalType {
    CAT((byte) 1),
    COW((byte) 2),
    DOG((byte) 3),
    KANGAROO((byte) 4),
    PIGEON((byte) 5),
    SHARK((byte) 6),
    SNAKE((byte) 7);

    private final byte ordinal;

    AnimalType(byte b) {
        this.ordinal = b;
    }

    public byte getOrdinal() {
        return ordinal;
    }

    public static AnimalType getOrdinal(byte b) {
        for (AnimalType animal : AnimalType.values()) {
            if (animal.ordinal == b) {
                return animal;
            }
        }
        return null;
    }
}
