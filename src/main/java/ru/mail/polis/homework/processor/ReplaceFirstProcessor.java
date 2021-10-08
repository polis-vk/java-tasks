package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor implements TextProcessor {

    private String regex;
    private String replacement;

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PROCESSING;
    }

    @Override
    public String execute(String str) {
        return str.replaceFirst(regex, replacement);
    }
}
