package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor implements TextProcessor {
    private static final ProcessingStage stage = ProcessingStage.PROCESSING;
    private final String regex;
    private final String replacement;

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }

    @Override
    public int getStage() {
        return stage.getStage();
    }
}
