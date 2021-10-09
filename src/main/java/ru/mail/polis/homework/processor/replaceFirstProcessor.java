package ru.mail.polis.homework.processor;

public class replaceFirstProcessor implements TextProcessor {
    private final String regex;
    private final String replacement;

    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.PROCESSING;
    }

    public replaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }
}
