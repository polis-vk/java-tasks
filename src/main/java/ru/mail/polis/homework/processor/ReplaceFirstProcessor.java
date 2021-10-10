package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor extends NullSafeTextProcessor implements TextProcessor {
    private final String regex;
    private final String replacement;

    public ReplaceFirstProcessor(String regex, String replacement)
    {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String processNotNullText(String text) {
        return text.replaceFirst(regex, replacement);
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PROCESSING;
    }
}
