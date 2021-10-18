package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    public static final ProcessingStage processingStage = ProcessingStage.PRE_PROCESSING;
    private String text;

    public SquashWhiteSpacesProcessor(String text) {
        this.text = text;
    }

    public TextProcessor squashWhiteSpacesProcessor() {
        text = text.replaceAll("\s+", " ");
        return new ReplaceFirstProcessor(text);
    }
}
