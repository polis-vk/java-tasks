package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor extends TextProcessor {

    private final String regex;
    private final String replacement;

    ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    private static final ProcessingStage STAGE = ProcessingStage.PROCESSING;

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }
}
