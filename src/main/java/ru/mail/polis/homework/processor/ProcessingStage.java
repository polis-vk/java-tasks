package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESSING_STAGE(0),
    PROCESSING_STAGE(1),
    POSTPROCESSING_STAGE(2);

    private final int stage;

    ProcessingStage(int stage) {
        this.stage = stage;
    }

    int getStage() {
        return stage;
    }

}
