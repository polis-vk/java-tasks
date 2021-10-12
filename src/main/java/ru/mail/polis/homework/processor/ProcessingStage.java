package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING(0), PROCESSING(1), POSTPROCESSING(2);

    private final int priority;

    ProcessingStage(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
