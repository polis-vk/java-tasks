package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor implements TextProcessor {
    private static final ProcessingStage processingStage = ProcessingStage.PROCESSING;
    private final String regex;
    private final String replacement;

    ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    public ProcessingStage getProcessingStage() {
        return processingStage;
    }

    @Override
    public String getProcessedText(String text) {
        return text.replaceFirst(regex, replacement);
    }
}
