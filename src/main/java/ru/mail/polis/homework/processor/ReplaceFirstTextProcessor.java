package ru.mail.polis.homework.processor;

// Stage:PROCESSING
public class ReplaceFirstTextProcessor implements TextProcessor {

    private String regex;
    private String replacement;

    public ReplaceFirstTextProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public int ordinal() {
        return 1;
    }

    @Override
    public String processText(String text) {
        return text.replaceFirst(regex, replacement);
    }

}
