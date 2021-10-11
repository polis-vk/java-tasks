package ru.mail.polis.homework.processor;

public class TrimTextProcessor implements TextProcessor {

    private static final ProcessingStage PROCESSING_STAGE = ProcessingStage.POSTPROCESSING;
    private final int maxLength;

    public TrimTextProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String process(String text) {
        return maxLength <= text.length() ? text.substring(0, maxLength) : text;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return PROCESSING_STAGE;
    }
}
