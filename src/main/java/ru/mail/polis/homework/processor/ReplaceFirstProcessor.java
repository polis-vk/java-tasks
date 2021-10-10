package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor extends Processor implements TextProcessor {

    private final String regex;
    private final String replacement;

    public ReplaceFirstProcessor(String regex, String replacement) {
        super(ProcessingStage.PROCESSING);
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String process(String data) {
        return data.replaceFirst(regex, replacement);
    }
}
