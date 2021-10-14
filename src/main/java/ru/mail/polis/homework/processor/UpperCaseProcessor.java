package ru.mail.polis.homework.processor;

public class UpperCaseProcessor implements TextProcessor {
    public UpperCaseProcessor() {
    }

    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String makeAction(String text) {
        String buf = text;
        return buf.toUpperCase();
    }
}
