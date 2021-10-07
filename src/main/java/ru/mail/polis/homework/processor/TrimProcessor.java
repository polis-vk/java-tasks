package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    private final ProcessingStage processingStage = ProcessingStage.POSTPROCESSING;
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    public ProcessingStage getProcessingStage() {
        return processingStage;
    }

    @Override
    public String getProcessedText(String text) {
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }
}
