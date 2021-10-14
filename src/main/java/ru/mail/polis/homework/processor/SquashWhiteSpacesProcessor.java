package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.PREPROCESSING;
    }

    @Override
    public String makeAction(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
