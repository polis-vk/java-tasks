package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    private static final ProcessingStage stage = ProcessingStage.POST_PROCESSING;
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String process(String text) {
        if (text.length() < maxLength) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    @Override
    public int getStage() {
        return stage.getStage();
    }
}
