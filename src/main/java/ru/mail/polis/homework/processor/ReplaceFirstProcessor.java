package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor implements TextProcessor {
    private final String regex;
    private final String replacement;

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.PROCESSING;
    }

    @Override
    public String makeAction(String text) {
        String buf = text;
        return buf.replaceFirst(regex, replacement);
    }
}
