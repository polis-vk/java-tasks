package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    private static final ProcessingStage processingStage = ProcessingStage.PREPROCESSING;

    @Override
    public int getStagePriority() {
        return processingStage.getPriority();
    }

    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
