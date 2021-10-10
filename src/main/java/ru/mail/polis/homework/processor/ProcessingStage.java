package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PRE_PROCESSING_STAGE(2),
    PROCESSING_STAGE(1),
    POST_PROCESSING_STAGE(0);

    private final int priority;

    ProcessingStage(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
