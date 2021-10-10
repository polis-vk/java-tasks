package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    private static final ProcessingStage stage = ProcessingStage.PREPROCESSING;

    @Override
    public ProcessingStage getProcessingStage() {
        return stage;
    }

    @Override
    public String processText(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
