package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PRE_PROCESSING(-1),
    PROCESSING(0),
    POST_PROCESSING(1);

    private final int weight;

    ProcessingStage(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
