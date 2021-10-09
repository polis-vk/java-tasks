package ru.mail.polis.homework.processor;

public class upperCaseProcessor implements TextProcessor {
    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
