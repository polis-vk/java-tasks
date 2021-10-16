package ru.mail.polis.homework.processor;

// Stage:PROCESSING
public class ReplaceFirstTextProcessor implements TextProcessor {

    private final String regex;
    private final String replacement;

    public ReplaceFirstTextProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public int ordinal() {
        return ProcessingStage.PROCESSING.ordinal;
    }

    @Override
    public String processText(String text) {
        return text.replaceFirst(regex, replacement);
    }

}
