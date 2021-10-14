package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING(1), PROCESSING(2), POSTPROCESSING(3);

    private final int ordinal;

    ProcessingStage(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return this.ordinal;
    }
}
