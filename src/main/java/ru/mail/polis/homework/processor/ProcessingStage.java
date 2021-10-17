package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESS(2),
    PROCESS(1),
    POSTPROCESS(0);

    private int priority;

    ProcessingStage(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }
}
