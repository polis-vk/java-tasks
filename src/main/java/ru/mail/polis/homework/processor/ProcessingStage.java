package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING(0),
    PROCESSING(1),
    POSTPROCESSING(2);

    ProcessingStage(int stage) {
        this.stage = stage;
    }

    public int stage;

    public int getStage() {
        return stage;
    }
}
