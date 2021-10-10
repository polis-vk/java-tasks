package ru.mail.polis.homework.processor;

public class Processor {
    private ProcessingStage processingStage;

    public Processor(ProcessingStage processingStage) {
        this.processingStage = processingStage;
    }

    public ProcessingStage getStage() {
        return processingStage;
    }
}
