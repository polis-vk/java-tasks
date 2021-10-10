package ru.mail.polis.homework.processor;

public abstract class AbstractTextProcessor implements TextProcessor {
    private final ProcessingStage processingStage;

    public AbstractTextProcessor(ProcessingStage processingStage) {
        this.processingStage = processingStage;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return processingStage;
    }
}
