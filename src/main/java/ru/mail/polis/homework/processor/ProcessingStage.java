package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PRE_PROCESSING(0),
    PROCESSING(1),
    POST_PROCESSING(2);

    public final int order;
    ProcessingStage(int order) {
        this.order = order;
    }
}
