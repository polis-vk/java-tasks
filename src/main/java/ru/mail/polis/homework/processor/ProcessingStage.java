package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESS(0),
    PROCESS(1),
    POSTPROCESS(2);

    private final int processingOrder;

    ProcessingStage(int processingOrder){
        this.processingOrder = processingOrder;
    }

    int getProcessingOrder(){
        return processingOrder;
    }
}
