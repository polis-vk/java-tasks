package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor implements TextProcessor {
    public static final ProcessingStage processingStage = ProcessingStage.PROCESSING;
    private String text;

    public ReplaceFirstProcessor(String text) {
        this.text = text;
    }

    public TextProcessor replaceFirstProcessor(String regex, String replacement) {
        text = text.replaceAll(regex, replacement);
        return new TrimProcessor(text);
    }
}
