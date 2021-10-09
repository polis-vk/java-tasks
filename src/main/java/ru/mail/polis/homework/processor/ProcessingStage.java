package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING(1),
    PROCESSING(2),
    POSTPROCESSING(3);

    private final int order;

    ProcessingStage(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

}
