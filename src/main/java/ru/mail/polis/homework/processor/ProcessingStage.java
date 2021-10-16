package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING(0),
    PROCESSING(1),
    POSTPROCESSING(2);

    int ordinal;
    ProcessingStage(int ordinal) {
        this.ordinal = ordinal;
    }
}
