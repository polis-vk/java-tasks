package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    public SquashWhiteSpacesProcessor() {
    }

    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.PREPROCESSING;
    }

    @Override
    public String makeAction(String text) {
        String buf = text;
        return buf.replaceAll("\\s+", " ");
    }
}
