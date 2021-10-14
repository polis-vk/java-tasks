package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor implements TextProcessor {
    private final String regex;
    private final String replacement;

    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.PROCESSING;
    }

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }
}
