package ru.mail.polis.homework.processor;


public class UpperCaseProcessorImpl implements TextProcessor {

    private final ProcessingStage stage = ProcessingStage.POSTPROCESS;

    @Override
    public String execute(String text) {
        return text.toUpperCase();
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
