package ru.mail.polis.homework.processor;

public class TrimProcessor extends Processor implements TextProcessor {

    {
        processingStage = ProcessingStage.POST_PROCESSING;
    }

    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String handle(String data) {
        return data.length() > maxLength ? data.substring(0, maxLength) : data;
    }
}
