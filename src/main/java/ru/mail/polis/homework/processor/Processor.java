package ru.mail.polis.homework.processor;

public class Processor {
    private final ProcessingStage processingStage;

    public Processor(ProcessingStage processingStage) {
        this.processingStage = processingStage;
    }

    public final ProcessingStage getStage() {
        return processingStage;
    }
}
