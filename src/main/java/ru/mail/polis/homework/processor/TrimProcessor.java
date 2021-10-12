package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    private final ProcessingStage stage;
    private final int length;

    public TrimProcessor(ProcessingStage stage, int length) {
        this.stage = stage;
        this.length = length;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return stage;
    }

    @Override
    public String process(String text) {
        if (length > text.length()) {
            return text;
        }
        return text.substring(0, length);
    }
}
