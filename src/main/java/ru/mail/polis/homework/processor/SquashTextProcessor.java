package ru.mail.polis.homework.processor;

public class SquashTextProcessor implements TextProcessor {

    private static final ProcessingStage PROCESSING_STAGE = ProcessingStage.PREPROCESSING;

    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", "");
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return PROCESSING_STAGE;
    }
}
