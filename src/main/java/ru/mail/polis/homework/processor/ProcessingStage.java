package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING(1),
    PROCESSING(2),
    POSTPROCESSING(3);

    private final int level;

    ProcessingStage(int level) {
        this.level = level;
    }

    int getLevel() {
        return level;
    }
}
