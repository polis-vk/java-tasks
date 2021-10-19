package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor implements TextProcessor {
    String regex;
    String replacement;
    ProcessingStage stage;

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.stage =  ProcessingStage.PROCESSING;
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }

    @Override
    public int getStageIndex() {
        return stage.ordinal();
    }
}
