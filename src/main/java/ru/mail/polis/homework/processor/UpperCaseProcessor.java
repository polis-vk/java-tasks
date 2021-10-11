package ru.mail.polis.homework.processor;

public class UpperCaseProcessor implements TextProcessor {

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String apply(String str) {
        return str.toUpperCase();
    }
}
