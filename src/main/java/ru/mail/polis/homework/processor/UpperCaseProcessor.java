package ru.mail.polis.homework.processor;

public class UpperCaseProcessor implements TextProcessor {
    private final ProcessingStage processingStage = ProcessingStage.POSTPROCESSING;

    public ProcessingStage getProcessingStage() {
        return processingStage;
    }

    @Override
    public String getProcessedText(String text) {
        return text.toUpperCase();
    }
}
