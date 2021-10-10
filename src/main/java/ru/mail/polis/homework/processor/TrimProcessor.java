package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor{

    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POST_PROCESSING_STAGE;
    }

    @Override
    public String process(String text) {
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }
}
