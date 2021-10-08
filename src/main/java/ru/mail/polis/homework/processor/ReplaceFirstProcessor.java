package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor extends AbstractTextProcessor {
    private final String replacement;
    private final String regex;

    public ReplaceFirstProcessor(String regex, String replacement) {
        super(ProcessingStage.PROCESSING);
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }
}
