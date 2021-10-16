package ru.mail.polis.homework.processor;

// Stage:POSTPROCESSING
public class TrimTextProcessor implements TextProcessor {
    private final int maxLength;
    protected final static int k = 4;

    public TrimTextProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int ordinal() {
        return ProcessingStage.POSTPROCESSING.ordinal;
    }

    @Override
    public String processText(String text) {
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }
}
