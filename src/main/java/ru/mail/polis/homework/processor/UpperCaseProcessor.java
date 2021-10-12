package ru.mail.polis.homework.processor;

public class UpperCaseProcessor implements TextProcessor {
    private static final ProcessingStage processingStage = ProcessingStage.POSTPROCESSING;

    public int getStagePriority() {
        return processingStage.getPriority();
    }

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
