package ru.mail.polis.homework.processor;


public class UpperCaseProcessor implements TextProcessor {

    private final static ProcessingStage STAGE = ProcessingStage.POSTPROCESSING;

    @Override
    public String processText(String text) {
        return text.toUpperCase();
    }

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }
}
