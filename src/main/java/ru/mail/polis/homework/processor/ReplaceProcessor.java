package ru.mail.polis.homework.processor;

public class ReplaceProcessor implements TextProcessor{

    private final String regex;
    private final String replacement;
    private final boolean allReplace;

    public ReplaceProcessor(String regex, String replacement, boolean allReplace) {
        this.regex = regex;
        this.replacement = replacement;
        this.allReplace = allReplace;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PROCESSING_STAGE;
    }

    @Override
    public String process(String text) {
        return allReplace ? text.replaceAll(regex, replacement) : text.replaceFirst(regex, replacement);
    }
}
