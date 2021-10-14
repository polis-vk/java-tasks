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
        if (text.length() <= maxLength) {
            return text;
        } else {
            String buf = text;
            return buf.substring(0, maxLength);
        }
    }
}
