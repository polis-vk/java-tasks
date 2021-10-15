package ru.mail.polis.homework.processor;

public class UpperCaseProcessor implements TextProcessor {
    private static final ProcessingStage PROCESSING_STAGE = ProcessingStage.POSTPROCESSING;

    @Override
    public int getStagePriority() {
        return PROCESSING_STAGE.getPriority();
    }

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
