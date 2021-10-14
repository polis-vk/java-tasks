package ru.mail.polis.homework.processor;

public class UpperCaseProcessor implements TextProcessor {
    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
