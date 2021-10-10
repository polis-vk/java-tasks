package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    private static final ProcessingStage stage = ProcessingStage.POSTPROCESSING;
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return stage;
    }

    @Override
    public String processText(String text) {
        return text.length() > maxLength ? text.substring(0, maxLength) : text;
    }
}
