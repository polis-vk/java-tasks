package ru.mail.polis.homework.processor;

public class ShrinkProcessor implements TextProcessor {
    private final ProcessingStage stage = ProcessingStage.POSTPROCESSING;
    private final int maxLength;

    ShrinkProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String execute(String text) {
        if (maxLength >= text.length()) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
