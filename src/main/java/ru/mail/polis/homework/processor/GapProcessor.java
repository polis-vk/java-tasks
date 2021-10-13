package ru.mail.polis.homework.processor;

public class GapProcessor implements TextProcessor {
    private final ProcessingStage stage = ProcessingStage.PREPROCESSING;

    @Override
    public String execute(String text) {
        return text.replaceAll("\\s+", " ");
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
