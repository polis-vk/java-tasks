package ru.mail.polis.homework.processor;

public class TrimProcessor extends AbstractTextProcessor {
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        super(ProcessingStage.POSTPROCESSING);
        this.maxLength = maxLength;
    }

    @Override
    public String process(String text) {
        return text.substring(0, Integer.min(maxLength, text.length()));
    }
}
