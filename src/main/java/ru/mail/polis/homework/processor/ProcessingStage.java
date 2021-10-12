package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING(1),
    PROCESSING(2),
    POSTPROCESSING(3);

    private final int stageNumber;

    ProcessingStage(int stage) {
        stageNumber = stage;
    }

    public int getStageNumber() {
        return stageNumber;
    }
}
