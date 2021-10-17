package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING(0), PROCESSING(1), POSTPROCESSING(2);
    private final int stageOrder;

    ProcessingStage(int stageOrder) {
        this.stageOrder = stageOrder;
    }

    int getStageOrder() {
        return stageOrder;
    }
}
