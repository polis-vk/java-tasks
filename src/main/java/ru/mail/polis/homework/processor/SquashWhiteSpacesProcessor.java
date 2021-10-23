package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor extends TextProcessor {

    private static final ProcessingStage STAGE = ProcessingStage.PREPROCESSING;

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }

    @Override
    public String process(String text) {
        return text.replaceAll("\\s++", " ");
    }

}
