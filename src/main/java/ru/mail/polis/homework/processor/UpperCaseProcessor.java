package ru.mail.polis.homework.processor;

public class UpperCaseProcessor implements TextProcessor {
    private static final ProcessingStage stage = ProcessingStage.POSTPROCESSING;

    @Override
    public ProcessingStage getProcessingStage() {
        return stage;
    }

    @Override
    public String processText(String text) {
        return text.toUpperCase();
    }
}
