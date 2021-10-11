package ru.mail.polis.homework.processor;

public class ReplaceFirstTextProcessor implements TextProcessor {

    private static final ProcessingStage PROCESSING_STAGE = ProcessingStage.PROCESSING;
    private final String regex;
    private final String replacement;

    public ReplaceFirstTextProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return PROCESSING_STAGE;
    }
}
