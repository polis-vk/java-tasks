package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING(0), PROCESSING(1), POSTPROCESSING(2);
    private final int index;

    ProcessingStage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
