package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessorImpl implements TextProcessor {

    private static final ProcessingStage stage = ProcessingStage.PROCESS;

    private final String regex;
    private final String replacement;

    ReplaceFirstProcessorImpl(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String processText(String text) {
        return text.replaceFirst(regex, replacement);
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
