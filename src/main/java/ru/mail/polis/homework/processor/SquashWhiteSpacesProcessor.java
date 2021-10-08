package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PREPROCESSING;
    }

    @Override
    public String execute(String str) {
        return str.replaceAll("\\s+", " ");
    }
}
