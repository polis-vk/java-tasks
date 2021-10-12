package ru.mail.polis.homework.processor;

public class UpperCaseProcessor extends TextProcessor {

    private static final ProcessingStage STAGE = ProcessingStage.POSTPROCESSING;

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
