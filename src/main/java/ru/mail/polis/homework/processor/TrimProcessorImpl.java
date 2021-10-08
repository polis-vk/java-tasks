package ru.mail.polis.homework.processor;

public class TrimProcessorImpl implements TextProcessor {

    private final ProcessingStage stage = ProcessingStage.POSTPROCESS;

    private final int maxLength;

    TrimProcessorImpl(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String execute(String text) {
        if (maxLength > text.length()) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
