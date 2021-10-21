package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    private final ProcessingStage stage = ProcessingStage.PREPROCESSING_STAGE;

    @Override
    public ProcessingStage getStage() {
        return stage;
    }

    @Override
    public String processText(String txt) {
        return txt.replaceAll("\\s+", " ");
    }
}
