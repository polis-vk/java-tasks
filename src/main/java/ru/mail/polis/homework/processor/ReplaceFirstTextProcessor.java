package ru.mail.polis.homework.processor;

public class ReplaceFirstTextProcessor implements TextProcessor {

    private String regex;
    private String replacement;

    public ReplaceFirstTextProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PROCESSING;
    }

    @Override
    public String processText(String text) {
        return text == null ? null : text.replaceFirst(regex, replacement);
    }

}
