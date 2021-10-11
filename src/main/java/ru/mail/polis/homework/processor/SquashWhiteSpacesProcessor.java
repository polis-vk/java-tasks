package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    private static final ProcessingStage processingStage = ProcessingStage.PREPROCESSING;

    public ProcessingStage getProcessingStage() {
        return processingStage;
    }

    @Override
    public String getProcessedText(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
