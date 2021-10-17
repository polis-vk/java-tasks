package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    private final ProcessingStage STAGE = ProcessingStage.POSTPROCESSING;
    private int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String processText(String text) {
        if (maxLength >= text.length()) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }
}
