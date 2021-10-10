package ru.mail.polis.homework.processor;

/**
 * Стадия обработки текста (2 балла)
 */
public enum ProcessingStage {
    PREPROCESS(0), 
    PROCESS(1), 
    POSTPROCESS(2);

    // честно не понимаю чем плох .ordinal() в конкретной ситуации

    private final int order;

    ProcessingStage(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

}
