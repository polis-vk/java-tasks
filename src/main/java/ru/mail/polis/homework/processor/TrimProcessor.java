package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    private final int length;

    public TrimProcessor(int length) {
        this.length = length;
    }

    @Override
    public ProcessingStage getStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String process(String text) {
        return text.length() > length ? text.substring(0, length) : text;
    }
}
