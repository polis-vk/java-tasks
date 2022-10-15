package ru.mail.polis.homework.oop.vet;

/**
 * Возможные количества конечностей у животных
 */
public enum LegsNumber {
    FOUR(4), TWO(2), ZERO(0);
    private final int number;

    LegsNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
