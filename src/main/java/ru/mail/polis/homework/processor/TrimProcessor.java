package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {

    private final ProcessingStage STAGE = ProcessingStage.POSTPROCESSING;
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    public String processText(String text) {
        if (text.length() < maxLength) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    public ProcessingStage getStage() {
        return STAGE;
    }
}
