package ru.mail.polis.homework.processor;

public abstract class AbstractTextProcessor implements TextProcessor {
    final ProcessingStage processingStage;

    public AbstractTextProcessor(ProcessingStage processingStage) {
        this.processingStage = processingStage;
    }

    @Override
    public ProcessingStage processingStage() {
        return processingStage;
    }
}
