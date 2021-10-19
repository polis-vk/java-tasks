package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    ProcessingStage stage;
    public SquashWhiteSpacesProcessor() {
        this.stage = ProcessingStage.PREPROCESSING;
    }

    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", " ");
    }

    @Override
    public int getStageIndex() {
        return stage.ordinal();
    }
}
