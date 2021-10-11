package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {

    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String apply(String str) {
        if (str.length() > maxLength) {
            return str.substring(0, maxLength);
        }
        return str;
    }
}
