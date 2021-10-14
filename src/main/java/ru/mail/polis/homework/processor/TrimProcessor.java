package ru.mail.polis.homework.processor;

public class TrimProcessor extends TextProcessor {

    private static final ProcessingStage STAGE = ProcessingStage.POSTPROCESSING;

    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }

    @Override
    public String process(String text) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength);
    }

}
