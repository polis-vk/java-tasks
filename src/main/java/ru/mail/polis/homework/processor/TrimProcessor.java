package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    public static final ProcessingStage processingStage = ProcessingStage.POST_PROCESSING;
    private String text;

    public TrimProcessor(String text) {
        this.text = text;
    }

    public TextProcessorManager trimProcessor(int maxLength) {
        text = text.length() <= maxLength ? text : text.substring(0, maxLength);
        return null;
    }
}
