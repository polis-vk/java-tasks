package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String makeAction(String text) {
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }
}
