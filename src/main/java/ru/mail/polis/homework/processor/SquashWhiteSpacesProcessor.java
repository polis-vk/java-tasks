package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor implements TextProcessor {
    private static final ProcessingStage PROCESSING_STAGE = ProcessingStage.PREPROCESSING;

    @Override
    public int getStagePriority() {
        return PROCESSING_STAGE.getPriority();
    }

    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
