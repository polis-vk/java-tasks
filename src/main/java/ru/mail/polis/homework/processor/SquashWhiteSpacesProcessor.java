package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", " ");
    }

    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.PREPROCESSING;
    }
}
