package ru.mail.polis.homework.processor;

public class TrimProcessor extends Processor implements TextProcessor {

    private final int maxLength;

    public TrimProcessor(int maxLength) {
        super(ProcessingStage.POST_PROCESSING);
        this.maxLength = maxLength;
    }

    @Override
    public String process(String data) {
        return data.length() > maxLength ? data.substring(0, maxLength) : data;
    }
}
