package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor implements TextProcessor {
    private final ProcessingStage stage = ProcessingStage.PROCESSING_STAGE;
    private final String regex;
    private final String replacement;

    ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String processText(String txt) {
        return txt.replaceFirst(regex, replacement);
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
