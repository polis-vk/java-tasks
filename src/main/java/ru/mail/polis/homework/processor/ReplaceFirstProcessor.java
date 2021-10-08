package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor extends Processor implements TextProcessor {

    {
        processingStage = ProcessingStage.PROCESSING;
    }

    private final String regex;
    private final String replacement;

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String handle(String data) {
        return data.replaceFirst(regex, replacement);
    }
}
