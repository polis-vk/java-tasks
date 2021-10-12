package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    private static final ProcessingStage processingStage = ProcessingStage.POSTPROCESSING;
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int getStagePriority() {
        return processingStage.getPriority();
    }

    @Override
    public String process(String text) {
        return text.substring(0, Math.min(maxLength, text.length()));
    }
}
