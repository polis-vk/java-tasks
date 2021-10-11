package ru.mail.polis.homework.processor;


public class UpperCaseProcessorImpl implements TextProcessor {

    private static final ProcessingStage stage = ProcessingStage.POSTPROCESS;

    @Override
    public String processText(String text) {
        return text.toUpperCase();
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
