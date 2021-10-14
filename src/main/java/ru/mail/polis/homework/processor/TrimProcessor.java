package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {

    private final static ProcessingStage STAGE = ProcessingStage.POSTPROCESSING;
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String processText(String text) {
        try {
            return text.substring(0, maxLength);
        } catch (IndexOutOfBoundsException e) {
            return text;
        }
    }

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }
}
