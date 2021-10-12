package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor implements TextProcessor {
    private final ProcessingStage stage;
    private final String regex;
    private final String replacement;

    public ReplaceFirstProcessor(ProcessingStage stage, String regex, String replacement) {
        this.stage = stage;
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return stage;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }
}
