package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PRE_PROCESSING,
    PROCESSING,
    POST_PROCESSING;

    int priority = this.ordinal();
}
