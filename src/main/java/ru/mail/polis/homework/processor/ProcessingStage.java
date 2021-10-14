package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PRE_PROCESSING(0),
    PROCESSING(1),
    POST_PROCESSING(2);

    private final int stage;

    ProcessingStage(int stage) {
        this.stage = stage;
    }

    public int getStage() {
        return stage;
    }
}
